package com.op.framework.boot.sdk.client.account.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.op.framework.boot.sdk.client.SdkProperties;
import com.op.framework.boot.sdk.client.account.entity.ThirdAccount;
import com.op.framework.boot.sdk.client.account.exception.ThirdAccountException;
import com.op.framework.boot.sdk.client.account.mapper.ThirdAccountMapper;
import com.op.framework.boot.sdk.client.account.model.TokenRequestInfo;
import com.op.framework.boot.sdk.client.account.model.TokenResponse;
import com.op.framework.boot.sdk.client.base.ThirdSdkType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 第三方帐号 Service
 *
 * @author cdrcool
 */
@Slf4j
public abstract class ThirdAccountService {
    private static final Map<String, TokenRequestInfo> STORE = new ConcurrentHashMap<>();

    private final SdkProperties sdkProperties;
    private final ThirdAccountMapper thirdAccountMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    public ThirdAccountService(SdkProperties sdkProperties,
                               ThirdAccountMapper thirdAccountMapper,
                               RedisTemplate<String, Object> redisTemplate) {
        this.sdkProperties = sdkProperties;
        this.thirdAccountMapper = thirdAccountMapper;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 应用启动后，初始化所有的第三方token
     */
    @PostConstruct
    public void postConstruct() {
//        initAllToken();
    }

    /**
     * 请求第三方token
     *
     * @param account        第三方账号
     * @param deferredResult {@link DeferredResult}
     */
    public void requestAccessToken(String account, DeferredResult<String> deferredResult) {
        ThirdAccount thirdAccount = getThirdAccount(account);
        String state = UUID.randomUUID().toString();

        TokenRequestInfo tokenRequestInfo = new TokenRequestInfo(state, thirdAccount.getAccount());
        // 保存当前请求的deferredResult，在token回调成功后对其setResult
        tokenRequestInfo.setDeferredResult(deferredResult);
        STORE.put(state, tokenRequestInfo);
        log.info("保存state：{}对应的第三方帐号：{}", state, thirdAccount.getAccount());

        requestAccessToken(thirdAccount, state);
    }

    /**
     * 获取第三方token回调
     *
     * @param code  授权码
     * @param state 回传state（与传递给第三方的state一致）
     */
    @Async
    public void callbackToken(String code, String state) {
        TokenResponse response = getTokenResponse(code);

        TokenRequestInfo tokenRequestInfo = STORE.get(state);
        if (tokenRequestInfo == null) {
            throw new ThirdAccountException("未找到与state：" + state + "对应的token请求信息");
        }
        log.info("找到state：{}对应的第三方帐号：{}", state, tokenRequestInfo.getAccount());

        // 查找第三方帐号，并更新其对应的token响应
        LambdaQueryWrapper<ThirdAccount> thirdAccountWrapper = Wrappers.lambdaQuery();
        thirdAccountWrapper.eq(ThirdAccount::getAccountType, accountType().getValue());
        thirdAccountWrapper.eq(ThirdAccount::getAccount, tokenRequestInfo.getAccount());
        ThirdAccount thirdAccount = thirdAccountMapper.selectOne(thirdAccountWrapper);
        if (thirdAccount == null) {
            throw new ThirdAccountException("未找到第三方帐号：" + tokenRequestInfo.getAccount());
        }
        // 更新第三方账号对应的token信息
        updateThirdAccountTokenInfo(thirdAccount, response);

        DeferredResult<String> deferredResult = tokenRequestInfo.getDeferredResult();
        if (deferredResult != null) {
            // 将token设置到deferredResult里
            deferredResult.setResult(response.getAccessToken());
        }
    }

    /**
     * 获取第三方账号的access token的redis key
     *
     * @param account 第三方账号
     * @return redis key
     */
    private String getAccessTokenKey(String account) {
        return String.format("%s:%s", accountType().getValue(), account);
    }

    /**
     * 更新第三方账号对应的token信息
     *
     * @param thirdAccount 第三方账号
     * @param response     第三方token响应
     */
    private void updateThirdAccountTokenInfo(ThirdAccount thirdAccount, TokenResponse response) {
        // 京东8小时后获取或刷新token才会返回新的token
        if (!Objects.equals(thirdAccount.getAccessToken(), response.getAccessToken())) {
            // 将第三方帐号及其对应的token存到redis缓存
            redisTemplate.opsForValue().set(getAccessTokenKey(thirdAccount.getAccount()), response.getAccessToken(),
                    System.currentTimeMillis() - (response.getTime() + response.getExpiresIn() * 1000), TimeUnit.MICROSECONDS);
        }

        LocalDateTime now = LocalDateTime.now();

        // 京东8小时后获取或刷新token才会返回新的token
        if (!Objects.equals(thirdAccount.getAccessToken(), response.getAccessToken())) {
            thirdAccount.setAccessToken(response.getAccessToken());
            thirdAccount.setAccessTokenExpiresAt(response.getTime() + response.getExpiresIn() * 1000);
            thirdAccount.setAccessTokenUpdateTime(now);
        }
        // 京东8小时后获取或刷新token才会返回新的token
        if (!Objects.equals(thirdAccount.getRefreshToken(), response.getRefreshToken())) {
            thirdAccount.setRefreshToken(response.getRefreshToken());
            // 京东未返回刷新token的过期时间
            if (response.getRefreshTokenExpires() != null) {
                thirdAccount.setRefreshTokenExpiresAt(response.getTime() + response.getRefreshTokenExpires() * 1000);
            }
            thirdAccount.setRefreshTokenUpdateTime(now);
        }

        thirdAccountMapper.updateById(thirdAccount);
    }

    /**
     * 刷新第三方token
     *
     * @param account 第三方账号
     * @return 第三方token
     */
    public String refreshAccessToken(String account) {
        ThirdAccount thirdAccount = getThirdAccount(account);
        return refreshAccessToken(thirdAccount);
    }

    /**
     * 刷新第三方token
     *
     * @param thirdAccount 第三方账号
     * @return 第三方token
     */
    public String refreshAccessToken(ThirdAccount thirdAccount) {
        TokenResponse response = getRefreshTokenResponse(thirdAccount);

        // 更新第三方账号对应的token信息
        updateThirdAccountTokenInfo(thirdAccount, response);

        return response.getAccessToken();
    }

    /**
     * 获取第三方token
     *
     * @param account        第三方账号
     * @param deferredResult {@link DeferredResult}
     */
    public String getAccessToken(String account, DeferredResult<String> deferredResult) {
        ThirdAccount thirdAccount = getThirdAccount(account);

        // 如果缓存中有访问令牌，则返回缓存中的访问令牌
        String token = (String) redisTemplate.opsForValue().get(getAccessTokenKey(thirdAccount.getAccount()));
        if (StringUtils.hasText(token)) {
            log.info("从缓存中获取到access token：{}", token);
            if (deferredResult != null) {
                deferredResult.setResult(token);
            }
            return token;
        }

        // 如果数据库中有访问令牌，且未过期，则返回数据据中的访问令牌
        Long now = System.currentTimeMillis();
        if (StringUtils.hasText(thirdAccount.getAccessToken()) && now.compareTo(thirdAccount.getAccessTokenExpiresAt()) < 0) {
            log.info("从数据库中获取到access token：{}", thirdAccount.getAccessToken());
            if (deferredResult != null) {
                deferredResult.setResult(thirdAccount.getAccessToken());
            }
            return thirdAccount.getAccessToken();
        }

        // 如果数据据中有刷新令牌，且未过期，则通过刷新令牌获取访问令牌
        boolean doRefresh = StringUtils.hasText(thirdAccount.getRefreshToken()) &&
                (thirdAccount.getRefreshTokenExpiresAt() == null || now.compareTo(thirdAccount.getRefreshTokenExpiresAt()) < 0);
        if (doRefresh) {
            try {
                log.info("从数据库中获取到refresh token：{}，执行刷新token操作", thirdAccount.getRefreshToken());
                String newToken = refreshAccessToken(thirdAccount.getRefreshToken());
                if (deferredResult != null) {
                    deferredResult.setResult(newToken);
                }
                return newToken;
            } catch (Exception e) {
                log.error("刷新第三方token异常，第三方账号：{}，刷新token:{}",
                        thirdAccount.getAccount(), thirdAccount.getRefreshToken(), e);
            }
        }

        // 请求第三方token
        requestAccessToken(account, deferredResult);

        return null;
    }

    /**
     * 初始化所有的第三方token
     */
    public void initAllToken() {
        LambdaQueryWrapper<ThirdAccount> thirdAccountWrapper = Wrappers.lambdaQuery();
        thirdAccountWrapper.eq(ThirdAccount::getAccountType, accountType().getValue());
        List<ThirdAccount> thirdAccounts = thirdAccountMapper.selectList(thirdAccountWrapper);
        thirdAccounts.forEach(thirdAccount -> {
            try {
                requestAccessToken(thirdAccount.getAccount(), null);
            } catch (Exception e) {
                log.error("初始化第三方token异常，账号信息：{}", thirdAccount, e);
            }
        });
    }

    /**
     * 刷新所有的第三方token
     */
    public void refreshAllToken() {
        LambdaQueryWrapper<ThirdAccount> thirdAccountWrapper = Wrappers.lambdaQuery();
        thirdAccountWrapper.eq(ThirdAccount::getAccountType, accountType().getValue());
        List<ThirdAccount> thirdAccounts = thirdAccountMapper.selectList(thirdAccountWrapper);
        thirdAccounts.forEach(thirdAccount -> {
            try {
                refreshAccessToken(thirdAccount);
            } catch (Exception e) {
                log.error("刷新第三方token异常，账号信息：{}", thirdAccount, e);
                requestAccessToken(thirdAccount.getAccount(), null);
            }
        });
    }

    /**
     * 获取第三方账号配置属性
     *
     * @return 第三方账号配置属性
     */
    protected final SdkProperties.ThirdProperties getThirdProperties() {
        return sdkProperties.getAccounts().computeIfAbsent(accountType().getValue(), (key) -> {
            throw new ThirdAccountException("未配置" + accountType().getValue() + "账号的相关属性");
        });
    }

    /**
     * 获取第三方账号
     *
     * @param account 第三方账号
     * @return 第三方账号
     */
    protected final ThirdAccount getThirdAccount(String account) {
        LambdaQueryWrapper<ThirdAccount> thirdAccountWrapper = Wrappers.lambdaQuery();
        thirdAccountWrapper.eq(ThirdAccount::getAccountType, accountType().getValue());
        thirdAccountWrapper.eq(ThirdAccount::getAccount, account);
        ThirdAccount thirdAccount = thirdAccountMapper.selectOne(thirdAccountWrapper);
        if (thirdAccount == null) {
            throw new ThirdAccountException("不存在帐号名为：" + account + "的第三方帐号");
        }

        log.info("找到帐号名为：{}对应的第三方帐号：{}", account, thirdAccount.getAccount());
        return thirdAccount;
    }

    /**
     * 第三方账号类型
     *
     * @return 第三方账号类型
     */
    protected abstract ThirdSdkType accountType();

    /**
     * 请求第三方token
     *
     * @param thirdAccount 第三方账号
     * @param state        传递给第三方的state
     */
    public abstract void requestAccessToken(ThirdAccount thirdAccount, String state);

    /**
     * 获取token响应
     *
     * @param code 授权码
     * @return 第三方token响应
     */
    protected abstract TokenResponse getTokenResponse(String code);

    /**
     * 获取刷新token响应
     *
     * @param thirdAccount 第三方账号
     * @return 第三方token响应
     */
    protected abstract TokenResponse getRefreshTokenResponse(ThirdAccount thirdAccount);
}

package com.op.sdk.client.account.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.op.sdk.client.account.entity.AccountType;
import com.op.sdk.client.account.entity.CompanyInfo;
import com.op.sdk.client.account.entity.ThirdAccount;
import com.op.sdk.client.account.exception.AccountException;
import com.op.sdk.client.account.mapper.CompanyInfoMapper;
import com.op.sdk.client.account.mapper.ThirdAccountMapper;
import com.op.sdk.client.account.model.TokenRequestInfo;
import com.op.sdk.client.account.model.TokenResponse;
import com.op.sdk.client.config.SdkProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
    private final CompanyInfoMapper companyInfoMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    public ThirdAccountService(SdkProperties sdkProperties,
                               ThirdAccountMapper thirdAccountMapper,
                               CompanyInfoMapper companyInfoMapper,
                               RedisTemplate<String, Object> redisTemplate) {
        this.sdkProperties = sdkProperties;
        this.thirdAccountMapper = thirdAccountMapper;
        this.companyInfoMapper = companyInfoMapper;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 应用启动后，初始化所有的第三方token
     */
    @PostConstruct
    public void postConstruct() {
        initAllToken();
    }

    /**
     * 请求第三方token
     *
     * @param taxpayerId     纳税人识别号
     * @param deferredResult {@link DeferredResult}
     */
    public void requestAccessToken(String taxpayerId, DeferredResult<String> deferredResult) {
        ThirdAccount thirdAccount = getThirdAccount(taxpayerId);
        String state = UUID.randomUUID().toString();
        requestAccessToken(thirdAccount, state);

        TokenRequestInfo tokenRequestInfo = new TokenRequestInfo(state, accountType(), thirdAccount.getAccount());
        // 保存当前请求的deferredResult，在token回调成功后对其setResult
        tokenRequestInfo.setDeferredResult(deferredResult);
        STORE.put(state, tokenRequestInfo);
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
            throw new AccountException("未找到与state：{}对应的token请求信息：" + state);
        }
        log.info("找到state：{}对应的第三方帐号：{}", state, tokenRequestInfo.getAccount());

        // 将第三方帐号及其对应的token存到redis缓存
        redisTemplate.opsForValue().set(tokenRequestInfo.getAccount(), response.getAccessToken());

        // 查找第三方帐号，并更新其对应的token响应
        LambdaQueryWrapper<ThirdAccount> jdAccountWrapper = Wrappers.lambdaQuery();
        jdAccountWrapper.eq(ThirdAccount::getAccount, tokenRequestInfo.getAccount());
        ThirdAccount thirdAccount = thirdAccountMapper.selectOne(jdAccountWrapper);
        if (thirdAccount == null) {
            throw new AccountException("未找到第三方帐号：" + tokenRequestInfo.getAccount());
        }
        thirdAccount.setAccessToken(response.getAccessToken());
        thirdAccount.setRefreshToken(response.getRefreshToken());
        thirdAccount.setAccessTokenExpiresAt(response.getTime() + response.getExpiresIn());
        thirdAccount.setRefreshTokenExpiresAt(response.getTime() + response.getRefreshTokenExpires());
        thirdAccountMapper.updateById(thirdAccount);

        DeferredResult<String> deferredResult = tokenRequestInfo.getDeferredResult();
        if (deferredResult != null) {
            // 将token设置到deferredResult里
            deferredResult.setResult(response.getAccessToken());
        }
    }

    /**
     * 刷新第三方token
     *
     * @param taxpayerId 纳税人识别号
     * @return 第三方token
     */
    public String refreshToken(String taxpayerId) {
        ThirdAccount thirdAccount = getThirdAccount(taxpayerId);
        return refreshToken(thirdAccount);
    }

    /**
     * 刷新第三方token
     *
     * @param thirdAccount 第三方账号
     * @return 第三方token
     */
    public String refreshToken(ThirdAccount thirdAccount) {
        TokenResponse response = getRefreshTokenResponse(thirdAccount);

        // 将第三方帐号及其对应的token存到redis缓存
        redisTemplate.opsForValue().set(thirdAccount.getAccount(), response.getAccessToken());

        // 更新第三方帐号对应的token响应
        thirdAccount.setAccessToken(response.getAccessToken());
        thirdAccount.setRefreshToken(response.getRefreshToken());
        thirdAccount.setAccessTokenExpiresAt(response.getTime() + response.getExpiresIn());
        thirdAccount.setRefreshTokenExpiresAt(response.getTime() + response.getRefreshTokenExpires());
        thirdAccountMapper.updateById(thirdAccount);

        return response.getAccessToken();
    }

    /**
     * 获取第三方token
     *
     * @param taxpayerId     纳税人识别号
     * @param deferredResult {@link DeferredResult}
     */
    public void getAccessToken(String taxpayerId, DeferredResult<String> deferredResult) {
        ThirdAccount thirdAccount = getThirdAccount(taxpayerId);

        // 如果缓存中有访问令牌，则返回缓存中的访问令牌
        String token = (String) redisTemplate.opsForValue().get(thirdAccount.getAccount());
        if (StringUtils.hasText(token)) {
            deferredResult.setResult(token);
            return;
        }

        // 如果数据据中有访问令牌，且未过期，则返回数据据中的访问令牌
        Long now = System.currentTimeMillis();
        if (StringUtils.hasText(thirdAccount.getAccessToken()) && now.compareTo(thirdAccount.getAccessTokenExpiresAt()) > 0) {
            deferredResult.setResult(thirdAccount.getAccessToken());
            return;
        }

        // 如果数据据中有刷新令牌，且未过期，则通过刷新令牌获取访问令牌
        if (StringUtils.hasText(thirdAccount.getRefreshToken()) && now.compareTo(thirdAccount.getRefreshTokenExpiresAt()) > 0) {
            deferredResult.setResult(refreshToken(thirdAccount.getRefreshToken()));
            return;
        }

        // 请求第三方token
        requestAccessToken(taxpayerId, deferredResult);
    }

    /**
     * 初始化所有的第三方token
     */
    public void initAllToken() {
        LambdaQueryWrapper<ThirdAccount> jdAccountWrapper = Wrappers.lambdaQuery();
        List<ThirdAccount> thirdAccounts = thirdAccountMapper.selectList(jdAccountWrapper);
        thirdAccounts.forEach(thirdAccount -> {
            try {
                requestAccessToken(thirdAccount, UUID.randomUUID().toString());
            } catch (Exception e) {
                log.error("初始化第三方token异常，账号信息：{}", thirdAccount, e);
            }
        });
    }

    /**
     * 刷新所有的第三方token
     */
    public void refreshAllToken() {
        LambdaQueryWrapper<ThirdAccount> jdAccountWrapper = Wrappers.lambdaQuery();
        List<ThirdAccount> thirdAccounts = thirdAccountMapper.selectList(jdAccountWrapper);
        thirdAccounts.forEach(thirdAccount -> {
            try {
                refreshToken(thirdAccount);
            } catch (Exception e) {
                log.error("刷新第三方token异常，账号信息：{}", thirdAccount, e);
            }
        });
    }

    /**
     * 获取第三方账号配置属性
     *
     * @return 第三方账号配置属性
     */
    protected final SdkProperties.Account getAccountProperties() {
        return sdkProperties.getAccounts().computeIfAbsent(accountType().getValue(), (key) -> {
            throw new AccountException("未配置" + accountType().getValue() + "账号的相关属性");
        });
    }

    /**
     * 获取第三方账号
     *
     * @param taxpayerId 纳税人识别号
     * @return 第三方账号
     */
    protected final ThirdAccount getThirdAccount(String taxpayerId) {
        if (!StringUtils.hasText(taxpayerId)) {
            SdkProperties.Account account = getAccountProperties();

            ThirdAccount thirdAccount = new ThirdAccount();
            thirdAccount.setAccount(account.getAccount());
            thirdAccount.setPassword(account.getPassword());
            return thirdAccount;
        }

        LambdaQueryWrapper<CompanyInfo> companyInfoWrapper = Wrappers.lambdaQuery();
        companyInfoWrapper.eq(CompanyInfo::getTaxpayerId, taxpayerId);
        CompanyInfo companyInfo = companyInfoMapper.selectOne(companyInfoWrapper);
        if (companyInfo == null) {
            throw new AccountException("不存在纳税人为：" + taxpayerId + "的公司信息");
        }


        String account;
        switch (accountType()) {
            case JD:
                account = companyInfo.getJdAccount();
                break;
            case SN:
                account = companyInfo.getSnAccount();
                break;
            default:
                throw new AccountException("不支持的账号类型：" + accountType().getValue());
        }

        LambdaQueryWrapper<ThirdAccount> thirdAccountWrapper = Wrappers.lambdaQuery();
        thirdAccountWrapper.eq(ThirdAccount::getAccountType, accountType().getValue());
        thirdAccountWrapper.eq(ThirdAccount::getAccount, account);
        ThirdAccount thirdAccount = thirdAccountMapper.selectOne(thirdAccountWrapper);
        if (thirdAccount == null) {
            throw new AccountException("不存在帐号名为：" + companyInfo.getJdAccount() + "的第三方帐号");
        }

        log.info("找到纳税人识别号：{}对应的第三方帐号：{}", taxpayerId, thirdAccount.getAccount());
        return thirdAccount;
    }

    /**
     * 第三方账号类型
     *
     * @return 第三方账号类型
     */
    protected abstract AccountType accountType();

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

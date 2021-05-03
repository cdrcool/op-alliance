package com.op.sdk.client.account.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.op.sdk.client.account.entity.CompanyInfo;
import com.op.sdk.client.account.entity.JdAccount;
import com.op.sdk.client.account.mapper.CompanyInfoMapper;
import com.op.sdk.client.account.mapper.JdAccountMapper;
import com.op.sdk.client.account.model.JdTokenRequestInfo;
import com.op.sdk.client.account.utils.RsaCoderUtils;
import com.op.sdk.client.config.JdAccountProperties;
import com.op.sdk.client.third.JdTokenFeignClient;
import com.op.sdk.client.third.response.JdTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.PostConstruct;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 京东帐号 Service Impl
 *
 * @author cdrcool
 */
@Slf4j
@Service
public class JdAccountServiceImpl implements JdAccountService {
    private static final Map<String, JdTokenRequestInfo> STORE = new ConcurrentHashMap<>();

    private final RedisTemplate<String, Object> redisTemplate;
    private final JdAccountProperties jdAccountProperties;
    private final JdTokenFeignClient jdTokenFeignClient;
    private final JdAccountMapper jdAccountMapper;
    private final CompanyInfoMapper companyInfoMapper;

    public JdAccountServiceImpl(RedisTemplate<String, Object> redisTemplate,
                                JdAccountProperties jdAccountProperties,
                                JdTokenFeignClient jdTokenFeignClient,
                                JdAccountMapper jdAccountMapper,
                                CompanyInfoMapper companyInfoMapper) {
        this.redisTemplate = redisTemplate;
        this.jdAccountProperties = jdAccountProperties;
        this.jdTokenFeignClient = jdTokenFeignClient;
        this.jdAccountMapper = jdAccountMapper;
        this.companyInfoMapper = companyInfoMapper;
    }

    @PostConstruct
    public void postConstruct() {
        initAllToken();
    }

    @Override
    public void requestAccessToken(String taxpayerId, DeferredResult<String> deferredResult) {
        JdAccount jdAccount = getJdAccount(taxpayerId);
        String state = UUID.randomUUID().toString();
        requestAccessToken(jdAccount, state);

        // 保存当前请求的deferredResult，在京东token回调成功后对其赋值
        JdTokenRequestInfo jdTokenRequestInfo = STORE.get(state);
        jdTokenRequestInfo.setDeferredResult(deferredResult);
        STORE.put(state, jdTokenRequestInfo);
    }

    private void requestAccessToken(JdAccount jdAccount, String state) {
        try {
            String encodeUsername = URLEncoder.encode(jdAccount.getAccount(), StandardCharsets.UTF_8.toString());
            String md5Password = DigestUtils.md5Hex(jdAccount.getPassword());
            String ciphertextPassword = RsaCoderUtils.encryptByPrivateKey(md5Password, jdAccountProperties.getRsaKey());
            String encodePassword = URLEncoder.encode(ciphertextPassword, StandardCharsets.UTF_8.toString());
            String encodeRedirectUri = URLEncoder.encode(jdAccountProperties.getRedirectUri(), StandardCharsets.UTF_8.toString());

            String code = jdTokenFeignClient.authorizeForVop(jdAccountProperties.getAppKey(),
                    encodeRedirectUri,
                    encodeUsername,
                    encodePassword,
                    "code",
                    "snsapi_base",
                    state);
            log.info("根据京东帐号：{}获取到鉴权码：{}", jdAccount.getAccount(), code);

            JdTokenRequestInfo jdTokenRequestInfo = new JdTokenRequestInfo(state, jdAccount.getAccount());
            STORE.put(state, jdTokenRequestInfo);
        } catch (Exception e) {
            log.error("请求京东token异常", e);
            throw new RuntimeException("请求京东token异常", e);
        }
    }

    private JdAccount getJdAccount(String taxpayerId) {
        LambdaQueryWrapper<CompanyInfo> companyInfoWrapper = Wrappers.lambdaQuery();
        companyInfoWrapper.eq(CompanyInfo::getTaxpayerId, taxpayerId);
        CompanyInfo companyInfo = companyInfoMapper.selectOne(companyInfoWrapper);
        if (companyInfo == null) {
            throw new RuntimeException("不存在纳税人为：" + taxpayerId + "的公司信息");
        }

        LambdaQueryWrapper<JdAccount> jdAccountWrapper = Wrappers.lambdaQuery();
        jdAccountWrapper.eq(JdAccount::getAccount, companyInfo.getJdAccount());
        JdAccount jdAccount = jdAccountMapper.selectOne(jdAccountWrapper);
        if (jdAccount == null) {
            throw new RuntimeException("不存在帐号名为：" + companyInfo.getJdAccount() + "的京东帐号");
        }

        log.info("找到纳税人识别号：{}对应的京东帐号：{}", taxpayerId, jdAccount.getAccount());
        return jdAccount;
    }

    @Async
    @Override
    public void callbackToken(String code, String state) {
        JdTokenResponse response = jdTokenFeignClient.accessToken(jdAccountProperties.getAppKey(),
                jdAccountProperties.getAppSecret(),
                "authorization_code",
                code);
        log.info("根据鉴权码：{}到获取token响应：{}", code, response);

        JdTokenRequestInfo jdTokenRequestInfo = STORE.get(state);
        log.info("找到state：{}对应的京东帐号：{}", state, jdTokenRequestInfo.getAccount());

        // 查找京东帐号，并更新其对应的token响应
        LambdaQueryWrapper<JdAccount> jdAccountWrapper = Wrappers.lambdaQuery();
        jdAccountWrapper.eq(JdAccount::getAccount, jdTokenRequestInfo.getAccount());
        JdAccount jdAccount = jdAccountMapper.selectOne(jdAccountWrapper);
        if (jdAccount == null) {
            throw new RuntimeException("未找到京东帐号：" + jdTokenRequestInfo.getAccount());
        }
        jdAccount.setAccessToken(response.getAccessToken());
        jdAccount.setRefreshToken(response.getRefreshToken());
        jdAccount.setAccessTokenExpiresAt(response.getTime() + response.getExpiresIn());
        jdAccount.setRefreshTokenExpiresAt(response.getTime() + response.getRefreshTokenExpires());
        jdAccountMapper.updateById(jdAccount);

        // 将京东帐号及其对应的token存到redis缓存
        redisTemplate.opsForValue().set(jdTokenRequestInfo.getAccount(), response.getAccessToken());

        DeferredResult<String> deferredResult = jdTokenRequestInfo.getDeferredResult();
        if (deferredResult != null) {
            // 将token设置到deferredResult里
            deferredResult.setResult(response.getAccessToken());
        }
    }

    @Override
    public String refreshToken(String taxpayerId) {
        JdAccount jdAccount = getJdAccount(taxpayerId);
        return refreshToken(jdAccount);
    }

    private String refreshToken(JdAccount jdAccount) {
        JdTokenResponse response = jdTokenFeignClient.refreshToken(jdAccountProperties.getAppKey(),
                jdAccountProperties.getAppSecret(),
                "authorization_code",
                jdAccount.getRefreshToken());
        log.info("刷新京东token成功，token响应：{}", response);

        // 更新京东帐号对应的token响应
        jdAccount.setAccessToken(response.getAccessToken());
        jdAccount.setRefreshToken(response.getRefreshToken());
        jdAccount.setAccessTokenExpiresAt(response.getTime() + response.getExpiresIn());
        jdAccount.setRefreshTokenExpiresAt(response.getTime() + response.getRefreshTokenExpires());
        jdAccountMapper.updateById(jdAccount);

        return response.getAccessToken();
    }

    @Override
    public void getAccessToken(String taxpayerId, DeferredResult<String> deferredResult) {
        JdAccount jdAccount = getJdAccount(taxpayerId);

        // 如果缓存中有访问令牌，则返回缓存中的访问令牌
       String token = (String) redisTemplate.opsForValue().get(jdAccount.getAccount());
       if (StringUtils.hasText(token)) {
           deferredResult.setResult(token);
           return;
       }

        // 如果数据据中有访问令牌，且未过期，则返回数据据中的访问令牌
        Long now = System.currentTimeMillis();
        if (StringUtils.hasText(jdAccount.getAccessToken()) && now.compareTo(jdAccount.getAccessTokenExpiresAt()) > 0) {
            deferredResult.setResult(jdAccount.getAccessToken());
            return;
        }

        // 如果数据据中有刷新令牌，且未过期，则通过刷新令牌获取访问令牌
        if (StringUtils.hasText(jdAccount.getRefreshToken()) && now.compareTo(jdAccount.getRefreshTokenExpiresAt()) > 0) {
            deferredResult.setResult(refreshToken(jdAccount.getRefreshToken()));
            return;
        }

        // 请求京东token
        requestAccessToken(taxpayerId, deferredResult);
    }

    @Override
    public void initAllToken() {
        LambdaQueryWrapper<JdAccount> jdAccountWrapper = Wrappers.lambdaQuery();
        List<JdAccount> jdAccounts = jdAccountMapper.selectList(jdAccountWrapper);
        jdAccounts.forEach(jdAccount -> requestAccessToken(jdAccount, UUID.randomUUID().toString()));
    }

    @Override
    public void refreshAllToken() {
        LambdaQueryWrapper<JdAccount> jdAccountWrapper = Wrappers.lambdaQuery();
        List<JdAccount> jdAccounts = jdAccountMapper.selectList(jdAccountWrapper);
        jdAccounts.forEach(this::refreshToken);
    }
}

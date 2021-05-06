package com.op.sdk.client.account.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.op.sdk.client.account.entity.AccountType;
import com.op.sdk.client.account.entity.ThirdAccount;
import com.op.sdk.client.account.mapper.CompanyInfoMapper;
import com.op.sdk.client.account.mapper.ThirdAccountMapper;
import com.op.sdk.client.account.model.TokenResponse;
import com.op.sdk.client.account.utils.RsaCoderUtils;
import com.op.sdk.client.config.SdkProperties;
import com.op.sdk.client.third.JdTokenFeignClient;
import com.op.sdk.client.third.response.JdTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * 京东帐号 Service Impl
 *
 * @author cdrcool
 */
@Slf4j
@Service
public class JdAccountServiceImpl extends ThirdAccountService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JdTokenFeignClient jdTokenFeignClient;

    public JdAccountServiceImpl(SdkProperties sdkProperties,
                                ThirdAccountMapper thirdAccountMapper,
                                CompanyInfoMapper companyInfoMapper,
                                JdTokenFeignClient jdTokenFeignClient,
                                RedisTemplate<String, Object> redisTemplate) {
        super(sdkProperties, thirdAccountMapper, companyInfoMapper, redisTemplate);
        this.jdTokenFeignClient = jdTokenFeignClient;
    }

    @Override
    protected AccountType accountType() {
        return AccountType.JD;
    }

    @Override
    public void requestAccessToken(ThirdAccount thirdAccount, String state) {
        try {
            String encodeUsername = URLEncoder.encode(thirdAccount.getAccount(), StandardCharsets.UTF_8.toString());
            String md5Password = DigestUtils.md5Hex(thirdAccount.getPassword());
            String ciphertextPassword = RsaCoderUtils.encryptByPrivateKey(md5Password, getAccountProperties().getRsaKey());
            String encodePassword = URLEncoder.encode(ciphertextPassword, StandardCharsets.UTF_8.toString());
            String encodeRedirectUri = URLEncoder.encode(getAccountProperties().getRedirectUri(), StandardCharsets.UTF_8.toString());

            String responseJson = jdTokenFeignClient.authorizeForVop(getAccountProperties().getAppKey(),
                    encodeRedirectUri,
                    encodeUsername,
                    encodePassword,
                    "code",
                    "snsapi_base",
                    state);

            Map<String, Object> param = objectMapper.readValue(responseJson, new TypeReference<Map<String, Object>>() {
            });
            if (!Objects.equals(0, param.get("code"))) {
                throw new RuntimeException("获取京东授权码失败，response:" + responseJson);
            }
        } catch (Exception e) {
            log.error("请求京东token异常", e);
            throw new RuntimeException("请求京东token异常", e);
        }
    }

    @Override
    protected TokenResponse getTokenResponse(String code) {
        JdTokenResponse response = jdTokenFeignClient.accessToken(getAccountProperties().getAppKey(),
                getAccountProperties().getAppSecret(),
                "authorization_code",
                code);
        log.info("根据鉴权码：{}到获取京东token响应：{}", code, response);
        return TokenResponse.build(response);
    }

    @Override
    protected TokenResponse getRefreshTokenResponse(ThirdAccount thirdAccount) {
        JdTokenResponse response = jdTokenFeignClient.refreshToken(getAccountProperties().getAppKey(),
                getAccountProperties().getAppSecret(),
                "authorization_code",
                thirdAccount.getRefreshToken());
        log.info("刷新京东token成功，token响应：{}", response);
        return TokenResponse.build(response);
    }
}

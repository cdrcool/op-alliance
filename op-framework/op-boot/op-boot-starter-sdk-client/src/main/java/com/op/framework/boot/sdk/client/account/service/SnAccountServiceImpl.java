package com.op.framework.boot.sdk.client.account.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.op.framework.boot.sdk.client.SdkProperties;
import com.op.framework.boot.sdk.client.account.entity.ThirdAccount;
import com.op.framework.boot.sdk.client.account.exception.ThirdAccountException;
import com.op.framework.boot.sdk.client.account.mapper.ThirdAccountMapper;
import com.op.framework.boot.sdk.client.account.model.ThirdTokenResponse;
import com.op.framework.boot.sdk.client.base.ThirdSdkType;
import com.op.framework.boot.sdk.client.feign.SnTokenFeignClient;
import com.op.framework.boot.sdk.client.response.SnTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * 苏宁帐号 Service Impl
 *
 * @author cdrcool
 */
@Slf4j
@Service
public class SnAccountServiceImpl extends ThirdAccountService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SnTokenFeignClient snTokenFeignClient;

    public SnAccountServiceImpl(SdkProperties sdkProperties,
                                ThirdAccountMapper thirdAccountMapper,
                                SnTokenFeignClient snTokenFeignClient,
                                RedisTemplate<String, Object> redisTemplate) {
        super(sdkProperties, thirdAccountMapper, redisTemplate);
        this.snTokenFeignClient = snTokenFeignClient;
    }

    @Override
    protected ThirdSdkType accountType() {
        return ThirdSdkType.SN;
    }

    @Override
    public void requestAccessToken(ThirdAccount thirdAccount, String state) {
        try {
            String responseJson = snTokenFeignClient.authorize(getThirdProperties().getAppKey(),
                    getThirdProperties().getRedirectUri(),
                    "code",
                    "snsapi_base",
                    state,
                    getThirdProperties().getItemcode());

            Map<String, Object> param = objectMapper.readValue(responseJson, new TypeReference<Map<String, Object>>() {
            });
            if (!Objects.equals(0, param.get("code"))) {
                throw new ThirdAccountException("请求苏宁token失败，response:" + responseJson);
            }
        } catch (Exception e) {
            log.error("请求苏宁token异常", e);
            throw new ThirdAccountException("请求苏宁token异常", e);
        }
    }

    @Override
    protected ThirdTokenResponse getTokenResponse(String code) {
        SnTokenResponse response = snTokenFeignClient.getToken(getThirdProperties().getAppKey(),
                getThirdProperties().getAppSecret(),
                code,
                "authorization_code",
                getThirdProperties().getRedirectUri()
        );
        log.info("根据鉴权码：{}到获取苏宁token响应：{}", code, response);

        return ThirdTokenResponse.buildFrom(response);
    }

    @Override
    protected ThirdTokenResponse getRefreshTokenResponse(ThirdAccount thirdAccount) {
        SnTokenResponse response = snTokenFeignClient.refreshToken(getThirdProperties().getAppKey(),
                getThirdProperties().getAppSecret(),
                "refresh_token",
                thirdAccount.getRefreshToken(),
                getThirdProperties().getRedirectUri());
        log.info("刷新苏宁token成功，token响应：{}", response);

        return ThirdTokenResponse.buildFrom(response);
    }
}

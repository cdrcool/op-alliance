package com.op.sdk.client.account.service;

import com.op.sdk.client.account.entity.AccountType;
import com.op.sdk.client.account.entity.ThirdAccount;
import com.op.sdk.client.account.mapper.ThirdAccountMapper;
import com.op.sdk.client.account.model.TokenResponse;
import com.op.sdk.client.config.SdkProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 苏宁帐号 Service Impl
 *
 * @author cdrcool
 */
@Slf4j
@Service
public class SnAccountServiceImpl extends ThirdAccountService {

    public SnAccountServiceImpl(SdkProperties sdkProperties,
                                ThirdAccountMapper thirdAccountMapper,
                                RedisTemplate<String, Object> redisTemplate) {
        super(sdkProperties, thirdAccountMapper, redisTemplate);
    }

    @Override
    protected AccountType accountType() {
        return AccountType.SN;
    }

    @Override
    public void requestAccessToken(ThirdAccount thirdAccount, String state) {
    }

    @Override
    protected TokenResponse getTokenResponse(String code) {
        return null;
    }

    @Override
    protected TokenResponse getRefreshTokenResponse(ThirdAccount thirdAccount) {
        return null;
    }
}

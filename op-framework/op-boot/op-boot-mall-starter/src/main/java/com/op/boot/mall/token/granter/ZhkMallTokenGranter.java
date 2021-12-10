package com.op.boot.mall.token.granter;

import com.op.boot.mall.constants.MallType;
import com.op.boot.mall.token.request.TokenAcquireRequest;
import com.op.boot.mall.token.request.TokenRefreshRequest;
import com.op.boot.mall.token.response.MallTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 震坤行电商 Token Granter
 *
 * @author chengdr01
 */
@Slf4j
@Component
public class ZhkMallTokenGranter implements MallTokenGranter {

    @Override
    public Optional<MallTokenResponse> acquireToken(TokenAcquireRequest tokenAcquireRequest) {
        return Optional.empty();
    }

    @Override
    public MallTokenResponse refreshToken(TokenRefreshRequest tokenRefreshRequest) {
        return null;
    }

    @Override
    public boolean supports(MallType mallType) {
        return MallType.ZKH.equals(mallType);
    }
}

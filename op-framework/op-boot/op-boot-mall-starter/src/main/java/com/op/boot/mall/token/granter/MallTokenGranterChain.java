package com.op.boot.mall.token.granter;

import com.op.boot.mall.constants.MallType;
import com.op.boot.mall.exception.MallException;
import com.op.boot.mall.token.request.TokenAcquireRequest;
import com.op.boot.mall.token.request.TokenAuthorizeRequest;
import com.op.boot.mall.token.request.TokenCallbackRequest;
import com.op.boot.mall.token.request.TokenRefreshRequest;
import com.op.boot.mall.token.response.MallTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 电商 Token Granter Chain
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class MallTokenGranterChain {
    private final List<MallTokenGranter> granters;

    public MallTokenGranterChain(List<MallTokenGranter> granters) {
        this.granters = granters;
    }

    /**
     * 获取电商 Token
     *
     * @param mallType            电商类型
     * @param tokenAcquireRequest Token 获取请求
     * @return 电商 token
     */
    public Optional<MallTokenResponse> acquireToken(MallType mallType, TokenAcquireRequest tokenAcquireRequest) {
        return getTokenGranter(mallType).acquireToken(tokenAcquireRequest);
    }

    /**
     * 刷新电商 Token
     *
     * @param mallType            电商类型
     * @param tokenRefreshRequest 刷新 Token 请求
     * @return 电商token
     */
    public MallTokenResponse refreshToken(MallType mallType, TokenRefreshRequest tokenRefreshRequest) {
        return getTokenGranter(mallType).refreshToken(tokenRefreshRequest);
    }

    /**
     * 申请鉴权码
     *
     * @param mallType              电商类型
     * @param tokenAuthorizeRequest 申请鉴权码请求
     */
    public void authorize(MallType mallType, TokenAuthorizeRequest tokenAuthorizeRequest) {
        getOauth2TokenGranter(mallType).authorize(tokenAuthorizeRequest);
    }

    /**
     * 电商回调接口：使用电商返回的鉴权码请求并消费电商 Token 响应
     *
     * @param mallType             电商类型
     * @param tokenCallbackRequest Token 回调请求
     */
    public void callback(MallType mallType, TokenCallbackRequest tokenCallbackRequest) {
        getOauth2TokenGranter(mallType).callback(tokenCallbackRequest);
    }

    /**
     * 获取电商 Token Granter
     *
     * @param mallType 电商类型
     * @return 电商 Token Granter
     */
    protected MallTokenGranter getTokenGranter(MallType mallType) {
        Optional<MallTokenGranter> optional = granters.stream()
                .filter(granter -> granter.supports(mallType))
                .findAny();
        return optional.orElseThrow(() -> new MallException("未定义电商【" + mallType.getDesc() + "】 Token Granter"));
    }

    /**
     * 获取电商 Oauth2 Token Granter
     *
     * @param mallType 电商类型
     * @return 电商 Oauth2 Token Granter
     */
    protected MallOauth2TokenGranter getOauth2TokenGranter(MallType mallType) {
        MallTokenGranter granter = getTokenGranter(mallType);
        return (MallOauth2TokenGranter) granter;
    }
}

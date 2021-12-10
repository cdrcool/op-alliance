package com.op.boot.mall.token.granter;

import com.op.boot.mall.token.request.TokenAuthorizeRequest;
import com.op.boot.mall.token.request.TokenCallbackRequest;
import com.op.boot.mall.token.response.MallTokenResponse;

import java.util.function.Consumer;

/**
 * 电商 Oauth2 Token Granter
 *
 * @author chengdr01
 */
public interface MallOauth2TokenGranter extends MallTokenGranter {

    /**
     * 申请鉴权码
     *
     * @param tokenAuthorizeRequest 鉴权码申请请求
     */
    void authorize(TokenAuthorizeRequest tokenAuthorizeRequest);

    /**
     * 电商回调接口：使用电商返回的鉴权码请求并消费电商 Token 响应
     *
     * @param tokenCallbackRequest Token 回调请求
     * @param responseConsumer     Token 响应消费者
     */
    void callback(TokenCallbackRequest tokenCallbackRequest, Consumer<MallTokenResponse> responseConsumer);
}

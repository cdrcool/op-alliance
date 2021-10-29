package com.op.boot.mall.client;

import java.util.Map;
import java.util.function.Consumer;

/**
 * 电商 Oauth2 Token 授予者
 *
 * @author cdrcool
 */
public interface MallOauth2TokenGranter extends MallTokenGranter {

    /**
     * 申请授权码
     *
     * @param params 请求参数
     */
    void authorize(Map<String, String> params);

    /**
     * 电商回调接口：使用电商返回的授权码请求并消费电商 token 响应
     *
     * @param code             授权码
     * @param params           请求参数
     * @param responseConsumer token 响应消费者
     */
    void callbackToken(String code, Map<String, String> params, Consumer<MallTokenResponse> responseConsumer);
}

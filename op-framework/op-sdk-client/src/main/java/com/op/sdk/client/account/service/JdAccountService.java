package com.op.sdk.client.account.service;

import com.op.sdk.client.account.model.JdTokenResponse;

/**
 * 京东帐号 Service
 *
 * @author chengdr01
 */
public interface JdAccountService {

    /**
     * 请求token
     *
     * @throws Exception exception
     */
    void requestAccessToken() throws Exception;

    /**
     * 获取token回调
     *
     * @param code 授权码
     */
    void callbackToken(String code);

    /**
     * 获取token
     *
     * @return 京东token响应
     */
    JdTokenResponse getAccessToken();
}

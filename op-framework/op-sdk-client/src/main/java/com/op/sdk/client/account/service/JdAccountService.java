package com.op.sdk.client.account.service;

import com.op.sdk.client.account.model.JdTokenResponse;

/**
 * 京东帐号 Service
 *
 * @author chengdr01
 */
public interface JdAccountService {

    /**
     * 请求京东token
     *
     */
    void requestAccessToken();

    /**
     * 获取京东token回调
     *
     * @param code 授权码
     */
    void callbackToken(String code);

    /**
     * 获取京东token
     *
     * @return 京东token响应
     */
    JdTokenResponse getAccessToken();
}

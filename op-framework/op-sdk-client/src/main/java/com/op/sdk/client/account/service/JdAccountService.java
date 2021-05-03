package com.op.sdk.client.account.service;

import org.springframework.web.context.request.async.DeferredResult;

/**
 * 京东帐号 Service
 *
 * @author cdrcool
 */
public interface JdAccountService {

    /**
     * 请求京东token
     *
     * @param taxpayerId     纳税人识别号
     * @param deferredResult {@link DeferredResult}
     */
    void requestAccessToken(String taxpayerId, DeferredResult<String> deferredResult);

    /**
     * 获取京东token回调
     *
     * @param code  授权码
     * @param state 回传state（需与传递给京东的保持一致）
     */
    void callbackToken(String code, String state);

    /**
     * 刷新京东token
     *
     * @param taxpayerId 纳税人识别号
     * @return 京东token
     */
    String refreshToken(String taxpayerId);

    /**
     * 获取京东token
     *
     * @param taxpayerId     纳税人识别号
     * @param deferredResult {@link DeferredResult}
     */
    void getAccessToken(String taxpayerId, DeferredResult<String> deferredResult);

    /**
     * 初始化所有的京东token
     */
    void initAllToken();

    /**
     * 刷新所有的京东token
     */
    void refreshAllToken();
}

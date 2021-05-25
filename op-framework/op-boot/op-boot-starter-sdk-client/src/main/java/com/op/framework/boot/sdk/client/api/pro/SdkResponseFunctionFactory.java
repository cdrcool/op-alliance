package com.op.framework.boot.sdk.client.api.pro;

import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.framework.boot.sdk.client.api.pro.BaseSdkResponse;
import com.op.framework.boot.sdk.client.api.pro.jd.JdResponseFunction;
import com.op.framework.boot.sdk.client.api.pro.sn.SnResponseFunction;
import com.suning.api.SuningResponse;

/**
 * SDK 响应对象 Function Factory
 *
 * @author chengdr01
 */
public interface SdkResponseFunctionFactory {

    /**
     * 获取京东 SDK 响应对象 Function
     *
     * @param actionName 动作名
     * @return 京东 SDK 请求对象 Function
     */
    <T extends AbstractResponse, R extends BaseSdkResponse> JdResponseFunction<T, R> getJdResponseFunction(String actionName);

    /**
     * 获取苏宁 SDK 响应对象 Function
     *
     * @param actionName 动作名
     * @return 苏宁 SDK 请求对象 Function
     */
    <T extends SuningResponse, R extends BaseSdkResponse> SnResponseFunction<T, R> getSnResponseFunction(String actionName);
}

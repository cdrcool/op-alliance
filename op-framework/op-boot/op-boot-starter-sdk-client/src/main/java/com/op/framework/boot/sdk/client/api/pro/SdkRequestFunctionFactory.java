package com.op.framework.boot.sdk.client.api.pro;

import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.framework.boot.sdk.client.api.pro.BaseSdkResponse;
import com.op.framework.boot.sdk.client.api.pro.jd.JdRequestFunction;
import com.op.framework.boot.sdk.client.api.pro.sn.SnRequestFunction;
import com.suning.api.SuningResponse;

/**
 * SDK 请求对象 Function Factory
 *
 * @author chengdr01
 */
public interface SdkRequestFunctionFactory {

    /**
     * 获取京东 SDK 请求对象 Function
     *
     * @param actionName 动作名
     * @return 京东 SDK 请求对象 Function
     */
    <T extends BaseSdkResponse, R extends AbstractResponse> JdRequestFunction<T, R> getJdRequestFunction(String actionName);

    /**
     * 获取苏宁 SDK 请求对象 Function
     *
     * @param actionName 动作名
     * @return 苏宁 SDK 请求对象 Function
     */
    <T extends BaseSdkResponse, R extends SuningResponse> SnRequestFunction<T, R> getSnRequestFunction(String actionName);
}

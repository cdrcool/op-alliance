package com.op.framework.boot.sdk.client.api.pro.function;

import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.framework.boot.sdk.client.api.pro.business.responsefunction.JdInvoiceSubmitApplyResponseFunction;
import com.op.framework.boot.sdk.client.api.pro.business.responsefunction.SnInvoiceSubmitApplyResponseFunction;
import com.op.framework.boot.sdk.client.api.pro.response.BaseSdkResponse;
import com.op.framework.web.common.api.response.exception.BusinessException;
import com.suning.api.SuningResponse;

import static com.op.framework.boot.sdk.client.api.pro.function.ActionNameConstants.INVOICE_APPLY_SUBMIT;

/**
 * SDK 响应对象 Function Factory
 *
 * @author cdrcool
 */
public class SdkResponseFunctionFactory {

    /**
     * 获取京东 SDK 响应对象 Function
     *
     * @param actionName 动作名
     * @return 京东 SDK 请求对象 Function
     */
    public static <T extends AbstractResponse, R extends BaseSdkResponse> JdResponseFunction<T, R> getJdResponseFunction(String actionName) {
        if (INVOICE_APPLY_SUBMIT.equals(actionName)) {
            return (JdResponseFunction<T, R>) new JdInvoiceSubmitApplyResponseFunction();
        }

        throw new BusinessException("未找到京东请求响应Function，actionName：" + actionName);
    }

    /**
     * 获取苏宁 SDK 响应对象 Function
     *
     * @param actionName 动作名
     * @return 苏宁 SDK 请求对象 Function
     */
    public static <T extends SuningResponse, R extends BaseSdkResponse> SnResponseFunction<T, R> getSnResponseFunction(String actionName){
        if (INVOICE_APPLY_SUBMIT.equals(actionName)) {
            return (SnResponseFunction<T, R>) new SnInvoiceSubmitApplyResponseFunction();
        }

        throw new BusinessException("未找到苏宁请求响应Function，actionName：" + actionName);
    }
}

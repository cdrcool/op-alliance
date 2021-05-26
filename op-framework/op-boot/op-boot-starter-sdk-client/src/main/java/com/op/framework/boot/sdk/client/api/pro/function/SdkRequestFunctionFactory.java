package com.op.framework.boot.sdk.client.api.pro.function;

import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.framework.boot.sdk.client.api.pro.business.requestfunction.JdInvoiceSubmitApplyRequestFunction;
import com.op.framework.boot.sdk.client.api.pro.business.requestfunction.SnInvoiceSubmitApplyRequestFunction;
import com.op.framework.boot.sdk.client.api.pro.response.BaseSdkResponse;
import com.op.framework.web.common.api.response.exception.BusinessException;
import com.suning.api.SuningResponse;

import static com.op.framework.boot.sdk.client.api.pro.function.ActionNameConstants.INVOICE_APPLY_SUBMIT;

/**
 * SDK 请求对象 Function Factory
 *
 * @author cdrcool
 */
public class SdkRequestFunctionFactory {

    /**
     * 获取京东 SDK 请求对象 Function
     *
     * @param actionName 动作名
     * @return 京东 SDK 请求对象 Function
     */
    public static <T extends BaseSdkResponse, R extends AbstractResponse> JdRequestFunction<T, R> getJdRequestFunction(String actionName) {
        if (INVOICE_APPLY_SUBMIT.equals(actionName)) {
            return (JdRequestFunction<T, R>) new JdInvoiceSubmitApplyRequestFunction();
        }

        throw new BusinessException("未找到京东请求对象Function，actionName：" + actionName);
    }

    /**
     * 获取苏宁 SDK 请求对象 Function
     *
     * @param actionName 动作名
     * @return 苏宁 SDK 请求对象 Function
     */
    public static <T extends BaseSdkResponse, R extends SuningResponse> SnRequestFunction<T, R> getSnRequestFunction(String actionName) {
        if (INVOICE_APPLY_SUBMIT.equals(actionName)) {
            return (SnRequestFunction<T, R>) new SnInvoiceSubmitApplyRequestFunction();
        }

        throw new BusinessException("未找到苏宁请求对象Function，actionName：" + actionName);
    }
}

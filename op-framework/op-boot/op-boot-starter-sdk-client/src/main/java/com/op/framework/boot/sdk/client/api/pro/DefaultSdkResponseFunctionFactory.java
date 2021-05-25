package com.op.framework.boot.sdk.client.api.pro;

import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.framework.boot.sdk.client.api.pro.jd.JdInvoiceSubmitApplyResponseFunction;
import com.op.framework.boot.sdk.client.api.pro.jd.JdResponseFunction;
import com.op.framework.boot.sdk.client.api.pro.sn.SnInvoiceSubmitApplyResponseFunction;
import com.op.framework.boot.sdk.client.api.pro.sn.SnResponseFunction;
import com.op.framework.web.common.api.response.exception.BusinessException;
import com.suning.api.SuningResponse;

/**
 * SDK 响应对象 Function Factory Impl
 *
 * @author chengdr01
 */
public class DefaultSdkResponseFunctionFactory implements SdkResponseFunctionFactory {
    private static final String INVOICE_APPLY_SUBMIT = "invoiceApplySubmit";


    @Override
    public <T extends AbstractResponse, R extends BaseSdkResponse> JdResponseFunction<T, R> getJdResponseFunction(String actionName) {
        if (INVOICE_APPLY_SUBMIT.equals(actionName)) {
            return (JdResponseFunction<T, R>) new JdInvoiceSubmitApplyResponseFunction();
        }

        throw new BusinessException("未找到京东请求响应Function，actionName：" + actionName);
    }

    @Override
    public <T extends SuningResponse, R extends BaseSdkResponse> SnResponseFunction<T, R> getSnResponseFunction(String actionName) {
        if (INVOICE_APPLY_SUBMIT.equals(actionName)) {
            return (SnResponseFunction<T, R>) new SnInvoiceSubmitApplyResponseFunction();
        }

        throw new BusinessException("未找到苏宁请求响应Function，actionName：" + actionName);
    }
}

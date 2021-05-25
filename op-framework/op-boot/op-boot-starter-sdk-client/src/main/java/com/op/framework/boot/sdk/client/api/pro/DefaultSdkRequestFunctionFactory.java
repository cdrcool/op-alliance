package com.op.framework.boot.sdk.client.api.pro;

import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.framework.boot.sdk.client.api.pro.jd.JdInvoiceSubmitApplyRequestFunction;
import com.op.framework.boot.sdk.client.api.pro.jd.JdRequestFunction;
import com.op.framework.boot.sdk.client.api.pro.sn.SnInvoiceSubmitApplyRequestFunction;
import com.op.framework.boot.sdk.client.api.pro.sn.SnRequestFunction;
import com.op.framework.web.common.api.response.exception.BusinessException;
import com.suning.api.SuningResponse;

/**
 * SDK 请求对象 Function Factory Impl
 *
 * @author chengdr01
 */
public class DefaultSdkRequestFunctionFactory implements SdkRequestFunctionFactory {
    private static final String INVOICE_APPLY_SUBMIT = "invoiceApplySubmit";

    @Override
    public <T extends BaseSdkResponse, R extends AbstractResponse> JdRequestFunction<T, R> getJdRequestFunction(String actionName) {
        if (INVOICE_APPLY_SUBMIT.equals(actionName)) {
            return (JdRequestFunction<T, R>) new JdInvoiceSubmitApplyRequestFunction();
        }

        throw new BusinessException("未找到京东请求对象Function，actionName：" + actionName);
    }

    @Override
    public <T extends BaseSdkResponse, R extends SuningResponse> SnRequestFunction<T, R> getSnRequestFunction(String actionName) {
        if (INVOICE_APPLY_SUBMIT.equals(actionName)) {
            return (SnRequestFunction<T, R>) new SnInvoiceSubmitApplyRequestFunction();
        }

        throw new BusinessException("未找到苏宁请求对象Function，actionName：" + actionName);
    }
}

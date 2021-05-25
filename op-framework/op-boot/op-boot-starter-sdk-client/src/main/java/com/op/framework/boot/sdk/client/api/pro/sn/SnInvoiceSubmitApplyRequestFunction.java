package com.op.framework.boot.sdk.client.api.pro.sn;

import com.op.framework.boot.sdk.client.api.pro.InvoiceApplySubmitResponse;
import com.op.framework.boot.sdk.client.api.pro.SdkRequest;
import com.suning.api.SuningRequest;
import com.suning.api.entity.govbus.InvoicesupplementConfirmResponse;

/**
 * 苏宁发票申请提交请求对象 Function
 *
 * @author cdrcool
 */
public class SnInvoiceSubmitApplyRequestFunction implements SnRequestFunction<InvoiceApplySubmitResponse, InvoicesupplementConfirmResponse> {

    @Override
    public SuningRequest<InvoicesupplementConfirmResponse> apply(SdkRequest<InvoiceApplySubmitResponse> invoiceApplySubmitResponseSdkRequest) {
        return null;
    }
}

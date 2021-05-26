package com.op.framework.boot.sdk.client.api.pro.business.requestfunction;

import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.vopfp.VopInvoiceSubmitInvoiceApplyResponse;
import com.op.framework.boot.sdk.client.api.pro.response.InvoiceApplySubmitResponse;
import com.op.framework.boot.sdk.client.api.pro.SdkRequest;
import com.op.framework.boot.sdk.client.api.pro.function.JdRequestFunction;

/**
 * 京东发票申请提交请求对象 Function
 *
 * @author cdrcool
 */
public class JdInvoiceSubmitApplyRequestFunction implements JdRequestFunction<InvoiceApplySubmitResponse, VopInvoiceSubmitInvoiceApplyResponse> {

    @Override
    public JdRequest<VopInvoiceSubmitInvoiceApplyResponse> apply(SdkRequest<InvoiceApplySubmitResponse> invoiceApplySubmitResponseSdkRequest) {
        return null;
    }
}

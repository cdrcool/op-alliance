package com.op.framework.boot.sdk.client.api.pro.jd;

import com.jd.open.api.sdk.response.vopfp.VopInvoiceSubmitInvoiceApplyResponse;
import com.op.framework.boot.sdk.client.api.pro.InvoiceApplySubmitResponse;

/**
 * 京东发票申请提交响应对象 Function
 *
 * @author cdrcool
 */
public class JdInvoiceSubmitApplyResponseFunction implements JdResponseFunction<VopInvoiceSubmitInvoiceApplyResponse, InvoiceApplySubmitResponse> {

    @Override
    public InvoiceApplySubmitResponse apply(VopInvoiceSubmitInvoiceApplyResponse vopInvoiceSubmitInvoiceApplyResponse) {
        return null;
    }
}

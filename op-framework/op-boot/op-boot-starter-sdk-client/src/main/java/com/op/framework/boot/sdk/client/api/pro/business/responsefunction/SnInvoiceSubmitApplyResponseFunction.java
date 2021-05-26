package com.op.framework.boot.sdk.client.api.pro.business.responsefunction;

import com.op.framework.boot.sdk.client.api.pro.response.InvoiceApplySubmitResponse;
import com.op.framework.boot.sdk.client.api.pro.function.SnResponseFunction;
import com.suning.api.entity.govbus.InvoicesupplementConfirmResponse;

/**
 * 京东发票申请提交响应对象 Function
 *
 * @author cdrcool
 */
public class SnInvoiceSubmitApplyResponseFunction implements SnResponseFunction<InvoicesupplementConfirmResponse, InvoiceApplySubmitResponse> {

    @Override
    public InvoiceApplySubmitResponse apply(InvoicesupplementConfirmResponse invoicesupplementConfirmResponse) {
        return null;
    }
}
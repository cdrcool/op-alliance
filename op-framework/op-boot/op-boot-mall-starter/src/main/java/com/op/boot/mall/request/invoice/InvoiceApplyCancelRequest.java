package com.op.boot.mall.request.invoice;

import com.op.boot.mall.constants.MallMethod;
import com.op.boot.mall.constants.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.invoice.InvoiceApplyCancelResponse;

/**
 * 发票申请取消请求
 *
 * @author chengdr01
 */
public class InvoiceApplyCancelRequest<P> extends MallRequest<P, InvoiceApplyCancelResponse> {

    public InvoiceApplyCancelRequest(MallType mallType, String accountName, P requestObj) {
        super(mallType, accountName, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethod.INVOICE_APPLY_CANCEL;
    }

    @Override
    public Class<InvoiceApplyCancelResponse> getResponseClass() {
        return InvoiceApplyCancelResponse.class;
    }
}

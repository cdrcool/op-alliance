package com.op.boot.mall.request.invoice;

import com.op.boot.mall.authentication.MallAuthentication;
import com.op.boot.mall.constants.MallMethod;
import com.op.boot.mall.constants.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.invoice.InvoiceApplySubmitResponse;

/**
 * 发票申请提交请求
 *
 * @author chengdr01
 */
public class InvoiceApplySubmitRequest<P> extends MallRequest<P, InvoiceApplySubmitResponse> {

    public InvoiceApplySubmitRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethod.INVOICE_APPLY_SUBMIT;
    }

    @Override
    public Class<InvoiceApplySubmitResponse> getResponseClass() {
        return InvoiceApplySubmitResponse.class;
    }
}

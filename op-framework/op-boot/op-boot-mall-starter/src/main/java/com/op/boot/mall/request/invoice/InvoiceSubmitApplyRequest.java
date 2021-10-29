package com.op.boot.mall.request.invoice;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.invoice.InvoiceSubmitApplyResponse;

/**
 * 发票提交申请请求
 *
 * @param <P> 请求对象类型
 * @author cdrcool
 */
public class InvoiceSubmitApplyRequest<P> extends MallRequest<P, InvoiceSubmitApplyResponse> {

    public InvoiceSubmitApplyRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.INVOICE_SUBMIT_APPLY;
    }

    @Override
    public Class<InvoiceSubmitApplyResponse> getResponseClass() {
        return InvoiceSubmitApplyResponse.class;
    }
}

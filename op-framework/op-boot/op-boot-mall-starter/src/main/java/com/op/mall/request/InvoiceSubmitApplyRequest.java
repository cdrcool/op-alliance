package com.op.mall.request;

import com.op.mall.client.MallAuthentication;
import com.op.mall.constans.MallMethodConstants;
import com.op.mall.constans.MallType;
import com.op.mall.response.InvoiceSubmitApplyResponse;

/**
 * 发票提交申请请求
 *
 * @author chengdr01
 */
public class InvoiceSubmitApplyRequest extends MallRequest<InvoiceSubmitApplyResponse> {

    public InvoiceSubmitApplyRequest(MallType mallType, MallAuthentication authentication, Object requestObj) {
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

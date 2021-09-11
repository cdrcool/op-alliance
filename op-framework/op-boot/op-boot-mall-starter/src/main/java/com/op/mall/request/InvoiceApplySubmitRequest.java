package com.op.mall.request;

import com.op.mall.response.InvoiceApplySubmitResponse;

/**
 * 发票申请提交请求对象
 *
 * @author chengdr01
 */
public class InvoiceApplySubmitRequest extends MallRequest<InvoiceApplySubmitResponse> {

    public InvoiceApplySubmitRequest(Object requestParams) {
        super(requestParams);
    }

    @Override
    public Class<InvoiceApplySubmitResponse> getResponseClass() {
        return InvoiceApplySubmitResponse.class;
    }
}

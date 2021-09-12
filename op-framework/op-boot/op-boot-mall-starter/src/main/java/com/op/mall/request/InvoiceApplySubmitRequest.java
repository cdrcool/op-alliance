package com.op.mall.request;

import com.op.mall.client.MallAuthentication;
import com.op.mall.response.InvoiceApplySubmitResponse;

/**
 * 发票申请提交请求对象
 *
 * @author chengdr01
 */
public class InvoiceApplySubmitRequest extends MallRequest<InvoiceApplySubmitResponse> {

    public InvoiceApplySubmitRequest(MallAuthentication authentication, Object requestObj) {
        super(authentication, requestObj);
    }

    @Override
    public Class<InvoiceApplySubmitResponse> getResponseClass() {
        return InvoiceApplySubmitResponse.class;
    }
}

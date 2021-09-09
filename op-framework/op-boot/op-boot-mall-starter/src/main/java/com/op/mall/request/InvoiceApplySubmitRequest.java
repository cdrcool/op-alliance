package com.op.mall.request;

import com.op.mall.constans.MallType;
import com.op.mall.response.InvoiceApplySubmitResponse;

import java.util.Map;

/**
 * 发票申请提交请求对象
 *
 * @author chengdr01
 */
public class InvoiceApplySubmitRequest extends MallRequest<InvoiceApplySubmitResponse> {

    protected InvoiceApplySubmitRequest(MallType mallType) {
        super(mallType);
    }

    @Override
    public String getRequestMethod() {
        return null;
    }

    @Override
    public Class<InvoiceApplySubmitResponse> getResponseClass() {
        return InvoiceApplySubmitResponse.class;
    }

    @Override
    public Map<String, Object> getRequestParams() {
        return null;
    }
}

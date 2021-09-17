package com.op.mall.request;

import com.op.mall.client.MallAuthentication;
import com.op.mall.constans.MallMethodConstants;
import com.op.mall.constans.MallType;
import com.op.mall.response.InvoiceQueryDetailResponse;

/**
 * 发票查询详情请求
 *
 * @author chengdr01
 */
public class InvoiceQueryDetailRequest extends MallRequest<InvoiceQueryDetailResponse> {

    public InvoiceQueryDetailRequest(MallType mallType, MallAuthentication authentication, Object requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.INVOICE_QUERY_DETAIL;
    }

    @Override
    public Class<InvoiceQueryDetailResponse> getResponseClass() {
        return InvoiceQueryDetailResponse.class;
    }
}

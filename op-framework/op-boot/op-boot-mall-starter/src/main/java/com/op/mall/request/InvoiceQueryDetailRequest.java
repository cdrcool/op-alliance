package com.op.mall.request;

import com.op.mall.client.MallAuthentication;
import com.op.mall.constans.MallMethodConstants;
import com.op.mall.response.InvoiceQueryDetailResponse;

/**
 * 发票查询发票详情请求
 *
 * @author chengdr01
 */
public class InvoiceQueryDetailRequest extends MallRequest<InvoiceQueryDetailResponse> {

    public InvoiceQueryDetailRequest(MallAuthentication authentication, Object requestObj) {
        super(authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.INVOICE_QUERY_DELIVERY;
    }

    @Override
    public Class<InvoiceQueryDetailResponse> getResponseClass() {
        return InvoiceQueryDetailResponse.class;
    }
}

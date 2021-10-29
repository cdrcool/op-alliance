package com.op.boot.mall.request.invoice;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.invoice.InvoiceQueryDetailResponse;

/**
 * 发票查询详情请求
 *
 * @param <P> 请求对象类型
 * @author cdrcool
 */
public class InvoiceQueryDetailRequest<P> extends MallRequest<P, InvoiceQueryDetailResponse> {

    public InvoiceQueryDetailRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
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

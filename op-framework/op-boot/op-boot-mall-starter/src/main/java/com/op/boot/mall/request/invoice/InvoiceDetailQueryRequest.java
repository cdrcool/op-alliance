package com.op.boot.mall.request.invoice;

import com.op.boot.mall.authentication.MallAuthentication;
import com.op.boot.mall.constants.MallMethod;
import com.op.boot.mall.constants.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.invoice.InvoiceDetailQueryResponse;

/**
 * 发票详情查询请求
 *
 * @author chengdr01
 */
public class InvoiceDetailQueryRequest<P> extends MallRequest<P, InvoiceDetailQueryResponse> {

    public InvoiceDetailQueryRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethod.INVOICE_DETAIL_QUERY;
    }

    @Override
    public Class<InvoiceDetailQueryResponse> getResponseClass() {
        return InvoiceDetailQueryResponse.class;
    }
}

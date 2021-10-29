package com.op.boot.mall.request.invoice;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.invoice.InvoiceQueryDeliveryResponse;

/**
 * 发票查询物流请求
 *
 * @param <P> 请求对象类型
 * @author cdrcool
 */
public class InvoiceQueryDeliveryRequest<P> extends MallRequest<P, InvoiceQueryDeliveryResponse> {

    public InvoiceQueryDeliveryRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.INVOICE_QUERY_DELIVERY;
    }

    @Override
    public Class<InvoiceQueryDeliveryResponse> getResponseClass() {
        return InvoiceQueryDeliveryResponse.class;
    }
}

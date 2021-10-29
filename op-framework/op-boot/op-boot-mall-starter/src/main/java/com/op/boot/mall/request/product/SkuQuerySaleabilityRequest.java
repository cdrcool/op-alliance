package com.op.boot.mall.request.product;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.product.SkuSaleabilityResponse;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
public class SkuQuerySaleabilityRequest<P> extends MallRequest<P, SkuSaleabilityResponse> {

    public SkuQuerySaleabilityRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.PRODUCT_QUERY_SALEABILITY;
    }

    @Override
    public Class<SkuSaleabilityResponse> getResponseClass() {
        return SkuSaleabilityResponse.class;
    }
}

package com.op.boot.mall.request.product;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.product.SkuDetailResponse;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
public class SkuQueryDetailAndCateRequest<P> extends MallRequest<P, SkuDetailResponse> {

    public SkuQueryDetailAndCateRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.PRODUCT_QUERY_DETAIL_AND_CATEGORY;
    }

    @Override
    public Class<SkuDetailResponse> getResponseClass() {
        return SkuDetailResponse.class;
    }
}

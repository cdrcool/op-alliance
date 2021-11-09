package com.op.boot.mall.request.product;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.product.SkuImageResponse;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
public class SkuQueryImageRequest<P> extends MallRequest<P, SkuImageResponse> {

    public SkuQueryImageRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.PRODUCT_QUERY_IMAGE;
    }

    @Override
    public Class<SkuImageResponse> getResponseClass() {
        return SkuImageResponse.class;
    }
}
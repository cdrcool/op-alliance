package com.op.boot.mall.request.product;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.product.SkuCategoryResponse;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
public class SkuQueryCategoryRequest<P> extends MallRequest<P, SkuCategoryResponse> {

    public SkuQueryCategoryRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.PRODUCT_QUERY_CATEGORY;
    }

    @Override
    public Class<SkuCategoryResponse> getResponseClass() {
        return SkuCategoryResponse.class;
    }
}

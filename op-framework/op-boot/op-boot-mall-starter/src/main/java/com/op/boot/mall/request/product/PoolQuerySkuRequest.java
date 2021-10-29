package com.op.boot.mall.request.product;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.product.PoolSkuResponse;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
public class PoolQuerySkuRequest<P> extends MallRequest<P, PoolSkuResponse> {

    public PoolQuerySkuRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.PRODUCT_QUERY_ID;
    }

    @Override
    public Class<PoolSkuResponse> getResponseClass() {
        return PoolSkuResponse.class;
    }
}

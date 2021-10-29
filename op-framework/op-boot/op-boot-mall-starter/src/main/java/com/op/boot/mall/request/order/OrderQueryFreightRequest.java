package com.op.boot.mall.request.order;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.order.OrderFreightResponse;

/**
 * 订单查询商品运费请求
 *
 * @param <P> 请求对象类型
 * @author cdrcool
 */
public class OrderQueryFreightRequest<P> extends MallRequest<P, OrderFreightResponse> {

    public OrderQueryFreightRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.ORDER_QUERY_FREIGHT;
    }

    @Override
    public Class<OrderFreightResponse> getResponseClass() {
        return OrderFreightResponse.class;
    }
}

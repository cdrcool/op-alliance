package com.op.boot.mall.request.order;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.order.OrderQueryStatusResponse;

/**
 * 订单查询状态请求
 *
 * @author cdrcool
 */
public class OrderQueryStatusRequest<P> extends MallRequest<P, OrderQueryStatusResponse> {

    public OrderQueryStatusRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.ORDER_QUERY_STATES;
    }

    @Override
    public Class<OrderQueryStatusResponse> getResponseClass() {
        return OrderQueryStatusResponse.class;
    }
}

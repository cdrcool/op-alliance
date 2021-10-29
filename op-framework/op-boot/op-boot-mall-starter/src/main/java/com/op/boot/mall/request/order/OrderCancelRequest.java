package com.op.boot.mall.request.order;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.order.OrderCancelResponse;

/**
 * 订单取消请求
 *
 * @author cdrcool
 */
public class OrderCancelRequest<P> extends MallRequest<P, OrderCancelResponse> {

    public OrderCancelRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.ORDER_CANCEL;
    }

    @Override
    public Class<OrderCancelResponse> getResponseClass() {
        return OrderCancelResponse.class;
    }
}

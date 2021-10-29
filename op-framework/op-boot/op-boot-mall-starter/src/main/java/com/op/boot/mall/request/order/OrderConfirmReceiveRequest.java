package com.op.boot.mall.request.order;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.order.OrderConfirmReceiveResponse;

/**
 * 确认订单收货请求
 *
 * @param <P> 请求对象类型
 * @author cdrcool
 */
public class OrderConfirmReceiveRequest<P> extends MallRequest<P, OrderConfirmReceiveResponse> {

    public OrderConfirmReceiveRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.ORDER_CONFIRM_RECEIVE;
    }

    @Override
    public Class<OrderConfirmReceiveResponse> getResponseClass() {
        return OrderConfirmReceiveResponse.class;
    }
}

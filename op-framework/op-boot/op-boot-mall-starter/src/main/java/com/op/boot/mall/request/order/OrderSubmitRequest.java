package com.op.boot.mall.request.order;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.order.OrderSubmitResponse;

/**
 * 订单提交请求
 *
 * @author cdrcool
 */
public class OrderSubmitRequest<P> extends MallRequest<P, OrderSubmitResponse> {

    public OrderSubmitRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.ORDER_SUBMIT;
    }

    @Override
    public Class<OrderSubmitResponse> getResponseClass() {
        return OrderSubmitResponse.class;
    }
}

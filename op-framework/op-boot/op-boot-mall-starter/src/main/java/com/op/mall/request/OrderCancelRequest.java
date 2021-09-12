package com.op.mall.request;

import com.op.mall.client.MallAuthentication;
import com.op.mall.constans.MallMethodConstants;
import com.op.mall.response.OrderCancelResponse;

/**
 * 订单取消请求
 *
 * @author cdrcool
 */
public class OrderCancelRequest extends MallRequest<OrderCancelResponse> {

    public OrderCancelRequest(MallAuthentication authentication, Object requestObj) {
        super(authentication, requestObj);
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

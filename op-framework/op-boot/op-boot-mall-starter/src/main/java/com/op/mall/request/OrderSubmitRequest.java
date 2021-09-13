package com.op.mall.request;

import com.op.mall.client.MallAuthentication;
import com.op.mall.constans.MallMethodConstants;
import com.op.mall.constans.MallType;
import com.op.mall.response.OrderSubmitResponse;

/**
 * 订单提交请求
 *
 * @author cdrcool
 */
public class OrderSubmitRequest extends MallRequest<OrderSubmitResponse> {

    public OrderSubmitRequest(MallType mallType, MallAuthentication authentication, Object requestObj) {
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

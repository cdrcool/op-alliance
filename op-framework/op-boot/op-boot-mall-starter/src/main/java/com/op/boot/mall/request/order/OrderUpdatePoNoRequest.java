package com.op.boot.mall.request.order;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.order.OrderUpdatePoNoResponse;

/**
 * 订单更新订单的 po 单号请求
 *
 * @param <P> 请求对象类型
 * @author cdrcool
 */
public class OrderUpdatePoNoRequest<P> extends MallRequest<P, OrderUpdatePoNoResponse> {

    public OrderUpdatePoNoRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.ORDER_UPDATE_PO_NO;
    }

    @Override
    public Class<OrderUpdatePoNoResponse> getResponseClass() {
        return OrderUpdatePoNoResponse.class;
    }
}

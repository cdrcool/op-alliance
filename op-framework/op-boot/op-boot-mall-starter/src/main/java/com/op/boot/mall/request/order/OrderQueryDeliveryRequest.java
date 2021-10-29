package com.op.boot.mall.request.order;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.order.OrderDeliveryResponse;

/**
 * 查询配送信息请求
 *
 * @param <P> 请求对象类型
 * @author cdrcool
 */
public class OrderQueryDeliveryRequest<P> extends MallRequest<P, OrderDeliveryResponse> {

    public OrderQueryDeliveryRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.ORDER_QUERY_DELIVERY;
    }

    @Override
    public Class<OrderDeliveryResponse> getResponseClass() {
        return OrderDeliveryResponse.class;
    }
}

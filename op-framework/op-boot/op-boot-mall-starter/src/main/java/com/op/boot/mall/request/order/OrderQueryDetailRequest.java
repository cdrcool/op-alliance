package com.op.boot.mall.request.order;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.order.OrderQueryDetailResponse;

/**
 * 查询订单详情请求
 *
 * @param <P> 请求对象类型
 * @author cdrcool
 */
public class OrderQueryDetailRequest<P> extends MallRequest<P, OrderQueryDetailResponse> {

    public OrderQueryDetailRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.ORDER_QUERY_DETAIL;
    }

    @Override
    public Class<OrderQueryDetailResponse> getResponseClass() {
        return OrderQueryDetailResponse.class;
    }
}

package com.op.boot.mall.request.order;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.order.OrderPageResponse;

/**
 * 订单根据订单状态分页查询请求
 *
 * @param <P> 请求对象类型
 * @author cdrcool
 */
public class OrderGetPageByStateRequest<P> extends MallRequest<P, OrderPageResponse> {

    public OrderGetPageByStateRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.ORDER_QUERY_PAGE_BY_STATE;
    }

    @Override
    public Class<OrderPageResponse> getResponseClass() {
        return OrderPageResponse.class;
    }
}

package com.op.boot.mall.request.order;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.order.OrderPredictPromiseResponse;

/**
 * 订单查询配送预计送达时间请求
 *
 * @param <P> 请求对象类型
 * @author cdrcool
 */
public class OrderQueryPromiseTipsRequest<P> extends MallRequest<P, OrderPredictPromiseResponse> {

    public OrderQueryPromiseTipsRequest(MallType mallType, MallAuthentication authentication, P requestObj) {
        super(mallType, authentication, requestObj);
    }

    @Override
    public String getRequestMethod() {
        return MallMethodConstants.ORDER_QUERY_PREDICT_PROMISE;
    }

    @Override
    public Class<OrderPredictPromiseResponse> getResponseClass() {
        return OrderPredictPromiseResponse.class;
    }
}

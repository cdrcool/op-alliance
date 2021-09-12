package com.op.mall.handler.jingdong;

import com.jd.open.api.sdk.request.vopdd.VopOrderCancelOrderRequest;
import com.jd.open.api.sdk.request.vopdd.VopOrderSubmitOrderRequest;
import com.jd.open.api.sdk.response.vopdd.VopOrderCancelOrderResponse;
import com.jd.open.api.sdk.response.vopdd.VopOrderSubmitOrderResponse;
import com.op.mall.client.jingdong.JdMallAuthentication;
import com.op.mall.client.jingdong.JdMallClient;
import com.op.mall.client.jingdong.JdMallRequest;
import com.op.mall.request.MallRequest;
import com.op.mall.request.OrderCancelRequest;
import com.op.mall.request.OrderSubmitRequest;
import com.op.mall.response.MallResponse;
import com.op.mall.response.OrderCancelResponse;
import com.op.mall.response.OrderSubmitResponse;

/**
 * 京东订单提交处理类
 *
 * @author cdrcool
 */
public class JdOrderSubmitHandler extends JdMallRequestHandler {
    public JdOrderSubmitHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public <T extends MallRequest<R>, R extends MallResponse> R handle(T request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 1. 转换为京东电商请求对象
        OrderSubmitRequest concreteRequest = (OrderSubmitRequest) request;
        Object requestObj = concreteRequest.getRequestObj();
        VopOrderSubmitOrderRequest jdRequest = (VopOrderSubmitOrderRequest) requestObj;

        // 2. 执行京东电商请求
        JdMallRequest<VopOrderSubmitOrderResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
        VopOrderSubmitOrderResponse jdResponse = getJdMallClient().execute(jdMallRequest);

        // 3. 解析为标准电商请求响应
        // 模拟京东电商请求响应解析
        OrderSubmitResponse response = new OrderSubmitResponse();
        return (R) response;
    }
}

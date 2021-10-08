package com.op.mall.handler.jingdong;

import com.jd.open.api.sdk.request.vopdd.VopOrderCancelOrderRequest;
import com.jd.open.api.sdk.response.vopdd.VopOrderCancelOrderResponse;
import com.op.mall.client.jingdong.JdMallAuthentication;
import com.op.mall.client.jingdong.JdMallClient;
import com.op.mall.client.jingdong.JdMallRequest;
import com.op.mall.constans.MallMethodConstants;
import com.op.mall.constans.MallType;
import com.op.mall.handler.MallRequestHandlerRegistry;
import com.op.mall.request.MallRequest;
import com.op.mall.request.MallRequestAction;
import com.op.mall.request.OrderCancelRequest;
import com.op.mall.response.MallResponse;
import com.op.mall.response.OrderCancelResponse;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
public class JdOrderCancelHandler extends JdMallRequestHandler {
    public JdOrderCancelHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public <T extends MallRequest<R>, R extends MallResponse> R handle(T request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 1. 转换为京东电商请求对象
        OrderCancelRequest concreteRequest = (OrderCancelRequest) request;
        Object requestObj = concreteRequest.getRequestObj();
        VopOrderCancelOrderRequest jdRequest = (VopOrderCancelOrderRequest) requestObj;

        // 2. 执行京东电商请求
        JdMallRequest<VopOrderCancelOrderResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
        VopOrderCancelOrderResponse jdResponse = getJdMallClient().execute(jdMallRequest);

        // 3. 解析为标准电商请求响应
        // 模拟京东电商请求响应解析
        OrderCancelResponse response = new OrderCancelResponse();
        return (R) response;
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.ORDER_CANCEL), this);
    }
}

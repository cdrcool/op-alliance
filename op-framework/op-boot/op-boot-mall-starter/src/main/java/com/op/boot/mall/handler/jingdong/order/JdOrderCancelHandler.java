package com.op.boot.mall.handler.jingdong.order;

import com.jd.open.api.sdk.domain.vopdd.OperaOrderOpenProvider.response.cancelOrder.OpenRpcResult;
import com.jd.open.api.sdk.request.vopdd.VopOrderCancelOrderRequest;
import com.jd.open.api.sdk.response.vopdd.VopOrderCancelOrderResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.order.OrderCancelRequest;
import com.op.boot.mall.response.order.OrderCancelResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdOrderCancelHandler extends JdMallRequestHandler<OrderCancelRequest<VopOrderCancelOrderRequest>,
        VopOrderCancelOrderRequest, OrderCancelResponse> {

    public JdOrderCancelHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public OrderCancelResponse handle(OrderCancelRequest<VopOrderCancelOrderRequest> request) {
        // 1. 获取京东请求凭证
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 2. 获取京东请求对象
        VopOrderCancelOrderRequest jdRequest = request.getRequestObj();

        // 3. 执行请求 -> 获取京东响应
        JdMallRequest<VopOrderCancelOrderResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
        VopOrderCancelOrderResponse jdResponse = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = jdResponse.getOpenRpcResult();
        if (!result.getSuccess()) {
            if (!"5002".equals(result.getResultCode())) {
                log.error("取消京东订单失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
                throw new JdMallException(result.getResultCode(), "取消京东订单失败：" + result.getResultMessage());
            }
            log.warn("取消京东订单失败，已取消订单：{}无需重复取消", jdRequest.getJdOrderId());
        }

        // 4. 转换为标准请求响应
        OrderCancelResponse response = new OrderCancelResponse();
        response.setResult(result.getResult());

        return response;
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.ORDER_CANCEL), this);
    }
}

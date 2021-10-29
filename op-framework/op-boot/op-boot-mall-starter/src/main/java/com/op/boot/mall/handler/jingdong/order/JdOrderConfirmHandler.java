package com.op.boot.mall.handler.jingdong.order;

import com.jd.open.api.sdk.domain.vopdd.OperaOrderOpenProvider.response.confirmOrder.OpenRpcResult;
import com.jd.open.api.sdk.request.vopdd.VopOrderConfirmOrderRequest;
import com.jd.open.api.sdk.response.vopdd.VopOrderConfirmOrderResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.order.OrderConfirmRequest;
import com.op.boot.mall.response.order.OrderConfirmResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 京东订单确认处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdOrderConfirmHandler extends JdMallRequestHandler<OrderConfirmRequest<VopOrderConfirmOrderRequest>,
        VopOrderConfirmOrderRequest, OrderConfirmResponse> {

    public JdOrderConfirmHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public OrderConfirmResponse handle(OrderConfirmRequest<VopOrderConfirmOrderRequest> request) {
        // 1. 获取京东请求凭证
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 2. 获取京东请求对象
        VopOrderConfirmOrderRequest jdRequest = request.getRequestObj();

        // 3. 执行请求 -> 获取京东响应
        JdMallRequest<VopOrderConfirmOrderResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
        VopOrderConfirmOrderResponse jdResponse = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = jdResponse.getOpenRpcResult();
        if (!result.getSuccess()) {
            if (!"5002".equals(result.getResultCode())) {
                log.error("确认京东订单失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
                throw new JdMallException(result.getResultCode(), "确认京东订单失败：" + result.getResultMessage());
            }
            log.warn("确认京东订单失败，已确认订单：{}无需重复取消", jdRequest.getJdOrderId());
        }

        // 4. 转换为标准请求响应
        OrderConfirmResponse response = new OrderConfirmResponse();
        response.setResult(result.getResult());

        return response;
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.ORDER_CONFIRM), this);
    }
}

package com.op.boot.mall.handler.jingdong.order;

import com.jd.open.api.sdk.domain.vopdd.OperaOrderOpenProvider.response.confirmReceiveByOrder.OpenRpcResult;
import com.jd.open.api.sdk.request.vopdd.VopOrderConfirmReceiveByOrderRequest;
import com.jd.open.api.sdk.response.vopdd.VopOrderConfirmReceiveByOrderResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.order.OrderConfirmReceiveRequest;
import com.op.boot.mall.response.order.OrderConfirmReceiveResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 京东确认订单收货处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdOrderConfirmReceiveHandler extends JdMallRequestHandler<OrderConfirmReceiveRequest<VopOrderConfirmReceiveByOrderRequest>,
        VopOrderConfirmReceiveByOrderRequest, OrderConfirmReceiveResponse> {

    public JdOrderConfirmReceiveHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public OrderConfirmReceiveResponse handle(OrderConfirmReceiveRequest<VopOrderConfirmReceiveByOrderRequest> request) {
        // 1. 获取京东请求凭证
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 2. 获取京东请求对象
        VopOrderConfirmReceiveByOrderRequest jdRequest = request.getRequestObj();

        // 3. 执行请求 -> 获取京东响应
        JdMallRequest<VopOrderConfirmReceiveByOrderResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
        VopOrderConfirmReceiveByOrderResponse jdResponse = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = jdResponse.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("确认京东订单收货失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdMallException(result.getResultCode(), "确认京东订单收货失败：" + result.getResultMessage());
        }

        // 4. 转换为标准请求响应
        OrderConfirmReceiveResponse response = new OrderConfirmReceiveResponse();
        response.setResult(result.getResult());

        return response;
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.ORDER_CONFIRM_RECEIVE), this);
    }
}

package com.op.boot.mall.handler.jingdong.order;

import com.jd.open.api.sdk.domain.vopdd.OperaOrderOpenProvider.response.updatePoNoByOrder.OpenRpcResult;
import com.jd.open.api.sdk.request.vopdd.VopOrderUpdatePoNoByOrderRequest;
import com.jd.open.api.sdk.response.vopdd.VopOrderUpdatePoNoByOrderResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.order.OrderUpdatePoNoRequest;
import com.op.boot.mall.response.order.OrderUpdatePoNoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 订单更新订单的 po 单号处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdOrderUpdatePoNoHandler extends JdMallRequestHandler<OrderUpdatePoNoRequest<VopOrderUpdatePoNoByOrderRequest>,
        VopOrderUpdatePoNoByOrderRequest, OrderUpdatePoNoResponse> {

    public JdOrderUpdatePoNoHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public OrderUpdatePoNoResponse handle(OrderUpdatePoNoRequest<VopOrderUpdatePoNoByOrderRequest> request) {
        // 1. 获取京东请求凭证
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 2. 获取京东请求对象
        VopOrderUpdatePoNoByOrderRequest jdRequest = request.getRequestObj();

        // 3. 执行请求 -> 获取京东响应
        JdMallRequest<VopOrderUpdatePoNoByOrderResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
        VopOrderUpdatePoNoByOrderResponse jdResponse = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = jdResponse.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("更新京东订单的 po 单号失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdMallException(result.getResultCode(), "更新京东订单的 po 单号失败：" + result.getResultMessage());
        }

        // 4. 转换为标准请求响应
        OrderUpdatePoNoResponse response = new OrderUpdatePoNoResponse();
        response.setResult(result.getResult());

        return response;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.ORDER_UPDATE_PO_NO), this);
    }
}

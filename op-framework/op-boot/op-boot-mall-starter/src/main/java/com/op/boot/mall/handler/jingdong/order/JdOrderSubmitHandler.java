package com.op.boot.mall.handler.jingdong.order;

import com.jd.open.api.sdk.domain.vopdd.OperaOrderOpenProvider.response.submitOrder.PriceOrderOpenResp;
import com.jd.open.api.sdk.domain.vopdd.OperaOrderOpenProvider.response.submitOrder.QueryOrderOpenResp;
import com.jd.open.api.sdk.domain.vopdd.OperaOrderOpenProvider.response.submitOrder.VopOrderRpcResult;
import com.jd.open.api.sdk.request.vopdd.VopOrderSubmitOrderRequest;
import com.jd.open.api.sdk.response.vopdd.VopOrderSubmitOrderResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.order.OrderSubmitRequest;
import com.op.boot.mall.response.order.OrderSubmitResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 京东订单提交处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdOrderSubmitHandler extends JdMallRequestHandler<OrderSubmitRequest<VopOrderSubmitOrderRequest>,
        VopOrderSubmitOrderRequest, OrderSubmitResponse> {

    public JdOrderSubmitHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public OrderSubmitResponse handle(OrderSubmitRequest<VopOrderSubmitOrderRequest> request) {
        // 1. 获取京东请求凭证
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 2. 获取京东请求对象
        VopOrderSubmitOrderRequest jdRequest = request.getRequestObj();

        // 3. 执行请求 -> 获取京东响应
        JdMallRequest<VopOrderSubmitOrderResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
        VopOrderSubmitOrderResponse jdResponse = getJdMallClient().execute(jdMallRequest);
        VopOrderRpcResult vopOrderRpcResult = jdResponse.getVopOrderRpcResult();
        if (!vopOrderRpcResult.getSuccess()) {
            log.error("提交京东订单失败，错误码：【{}】，错误消息【{}】", vopOrderRpcResult.getResultCode(), vopOrderRpcResult.getResultMessage());
            throw new JdMallException(vopOrderRpcResult.getResultCode(), "提交京东订单失败：" + vopOrderRpcResult.getResultMessage());
        }
        QueryOrderOpenResp result = vopOrderRpcResult.getResult();

        // 4. 转换为标准请求响应
        OrderSubmitResponse response = new OrderSubmitResponse();
        response.setOrderId(String.valueOf(result.getJdOrderId()));

        // 4.1 填充费用信息
        PriceOrderOpenResp orderPrice = result.getOrderPrice();
        if (orderPrice != null) {
            response.setFreight(orderPrice.getOrderTotalFreight());
            response.setOrderPrice(orderPrice.getOrderTotalPrice());
            response.setOrderNakedPrice(orderPrice.getOrderNakedPrice());
            response.setOrderTaxPrice(orderPrice.getOrderTaxPrice());
        }

        // 4.2 填充 sku 信息
        response.setOrderSubmitSkuResponseList(Optional.ofNullable(result.getSkuInfoList())
                .orElse(new ArrayList<>()).stream()
                .map(skuInfo -> {
                    OrderSubmitResponse.OrderSubmitSkuResponse skuResponse = new OrderSubmitResponse.OrderSubmitSkuResponse();
                    skuResponse.setSkuId(String.valueOf(skuInfo.getSkuId()));
                    skuResponse.setNum(skuInfo.getSkuNum());
                    skuResponse.setCategory((int) skuInfo.getSkuCategoryThird());
                    skuResponse.setPrice(skuInfo.getSkuPrice());
                    skuResponse.setName(skuInfo.getSkuName());
                    skuResponse.setTax(skuInfo.getSkuTaxRate());
                    skuResponse.setTaxPrice(skuInfo.getSkuTaxPrice());
                    skuResponse.setNakedPrice(skuInfo.getSkuNakedPrice());

                    return skuResponse;
                })
                .collect(Collectors.toList()));

        return response;
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.ORDER_SUBMIT), this);
    }
}

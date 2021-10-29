package com.op.boot.mall.handler.jingdong.order;

import com.jd.open.api.sdk.domain.vopdd.QueryOrderOpenProvider.response.queryDeliveryInfo.DeliveryInfoQueryOpenResp;
import com.jd.open.api.sdk.domain.vopdd.QueryOrderOpenProvider.response.queryDeliveryInfo.OpenRpcResult;
import com.jd.open.api.sdk.request.vopdd.VopOrderQueryDeliveryInfoRequest;
import com.jd.open.api.sdk.response.vopdd.VopOrderQueryDeliveryInfoResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.order.OrderQueryDeliveryRequest;
import com.op.boot.mall.response.order.OrderDeliveryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * 京东查询配送信息处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdOrderQueryDeliveryHandler extends JdMallRequestHandler<OrderQueryDeliveryRequest<VopOrderQueryDeliveryInfoRequest>,
        VopOrderQueryDeliveryInfoRequest, OrderDeliveryResponse> {

    public JdOrderQueryDeliveryHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public OrderDeliveryResponse handle(OrderQueryDeliveryRequest<VopOrderQueryDeliveryInfoRequest> request) {
        // 1. 获取京东请求凭证
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 2. 获取京东请求对象
        VopOrderQueryDeliveryInfoRequest vopOrderQueryDeliveryInfoRequest = request.getRequestObj();

        // 3. 执行请求 -> 获取京东响应
        JdMallRequest<VopOrderQueryDeliveryInfoResponse> jdMallRequest = new JdMallRequest<>(authentication, vopOrderQueryDeliveryInfoRequest);
        VopOrderQueryDeliveryInfoResponse response = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = response.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("查询京东配送信息失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdMallException(result.getResultCode(), "查询京东配送信息失败：" + result.getResultMessage());
        }
        DeliveryInfoQueryOpenResp deliveryInfoQueryOpenResp = result.getResult();

        // 4. 转换为标准请求响应
        OrderDeliveryResponse orderDeliveryResponse = new OrderDeliveryResponse();
        orderDeliveryResponse.setJdOrderId(String.valueOf(deliveryInfoQueryOpenResp.getJdOrderId()));

        orderDeliveryResponse.setOrderTrack(deliveryInfoQueryOpenResp.getTrackInfoList().stream()
                .map(trackInfo -> {
                    OrderDeliveryResponse.OrderTrackDetailResponse detailResponse = new OrderDeliveryResponse.OrderTrackDetailResponse();
                    detailResponse.setContent(trackInfo.getTrackContent());
                    detailResponse.setMsgTime(trackInfo.getTrackMsgTime());
                    detailResponse.setOperator(trackInfo.getTrackOperator());

                    return detailResponse;
                }).collect(Collectors.toList()));

        orderDeliveryResponse.setWaybillCode(deliveryInfoQueryOpenResp.getLogisticInfoList().stream()
                .map(logisticInfo -> {
                    OrderDeliveryResponse.OrderTrackBillCodeResponse billCodeResponse = new OrderDeliveryResponse.OrderTrackBillCodeResponse();
                    billCodeResponse.setOrderId(logisticInfo.getJdOrderId());
                    billCodeResponse.setParentId(logisticInfo.getParentJdOrderId());
                    billCodeResponse.setCarrier(logisticInfo.getDeliveryCarrier());
                    billCodeResponse.setDeliveryOrderId(logisticInfo.getDeliveryOrderId());

                    return billCodeResponse;
                })
                .collect(Collectors.toList()));

        return orderDeliveryResponse;
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.ORDER_QUERY_DELIVERY), this);
    }
}

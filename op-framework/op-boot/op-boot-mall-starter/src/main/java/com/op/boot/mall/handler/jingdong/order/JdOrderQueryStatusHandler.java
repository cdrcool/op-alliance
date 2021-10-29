package com.op.boot.mall.handler.jingdong.order;

import com.jd.open.api.sdk.domain.vopdd.QueryOrderOpenProvider.response.queryOrderDetail.OpenRpcResult;
import com.jd.open.api.sdk.domain.vopdd.QueryOrderOpenProvider.response.queryOrderDetail.QueryOrderOpenResp;
import com.jd.open.api.sdk.domain.vopdd.QueryOrderOpenProvider.response.queryOrderDetail.StateOrderOpenResp;
import com.jd.open.api.sdk.request.vopdd.VopOrderQueryOrderDetailRequest;
import com.jd.open.api.sdk.response.vopdd.VopOrderQueryOrderDetailResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.order.OrderQueryStatusRequest;
import com.op.boot.mall.response.order.OrderQueryStatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 京东查询订单状态处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdOrderQueryStatusHandler extends JdMallRequestHandler<OrderQueryStatusRequest<VopOrderQueryOrderDetailRequest>,
        VopOrderQueryOrderDetailRequest, OrderQueryStatusResponse> {

    public JdOrderQueryStatusHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public OrderQueryStatusResponse handle(OrderQueryStatusRequest<VopOrderQueryOrderDetailRequest> request) {
        // 1. 获取京东请求凭证
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 2. 获取京东请求对象
        VopOrderQueryOrderDetailRequest vopOrderQueryOrderDetailRequest = request.getRequestObj();

        // 3. 执行请求 -> 获取京东响应
        JdMallRequest<VopOrderQueryOrderDetailResponse> jdMallRequest = new JdMallRequest<>(authentication, vopOrderQueryOrderDetailRequest);
        VopOrderQueryOrderDetailResponse jdResponse = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = jdResponse.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("确认京东订单收货失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdMallException(result.getResultCode(), "确认京东订单收货失败：" + result.getResultMessage());
        }
        QueryOrderOpenResp resp = result.getResult().get(0);
        StateOrderOpenResp stateOrderOpenResp = resp.getOrderState();

        // 4. 转换为标准请求响应
        OrderQueryStatusResponse orderStatus = new OrderQueryStatusResponse();
        orderStatus.setConfirmState(stateOrderOpenResp.getConfirmState());
        orderStatus.setCancelOrderState(stateOrderOpenResp.getCancelOrderState());
        orderStatus.setDeliveryState(stateOrderOpenResp.getDeliveryState());
        orderStatus.setOrderState(stateOrderOpenResp.getJdOrderState());
        orderStatus.setOrderStateName(getOrderStatusName(stateOrderOpenResp.getJdOrderState()));

        return orderStatus;
    }

    public String getOrderStatusName(Integer orderStatus) {
        switch (orderStatus) {
            case 1:
                return "新单";
            case 2:
                return "等待支付";
            case 3:
                return "等待支付确认";
            case 4:
                return "延迟付款确认";
            case 5:
                return "订单暂停";
            case 6:
                return "店长最终审核";
            case 7:
                return "等待打印";
            case 8:
                return "等待出库";
            case 9:
                return "等待打包";
            case 10:
                return "等待发货";
            case 11:
                return "自提途中";
            case 12:
                return "上门提货";
            case 13:
                return "自提退货";
            case 14:
                return "确认自提";
            case 16:
                return "等待确认收货";
            case 17:
                return "配送退货";
            case 18:
                return "货到付款确认";
            case 19:
                return "已完成";
            case 21:
                return "收款确认";
            case 22:
                return "锁定";
            case 29:
                return "等待三方出库";
            case 30:
                return "等待三方发货";
            case 31:
                return "等待三方发货完成";
            default:
                return "未知";
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.ORDER_QUERY_STATES), this);
    }
}

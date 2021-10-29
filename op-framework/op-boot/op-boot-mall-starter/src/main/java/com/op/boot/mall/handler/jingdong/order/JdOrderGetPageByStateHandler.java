package com.op.boot.mall.handler.jingdong.order;

import com.jd.open.api.sdk.domain.vopdd.QueryOrderOpenProvider.response.checkOrderPageByState.OpenPagingResult;
import com.jd.open.api.sdk.domain.vopdd.QueryOrderOpenProvider.response.checkOrderPageByState.OpenRpcResult;
import com.jd.open.api.sdk.request.vopdd.VopOrderCheckOrderPageByStateRequest;
import com.jd.open.api.sdk.response.vopdd.VopOrderCheckOrderPageByStateResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.order.OrderGetPageByStateRequest;
import com.op.boot.mall.response.order.OrderPageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 订单根据订单状态分页查询处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdOrderGetPageByStateHandler extends JdMallRequestHandler<OrderGetPageByStateRequest<VopOrderCheckOrderPageByStateRequest>,
        VopOrderCheckOrderPageByStateRequest, OrderPageResponse> {

    public JdOrderGetPageByStateHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public OrderPageResponse handle(OrderGetPageByStateRequest<VopOrderCheckOrderPageByStateRequest> request) {
        // 1. 获取京东请求凭证
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 2. 获取京东请求对象
        VopOrderCheckOrderPageByStateRequest jdRequest = request.getRequestObj();

        // 3. 执行请求 -> 获取京东响应
        JdMallRequest<VopOrderCheckOrderPageByStateResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
        VopOrderCheckOrderPageByStateResponse jdResponse = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = jdResponse.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("根据京东订单状态分页查询失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdMallException(result.getResultCode(), "根据京东订单状态分页查询失败：" + result.getResultMessage());
        }
        OpenPagingResult pagingResult = result.getResult();

        // 4. 转换为标准请求响应
        OrderPageResponse response = new OrderPageResponse();
        response.setTotal((int) pagingResult.getPageItemTotal());
        response.setTotalPage(pagingResult.getPageTotal());
        response.setCurPage(pagingResult.getPageIndex());
        response.setOrderItemResponseList(Optional.ofNullable(pagingResult.getItems()).orElse(new ArrayList<>()).stream()
                .map(item -> {
                    OrderPageResponse.OrderItemResponse orderItemResponse = new OrderPageResponse.OrderItemResponse();
                    orderItemResponse.setOrderId(item.getJdOrderId());
                    orderItemResponse.setState(item.getJdOrderState());
                    orderItemResponse.setInvoiceState(item.getInvoicePutType());
                    orderItemResponse.setOrderPrice(item.getOrderTotalPrice());
                    orderItemResponse.setTime(item.getOrderCreateTime());

                    return orderItemResponse;
                })
                .collect(Collectors.toList()));

        return response;
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.ORDER_QUERY_PAGE_BY_STATE), this);
    }
}

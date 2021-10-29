package com.op.boot.mall.handler.jingdong.order;

import com.jd.open.api.sdk.domain.vopdd.QueryOrderOpenProvider.response.queryOrderDetail.OpenRpcResult;
import com.jd.open.api.sdk.domain.vopdd.QueryOrderOpenProvider.response.queryOrderDetail.QueryOrderOpenResp;
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
import com.op.boot.mall.request.order.OrderQueryDetailRequest;
import com.op.boot.mall.response.order.OrderQueryDetailResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 京东查询订单详情处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdOrderQueryDetailHandler extends JdMallRequestHandler<OrderQueryDetailRequest<VopOrderQueryOrderDetailRequest>,
        VopOrderQueryOrderDetailRequest, OrderQueryDetailResponse> {

    public JdOrderQueryDetailHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public OrderQueryDetailResponse handle(OrderQueryDetailRequest<VopOrderQueryOrderDetailRequest> request) {
        // 1. 获取京东请求凭证
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 2. 获取京东请求对象
        VopOrderQueryOrderDetailRequest vopOrderQueryOrderDetailRequest = request.getRequestObj();

        // 3. 执行请求 -> 获取京东响应
        JdMallRequest<VopOrderQueryOrderDetailResponse> jdMallRequest = new JdMallRequest<>(authentication, vopOrderQueryOrderDetailRequest);
        VopOrderQueryOrderDetailResponse jdResponse = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = jdResponse.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("查询京东订单详情失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdMallException(result.getResultCode(), "确认京东订单收货失败：" + result.getResultMessage());
        }
        List<QueryOrderOpenResp> respList = result.getResult();
        if (CollectionUtils.isEmpty(respList)) {
            log.error("查询京东订单详情失败：未查询到订单信息");
            throw new JdMallException("查询京东订单详情失败：未查询到订单信息");
        }

        QueryOrderOpenResp resp = respList.get(0);

        // 4. 转换为标准请求响应
        OrderQueryDetailResponse response = buildResponse(resp);

        // 如果是父订单，需要处理以下逻辑
        if (response.getType() == 1) {
            // 1.组装父单信息
            Map<String, Object> json = new HashMap<>(8);
            json.put("jdOrderId", response.getOrderId());
            json.put("sku", response.getSku());
            json.put("freight", response.getFreight());
            json.put("orderPrice", response.getOrderPrice());
            json.put("orderNakedPrice", response.getOrderNakedPrice());
            json.put("orderTaxPrice", response.getOrderTaxPrice());
            response.setParentOrder(json);

            // 2.查询并组装子单信息
            List<OrderQueryDetailResponse> childOrder = new ArrayList<>();
            if (resp.getChildJdOrderIdList() != null && resp.getChildJdOrderIdList().size() > 0) {
                childOrder = result.getResult().stream().filter(o -> o.getParentType() != 1).map(this::buildResponse).collect(Collectors.toList());
            }
            response.setChildOrder(childOrder);
        } else {
            response.setParentOrder(resp.getJdOrderId());
        }

        return response;
    }

    private OrderQueryDetailResponse buildResponse(QueryOrderOpenResp resp) {
        OrderQueryDetailResponse response = new OrderQueryDetailResponse();
        response.setOrderId(resp.getJdOrderId());
        response.setState(resp.getOrderState().getDeliveryState());
        response.setCancelState(resp.getOrderState().getCancelOrderState());
        response.setConfirmState(resp.getOrderState().getConfirmState());
        response.setType(resp.getParentType() == 1 ? 1 : 2);

        response.setSku(resp.getSkuInfoList().stream()
                .map(t -> {
                    OrderQueryDetailResponse.OrderSkuResponse skuResponse = new OrderQueryDetailResponse.OrderSkuResponse();
                    skuResponse.setName(t.getSkuName());
                    skuResponse.setSkuId(t.getSkuId());
                    skuResponse.setNum(t.getSkuNum());
                    skuResponse.setCategory((int) t.getSkuCategoryThird());
                    skuResponse.setPrice(t.getSkuPrice());
                    skuResponse.setTax(t.getSkuTaxRate());
                    /*
                     * TODO:需要京东确认一下新的sdk方式这三个字段是不是不需要同步
                     *  跟开通的账号有关
                     */
                    skuResponse.setOid(0L);
                    skuResponse.setType(0);
                    skuResponse.setSplitFreight(0);
                    skuResponse.setTaxPrice(t.getSkuTaxPrice());
                    skuResponse.setNakedPrice(t.getSkuNakedPrice());

                    return skuResponse;
                })
                .collect(Collectors.toList()));

        response.setFreight(resp.getOrderPrice().getOrderTotalFreight());
        response.setOrderPrice(resp.getOrderPrice().getOrderTotalPrice());
        response.setOrderNakedPrice(resp.getOrderPrice().getOrderNakedPrice());
        response.setOrderTaxPrice(resp.getOrderPrice().getOrderTaxPrice());
        response.setOrderType(resp.getOrderType());
        response.setCreateOrderTime(resp.getOrderCreateTime());
        response.setFinishTime(resp.getOrderFinishTime());
        response.setOrderState(resp.getOrderState().getJdOrderState());
        response.setPaymentType(resp.getOrderPaymentInfo().getPaymentType());

        if (resp.getOrderDeliveryInfo() != null) {
            response.setOutTime(resp.getOrderDeliveryInfo().getDeliveryOutTime());
        }
        if (resp.getOrderInvoiceInfo() != null) {
            response.setInvoiceType(resp.getOrderInvoiceInfo().getSubmitInvoiceType());
        }

        return response;
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.ORDER_QUERY_DETAIL), this);
    }
}


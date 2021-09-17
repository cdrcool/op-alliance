package com.op.mall.business.mall.requestbuilder;

import com.op.mall.business.dto.OrderSubmitDTO;
import com.op.mall.constans.MallType;
import com.op.mall.request.OrderCancelRequest;
import com.op.mall.request.OrderSubmitRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 电商订单请求构造者 proxy
 *
 * @author chengdr01
 */
public class MallOrderRequestBuilderProxy {
    private final List<MallOrderRequestBuilder> orderRequestBuilders;

    public MallOrderRequestBuilderProxy(List<MallOrderRequestBuilder> orderRequestBuilders) {
        this.orderRequestBuilders = orderRequestBuilders;
    }

    /**
     * 构建订单提交请求
     *
     * @param orderSubmitDTO 订单提交 DTO
     * @param supplierMap    扩展信息 supplier
     * @return 订单提交请求
     */
    public OrderSubmitRequest buildOrderSubmitRequest(String mallType, OrderSubmitDTO orderSubmitDTO, Map<String, Supplier<Object>> supplierMap) {
        return getMallOrderRequestBuilder(mallType).map(builder -> builder.buildOrderSubmitRequest(orderSubmitDTO, supplierMap)).orElse(null);
    }

    /**
     * 构建订单取消请求
     *
     * @param taxpayerId   纳税人识别号
     * @param thirdOrderId 第三方订单号
     * @param supplierMap  扩展信息 supplier
     * @return 订单提交请求
     */
    public OrderCancelRequest buildOrderCancelRequest(String mallType, String taxpayerId, Long thirdOrderId, Map<String, Supplier<Object>> supplierMap) {
        return getMallOrderRequestBuilder(mallType).map(builder -> builder.buildOrderCancelRequest(taxpayerId, thirdOrderId, supplierMap)).orElse(null);
    }

    private Optional<MallOrderRequestBuilder> getMallOrderRequestBuilder(String mallType) {
        return orderRequestBuilders.stream().filter(builder -> builder.supports(MallType.getMallType(mallType))).findAny();
    }
}

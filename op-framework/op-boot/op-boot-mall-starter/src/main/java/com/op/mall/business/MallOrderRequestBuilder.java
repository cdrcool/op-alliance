package com.op.mall.business;

import com.op.mall.client.MallAuthenticationManager;
import com.op.mall.request.OrderCancelRequest;
import com.op.mall.request.OrderSubmitRequest;

import java.util.Map;
import java.util.function.Supplier;

/**
 * 电商订单请求提供者
 *
 * @author chengdr01
 */
public abstract class MallOrderRequestBuilder extends MallRequestBuilder {

    public MallOrderRequestBuilder(MallAuthenticationManager mallAuthenticationManager) {
        super(mallAuthenticationManager);
    }

    /**
     * 构建订单提交请求
     *
     * @param orderSubmitDTO 订单提交 DTO
     * @param supplierMap    扩展信息 supplier
     * @return 订单提交请求
     */
    public abstract OrderSubmitRequest buildOrderSubmitRequest(OrderSubmitDTO orderSubmitDTO, Map<String, Supplier<Object>> supplierMap);

    /**
     * 构建订单取消请求
     *
     * @param taxpayerId   纳税人识别号
     * @param thirdOrderId 第三方订单号
     * @param supplierMap  扩展信息 supplier
     * @return 订单提交请求
     */
    public abstract OrderCancelRequest buildOrderCancelRequest(String taxpayerId, Long thirdOrderId, Map<String, Supplier<Object>> supplierMap);
}

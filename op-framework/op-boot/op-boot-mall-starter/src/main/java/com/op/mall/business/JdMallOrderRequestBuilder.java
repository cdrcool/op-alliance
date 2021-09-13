package com.op.mall.business;

import com.op.mall.client.MallAuthentication;
import com.op.mall.client.MallAuthenticationManager;
import com.op.mall.constans.MallType;
import com.op.mall.request.OrderCancelRequest;
import com.op.mall.request.OrderSubmitRequest;

import java.util.Map;
import java.util.function.Supplier;

/**
 * 京东电商订单请求提供者
 *
 * @author chengdr01
 */
public class JdMallOrderRequestBuilder extends MallOrderRequestBuilder {

    public JdMallOrderRequestBuilder(MallAuthenticationManager mallAuthenticationManager) {
        super(mallAuthenticationManager);
    }

    @Override
    public OrderSubmitRequest buildOrderSubmitRequest(OrderSubmitDTO orderSubmitDTO, Map<String, Supplier<Object>> supplierMap) {
        // 获取京东电商身份认证凭据
        MallAuthentication mallAuthentication = getMallAuthenticationManager().loadAuthentication(MallType.JINGDONG, orderSubmitDTO.getTaxpayerId());

        return new OrderSubmitRequest(MallType.JINGDONG, mallAuthentication, null);
    }

    @Override
    public OrderCancelRequest buildOrderCancelRequest(String taxpayerId, Long thirdOrderId, Map<String, Supplier<Object>> supplierMap) {
        // 获取京东电商身份认证凭据
        MallAuthentication mallAuthentication = getMallAuthenticationManager().loadAuthentication(MallType.JINGDONG, taxpayerId);

        return new OrderCancelRequest(MallType.JINGDONG, mallAuthentication, null);
    }

    @Override
    public boolean supports(MallType mallType) {
        return false;
    }
}

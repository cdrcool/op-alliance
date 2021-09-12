package com.op.mall.business;

import com.jd.open.api.sdk.request.vopdd.VopOrderCancelOrderRequest;
import com.op.mall.MallRequestExecutor;
import com.op.mall.client.MallAuthentication;
import com.op.mall.client.MallAuthenticationProvider;
import com.op.mall.constans.MallType;
import com.op.mall.exception.MallException;
import com.op.mall.request.OrderCancelRequest;
import com.op.mall.request.OrderSubmitRequest;
import com.op.mall.response.MallResponse;
import com.op.mall.response.OrderSubmitResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 订单 Service Impl
 *
 * @author cdrcool
 */
@Slf4j
public class OrderServiceImpl implements OrderService {
    /**
     * 电商身份认证凭据管理者
     */
    private final MallAuthenticationProvider mallAuthenticationProvider;

    /**
     * 电商请求执行者
     */
    private final MallRequestExecutor mallRequestExecutor;

    public OrderServiceImpl(MallAuthenticationProvider mallAuthenticationProvider, MallRequestExecutor mallRequestExecutor) {
        this.mallAuthenticationProvider = mallAuthenticationProvider;
        this.mallRequestExecutor = mallRequestExecutor;
    }

    @Override
    public OrderSubmitVO submitOrder(OrderSubmitDTO submitDTO) {
        // 1. 执行业务校验

        // 2. 获取并校验其他相关信息

        // 3. 构造订单提交请求的提供者 map
        Map<String, Supplier<OrderSubmitRequest>> requestSupplierMap = new HashMap<>(8);
        requestSupplierMap.put(MallType.JINGDONG.getValue(), () -> buildJdOrderSubmitRequest(submitDTO));
        requestSupplierMap.put(MallType.SUNING.getValue(), () -> buildSnOrderSubmitRequest(submitDTO));

        // 4. 发起订单提交请求
        List<String> mallTypes = submitDTO.getSkuInfoList().stream().map(OrderSubmitDTO.SkuInfo::getMallType).distinct().collect(Collectors.toList());
        List<OrderSubmitResponse> responses = mallRequestExecutor.executeConcurrent(mallTypes, requestSupplierMap);

        // 5. 如果存在任一电商提交订单失败，就回滚所有电商成功的提交订单
        List<OrderSubmitResponse> successResponses = responses.stream().filter(MallResponse::isSuccess).collect(Collectors.toList());
        if (successResponses.size() < mallTypes.size()) {
            if (successResponses.size() > 0) {
                // 5.1. 构造订单取消请求的提供者 map
                Map<String, Supplier<OrderCancelRequest>> cancelRequestSupplierMap = new HashMap<>(8);

                List<String> undefinedMallTypes = new ArrayList<>();
                successResponses.forEach(response -> {
                    String mallType = response.getMallType();
                    cancelRequestSupplierMap.put(mallType, () -> {
                        if (MallType.JINGDONG.getValue().equals(mallType)) {
                            return buildJdOrderCancelRequest(submitDTO.getTaxpayerId(), response.getThirdOrderId());
                        }
                        if (MallType.SUNING.getValue().equals(mallType)) {
                            return buildSnOrderCancelRequest(submitDTO.getTaxpayerId(), response.getThirdOrderId());
                        }
                        log.error("未定义订单取消请求提供者，电商类型：{}", response.getMallType());
                        undefinedMallTypes.add(mallType);
                        return null;
                    });
                });

                if (undefinedMallTypes.size() > 0) {
                    throw new MallException("提交订单失败，且存在电商订单提交成功但执行取消回滚操作失败，电商类型：" + undefinedMallTypes);
                }

                // 5.2. 发起订单取消请求（尚未实现订单取消请求失败后的补偿机制）
                List<String> successMallTypes = successResponses.stream().map(MallResponse::getMallType).collect(Collectors.toList());
                mallRequestExecutor.executeConcurrent(successMallTypes, cancelRequestSupplierMap);
            } else {
                String message = "提交订单失败";
                log.error(message);
                throw new MallException(message);
            }
        }

        // 6. 转换请求响应（List<OrderSubmitResponse> -> OrderSubmitVO）
        return null;
    }

    private OrderSubmitRequest buildJdOrderSubmitRequest(OrderSubmitDTO submitDTO, Object... otherArgs) {
        // 获取京东电商身份认证凭据
        MallAuthentication mallAuthentication = mallAuthenticationProvider.getAuthentication(submitDTO.getTaxpayerId());

        return new OrderSubmitRequest(mallAuthentication, null);
    }

    private OrderCancelRequest buildJdOrderCancelRequest(String taxpayerId, Long thirdOrderId) {
        // 获取京东电商身份认证凭据
        MallAuthentication mallAuthentication = mallAuthenticationProvider.getAuthentication(taxpayerId);

        VopOrderCancelOrderRequest jdRequest = new VopOrderCancelOrderRequest();
        jdRequest.setJdOrderId(thirdOrderId);

        return new OrderCancelRequest(mallAuthentication, jdRequest);
    }

    private OrderSubmitRequest buildSnOrderSubmitRequest(OrderSubmitDTO submitDTO, Object... otherArgs) {
        return new OrderSubmitRequest(null, null);
    }

    private OrderCancelRequest buildSnOrderCancelRequest(String taxpayerId, Long thirdOrderId) {
        return new OrderCancelRequest(null, null);
    }
}

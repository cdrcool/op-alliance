package com.op.mall.business;

import com.op.mall.MallRequestExecutor;
import com.op.mall.exception.MallException;
import com.op.mall.request.OrderCancelRequest;
import com.op.mall.request.OrderSubmitRequest;
import com.op.mall.response.MallResponse;
import com.op.mall.response.OrderSubmitResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
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
    private final MallOrderRequestBuilderProxy mallOrderRequestBuilderProxy;

    /**
     * 电商请求执行者
     */
    private final MallRequestExecutor mallRequestExecutor;

    public OrderServiceImpl(MallOrderRequestBuilderProxy mallOrderRequestBuilderProxy, MallRequestExecutor mallRequestExecutor) {
        this.mallOrderRequestBuilderProxy = mallOrderRequestBuilderProxy;
        this.mallRequestExecutor = mallRequestExecutor;
    }

    @Override
    public OrderSubmitVO submitOrder(OrderSubmitDTO submitDTO) {
        // 1. 执行业务校验

        // 2. 获取并校验其他相关信息

        // 3. 构造订单提交请求
        Map<String, List<OrderSubmitDTO.SkuInfo>> skuInfoMap = submitDTO.getSkuInfoList().stream().collect(Collectors.groupingBy(OrderSubmitDTO.SkuInfo::getMallType));
        List<OrderSubmitRequest> requests = skuInfoMap.keySet().stream().map(mallType -> {
            // TODO 深拷贝（克隆）
            OrderSubmitDTO curSubmitDTO = submitDTO;
            curSubmitDTO.setSkuInfoList(skuInfoMap.get(mallType));

            return mallOrderRequestBuilderProxy.buildOrderSubmitRequest(mallType, curSubmitDTO, null);
        }).collect(Collectors.toList());


        // 4. 发起订单提交请求
        List<OrderSubmitResponse> responses = mallRequestExecutor.executeConcurrent(requests);

        // 5. 如果存在任一电商提交订单失败，就回滚所有电商成功的提交订单
        List<OrderSubmitResponse> successResponses = responses.stream().filter(MallResponse::isSuccess).collect(Collectors.toList());
        if (successResponses.size() < skuInfoMap.keySet().size()) {
            if (successResponses.size() > 0) {
                List<OrderCancelRequest> cancelRequests = successResponses.stream()
                        .map(response -> mallOrderRequestBuilderProxy.buildOrderCancelRequest(response.getMallType().getValue(), null, response.getThirdOrderId(), null)).collect(Collectors.toList());
                mallRequestExecutor.executeConcurrent(cancelRequests);
            } else {
                String message = "提交订单失败";
                log.error(message);
                throw new MallException(message);
            }
        }

        // 6. 转换请求响应（List<OrderSubmitResponse> -> OrderSubmitVO）
        return null;
    }
}

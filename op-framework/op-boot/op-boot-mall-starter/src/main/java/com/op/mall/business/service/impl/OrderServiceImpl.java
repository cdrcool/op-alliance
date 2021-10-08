package com.op.mall.business.service.impl;

import com.op.mall.MallRequestExecutor;
import com.op.mall.business.dto.OrderSubmitDTO;
import com.op.mall.business.mall.requestbuilder.MallOrderRequestBuilderProxy;
import com.op.mall.business.service.OrderService;
import com.op.mall.business.vo.OrderSubmitVO;
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
     * 电商订单请求构造者
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
        Map<String, List<OrderSubmitDTO.SkuInfo>> skuInfoMap = submitDTO.getSkuInfoList().stream()
                .collect(Collectors.groupingBy(OrderSubmitDTO.SkuInfo::getMallType));
        List<OrderSubmitRequest> requests = skuInfoMap.keySet().stream()
                .map(mallType -> {
                    OrderSubmitDTO curSubmitDTO;
                    try {
                        // 深克隆（拷贝 skuInfoList 之外的其他对象）
                        curSubmitDTO = submitDTO.clone();
                    } catch (CloneNotSupportedException e) {
                        String message = "提交订单失败，构建订单提交请求异常";
                        log.error(message, e);
                        throw new MallException(message);
                    }
                    curSubmitDTO.setSkuInfoList(skuInfoMap.get(mallType));

                    // 订单提交请求构造上下文定义为 curSubmitDTO
                    // 对于有后续接入的电商，如果需要补充额外的信息，且不好划入到 OrderSubmitDTO 中，那么可以放到 supplierMap 中
                    return mallOrderRequestBuilderProxy.buildOrderSubmitRequest(mallType, curSubmitDTO, null);
                }).collect(Collectors.toList());


        // 4. 发起订单提交请求（可能同时下单多个电商的商品，这里先构建多个请求，然后调用 executeConcurrent 并发执行）
        List<OrderSubmitResponse> responses = mallRequestExecutor.executeConcurrent(requests);

        // 5. 如果存在任一电商提交订单失败，就回滚所有电商成功的提交订单
        List<OrderSubmitResponse> successResponses = responses.stream()
                .filter(MallResponse::isSuccess).collect(Collectors.toList());
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

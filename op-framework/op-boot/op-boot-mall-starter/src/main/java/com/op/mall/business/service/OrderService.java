package com.op.mall.business.service;

import com.op.mall.business.dto.OrderSubmitDTO;
import com.op.mall.business.vo.OrderSubmitVO;

/**
 * 订单 Service
 *
 * @author cdrcool
 */
public interface OrderService {

    /**
     * 提交发票申请
     *
     * @param submitDTO 订单提交 DTO
     * @return 订单提交响应
     */
    OrderSubmitVO submitOrder(OrderSubmitDTO submitDTO);
}

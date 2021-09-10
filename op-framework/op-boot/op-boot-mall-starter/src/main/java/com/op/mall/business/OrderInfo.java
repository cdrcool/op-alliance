package com.op.mall.business;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单信息
 *
 * @author cdrcool
 */
@Data
public class OrderInfo {
    /**
     * 电商类型
     */
    private String mallType;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 供货单信息列表
     */
    private List<SupplyOrderInfo> supplyOrders;
}

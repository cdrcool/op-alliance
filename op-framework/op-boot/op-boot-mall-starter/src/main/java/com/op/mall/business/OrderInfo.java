package com.op.mall.business;

import lombok.Data;

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
     * 第三方订单号
     */
    private Long thirdOrderId;

    /**
     * 供货单信息列表
     */
    private List<SupplyOrderInfo> supplyOrders;
}

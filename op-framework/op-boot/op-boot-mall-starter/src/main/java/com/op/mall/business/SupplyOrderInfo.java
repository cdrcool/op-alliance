package com.op.mall.business;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 供货单信息
 *
 * @author cdrcool
 */
@Data
public class SupplyOrderInfo {
    /**
     * 第三方子单号
     */
    private Long thirdSubOrderId;

    /**
     * 供货单总金额
     */
    private BigDecimal totalPrice;
}

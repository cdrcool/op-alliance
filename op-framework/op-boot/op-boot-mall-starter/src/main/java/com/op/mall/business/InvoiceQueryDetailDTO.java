package com.op.mall.business;

import lombok.Data;

/**
 * 发票查询发票详情 DTO
 *
 * @author cdrcool
 */
@Data
public class InvoiceQueryDetailDTO {
    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 供货单ID
     */
    private Long supplyId;
}

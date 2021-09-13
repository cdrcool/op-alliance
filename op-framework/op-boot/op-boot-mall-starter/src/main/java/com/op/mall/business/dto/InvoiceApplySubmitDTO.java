package com.op.mall.business.dto;

import lombok.Data;

import java.util.List;

/**
 * 发票提交申请 DTO
 *
 * @author cdrcool
 */
@Data
public class InvoiceApplySubmitDTO {
    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 供货单IDS
     */
    private List<Long> supplyIds;
}

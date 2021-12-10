package com.op.boot.mall.request.invoice.custom;

import lombok.Data;

import java.util.List;

/**
 * 京东发票详情查询请求
 *
 * @author cdrcool
 */
@Data
public class JdInvoiceDetailQueryRequest {
    /**
     * 发票类型
     */
    private Integer invoiceType;

    /**
     * 申请单号
     */
    private String markId;

    /**
     * 订单号列表
     */
    private List<Long> orderIds;
}

package com.op.mall.request.jingdong;

import lombok.Data;

/**
 * 京东发票查询物流信息请求
 *
 * @author cdrcool
 */
@Data
public class JdInvoiceQueryDetailRequest {
    /**
     * 发票类型
     */
    private Integer invoiceType;

    /**
     * 申请单号
     */
    private String markId;

    /**
     * 子订单号
     */
    private Long subOrderId;
}

package com.op.boot.mall.request.invoice.jingdong;

import lombok.Data;

import java.util.List;

/**
 * 发票物流请求对象
 *
 * @author cdrcool
 */
@Data
public class JdInvoiceQueryDeliveryRequest {
    /**
     * 申请单号
     */
    private String markId;

    /**
     * 订单号列表
     */
    private List<Long> orderIds;
}

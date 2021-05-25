package com.op.framework.boot.sdk.client.request;

import lombok.Data;

import java.util.List;

/**
 * 电子发票明细请求对象
 *
 * @author cdrcool
 */
@Data
public class InvoiceElectronicDetailRequest {
    /**
     * 订单号
     */
    private String orderId;

    /**
     * 子订单号列表
     */
    private List<String> subOrderIds;
}

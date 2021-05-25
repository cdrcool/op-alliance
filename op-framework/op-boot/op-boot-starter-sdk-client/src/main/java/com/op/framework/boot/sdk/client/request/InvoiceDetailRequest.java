package com.op.framework.boot.sdk.client.request;

import lombok.Data;

/**
 * 发票详情请求对象
 *
 * @author cdrcool
 */
@Data
public class InvoiceDetailRequest {
    /**
     * 发票号码
     */
    private String invoiceId;

    /**
     * 发票代码
     */
    private String invoiceCode;
}

package com.op.boot.mall.request.invoice.custom;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 震坤行发票详情查询请求
 *
 * @author chengdr01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ZkhMallInvoiceDetailQueryRequest extends ZkhMallBaseRequest {
    /**
     * 申请单号
     */
    private String markId;
}

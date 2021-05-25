package com.op.framework.boot.sdk.client.request;

import lombok.Data;

/**
 * 发票概要请求对象
 *
 * @author cdrcool
 */
@Data
public class InvoiceOutlineRequest {
    /**
     * 申请单号（不允许重复，根据该字段查询消息及撤回开票申请等）
     */
    private String markId;
}

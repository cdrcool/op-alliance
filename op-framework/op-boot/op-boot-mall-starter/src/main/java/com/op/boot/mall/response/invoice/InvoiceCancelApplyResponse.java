package com.op.boot.mall.response.invoice;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 发票取消申请响应
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InvoiceCancelApplyResponse extends MallResponse {
    /**
     * 是否成功
     */
    private Boolean success;
}

package com.op.framework.boot.sdk.client.api.pro.sn;

import com.suning.api.entity.govbus.InvoicebatchDeleteRequest;
import com.suning.api.entity.govbus.InvoicesupplementConfirmRequest;

/**
 * 苏宁发票 API 接口
 *
 * @author cdrcool
 */
public interface SnInvoiceApi {

    /**
     * 提交发票申请
     *
     * @param token   访问令牌
     * @param request 发票申请提交对象
     * @return 是否成功
     */
    boolean submitInvoiceApply(String token, InvoicesupplementConfirmRequest request);

    /**
     * 取消发票申请
     *
     * @param token                     访问令牌
     * @param invoicebatchDeleteRequest 发票申请取消对象
     * @return 是否成功
     */
    Boolean cancelInvoiceApply(String token, InvoicebatchDeleteRequest invoicebatchDeleteRequest);
}

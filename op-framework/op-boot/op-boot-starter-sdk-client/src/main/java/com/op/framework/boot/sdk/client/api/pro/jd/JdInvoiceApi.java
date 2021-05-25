package com.op.framework.boot.sdk.client.api.pro.jd;

import com.jd.open.api.sdk.request.vopfp.VopInvoiceCancelInvoiceApplyRequest;
import com.jd.open.api.sdk.request.vopfp.VopInvoiceSubmitInvoiceApplyRequest;

/**
 * 京东发票 API 接口
 *
 * @author cdrcool
 */
public interface JdInvoiceApi {

    /**
     * 提交发票申请
     *
     * @param token   访问令牌
     * @param request 发票申请提交对象
     * @return 是否成功
     */
    boolean submitInvoiceApply(String token, VopInvoiceSubmitInvoiceApplyRequest request);

    /**
     * 取消发票申请
     *
     * @param token                     访问令牌
     * @param invoiceApplyCancelRequest 发票申请取消对象
     * @return 是否成功
     */
    Boolean cancelInvoiceApply(String token, VopInvoiceCancelInvoiceApplyRequest invoiceApplyCancelRequest);

}

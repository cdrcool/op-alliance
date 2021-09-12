package com.op.mall.business;

import com.op.mall.response.InvoiceQueryDetailResponse;

/**
 * 发票 Service
 *
 * @author cdrcool
 */
public interface InvoiceService {

    /**
     * 提交发票申请
     *
     * @param submitDTO 发票提交申请 DTO
     */
    void submitInvoiceApply(InvoiceApplySubmitDTO submitDTO);

    /**
     * 查看发票详情
     *
     * @param queryDetailDTO 查询发票详情 DTO
     * @return 发票详情
     */
    InvoiceQueryDetailResponse viewInvoiceDetail(InvoiceQueryDetailDTO queryDetailDTO);
}

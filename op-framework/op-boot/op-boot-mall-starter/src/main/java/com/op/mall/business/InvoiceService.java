package com.op.mall.business;

/**
 * 发票 Service
 *
 * @author cdrcool
 */
public interface InvoiceService {

    /**
     * 提交发票申请
     *
     * @param submitDTO 发票申请提交 DTO
     */
    void submitInvoiceApply(InvoiceApplySubmitDTO submitDTO);
}

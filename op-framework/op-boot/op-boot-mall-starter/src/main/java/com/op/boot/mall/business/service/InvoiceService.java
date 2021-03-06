package com.op.boot.mall.business.service;

import com.op.boot.mall.business.dto.InvoiceApplySubmitDto;
import com.op.boot.mall.business.requestbuilder.invoice.InvoiceApplySubmitRequestBuilder;
import com.op.boot.mall.invoker.MallCommandInvoker;
import com.op.boot.mall.response.invoice.InvoiceApplySubmitResponse;
import org.springframework.stereotype.Service;

/**
 * 发票 Service
 *
 * @author chengdr01
 */
@Service
public class InvoiceService {
    private final MallCommandInvoker mallCommandInvoker;

    public InvoiceService(MallCommandInvoker mallCommandInvoker) {
        this.mallCommandInvoker = mallCommandInvoker;
    }

    public void submitInvoiceApply() {
        InvoiceApplySubmitDto invoiceApplySubmitDto = new InvoiceApplySubmitDto();
        InvoiceApplySubmitRequestBuilder invoiceApplySubmitRequestBuilder = new InvoiceApplySubmitRequestBuilder();
        invoiceApplySubmitRequestBuilder.setInvoiceApplySubmitDto(invoiceApplySubmitDto);
        InvoiceApplySubmitResponse response = mallCommandInvoker.invoke(invoiceApplySubmitRequestBuilder.buildRequest());
    }
}

package com.op.boot.mall.business.requestbuilder.invoice;

import com.jd.open.api.sdk.request.vopfp.VopInvoiceSubmitInvoiceApplyRequest;
import com.op.boot.mall.business.dto.InvoiceApplySubmitDto;
import com.op.boot.mall.constants.MallType;
import com.op.boot.mall.exception.MallException;
import com.op.boot.mall.request.invoice.InvoiceApplySubmitRequest;
import com.suning.api.entity.govbus.InvoicesupplementConfirmRequest;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * 发票申请提交请求构造类
 *
 * @author chengdr01
 */
@Data
@Component
public class InvoiceApplySubmitRequestBuilder {
    private final Map<String, Supplier<InvoiceApplySubmitRequest<?>>> strategyMap = new ConcurrentHashMap<>();

    private InvoiceApplySubmitDto invoiceApplySubmitDto;

    public InvoiceApplySubmitRequestBuilder() {
        this.strategyMap.put(MallType.JD.getValue(), this::buildJdRequest);
        this.strategyMap.put(MallType.SN.getValue(), this::buildSnRequest);
    }

    public InvoiceApplySubmitRequest<?> buildRequest() {
        String mallType = invoiceApplySubmitDto.getMallType();
        Supplier<InvoiceApplySubmitRequest<?>> supplier = strategyMap.get(mallType);

        if (supplier == null) {
            throw new MallException("未定义【" + mallType + "】的发票申请提交请求对象构造策略");
        }

        return supplier.get();
    }

    /**
     * 构造京东请求
     *
     * @return 京东请求
     */
    private InvoiceApplySubmitRequest<VopInvoiceSubmitInvoiceApplyRequest> buildJdRequest() {
        return null;
    }

    /**
     * 构造苏宁请求
     *
     * @return 苏宁请求
     */
    private InvoiceApplySubmitRequest<InvoicesupplementConfirmRequest> buildSnRequest() {
        return null;
    }

    /**
     * 构造震坤行请求
     *
     * @return 震坤行请求
     */
    private InvoiceApplySubmitRequest<Object> buildZkhRequest() {
        return null;
    }
}

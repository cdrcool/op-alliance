package com.op.boot.mall.business.requestsupplier.invoice;

import com.jd.open.api.sdk.request.vopfp.VopInvoiceCancelInvoiceApplyRequest;
import com.op.boot.mall.authentication.MallAuthentication;
import com.op.boot.mall.business.dto.InvoiceApplySubmitDto;
import com.op.boot.mall.constants.MallType;
import com.op.boot.mall.exception.MallException;
import com.op.boot.mall.request.invoice.InvoiceDetailQueryRequest;
import com.suning.api.entity.govbus.InvoicebatchDeleteRequest;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * 发票详情查询请求构造类
 *
 * @author chengdr01
 */
@Data
@Component
public class InvoiceDetailQueryRequestBuilder {
    private final Map<String, Supplier<InvoiceDetailQueryRequest<?>>> strategyMap = new ConcurrentHashMap<>();

    private MallAuthentication authentication;
    private InvoiceApplySubmitDto invoiceApplySubmitDto;

    public InvoiceDetailQueryRequestBuilder() {
        this.strategyMap.put(MallType.JD.getValue(), this::buildJdRequest);
        this.strategyMap.put(MallType.SN.getValue(), this::buildSnRequest);
    }

    public InvoiceDetailQueryRequest<?> buildRequest() {
        String mallType = invoiceApplySubmitDto.getMallType();
        Supplier<InvoiceDetailQueryRequest<?>> supplier = strategyMap.get(mallType);

        if (supplier == null) {
            throw new MallException("未定义【" + mallType + "】的发票详情查询请求对象构造策略");
        }

        return supplier.get();
    }

    /**
     * 构造京东请求
     *
     * @return 京东请求
     */
    private InvoiceDetailQueryRequest<VopInvoiceCancelInvoiceApplyRequest> buildJdRequest() {
        return null;
    }

    /**
     * 构造苏宁请求
     *
     * @return 苏宁请求
     */
    private InvoiceDetailQueryRequest<InvoicebatchDeleteRequest> buildSnRequest() {
        return null;
    }

    /**
     * 构造震坤行请求
     *
     * @return 震坤行请求
     */
    private InvoiceDetailQueryRequest<Object> buildZkhRequest() {
        return null;
    }
}

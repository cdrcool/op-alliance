package com.op.mall.business.mall.requestbuilder;

import com.op.mall.business.dto.OrderInfo;
import com.op.mall.business.dto.SupplyOrderInvoiceInfo;
import com.op.mall.constans.MallType;
import com.op.mall.request.InvoiceQueryDetailRequest;
import com.op.mall.request.InvoiceSubmitApplyRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 电商发票请求构造者 proxy
 *
 * @author chengdr01
 */
public class MallInvoiceRequestBuilderProxy {
    private final List<MallInvoiceRequestBuilder> invoiceRequestBuilders;

    public MallInvoiceRequestBuilderProxy(List<MallInvoiceRequestBuilder> invoiceRequestBuilders) {
        this.invoiceRequestBuilders = invoiceRequestBuilders;
    }

    public InvoiceSubmitApplyRequest buildInvoiceSubmitApplyRequest(String mallType, OrderInfo orderInfo, SupplyOrderInvoiceInfo invoiceInfo, Map<String, Supplier<Object>> supplierMap) {
        return getMallInvoiceRequestBuilder(mallType).map(builder -> builder.buildInvoiceSubmitApplyRequest(orderInfo, invoiceInfo, supplierMap)).orElse(null);
    }

    public InvoiceQueryDetailRequest buildInvoiceQueryDetailRequest(String mallType, SupplyOrderInvoiceInfo invoiceInfo, Map<String, Supplier<Object>> supplierMap) {
        return getMallInvoiceRequestBuilder(mallType).map(builder -> builder.buildInvoiceQueryDetailRequest(invoiceInfo, supplierMap)).orElse(null);
    }

    private Optional<MallInvoiceRequestBuilder> getMallInvoiceRequestBuilder(String mallType) {
        return invoiceRequestBuilders.stream().filter(builder -> builder.supports(MallType.get(mallType))).findAny();
    }
}

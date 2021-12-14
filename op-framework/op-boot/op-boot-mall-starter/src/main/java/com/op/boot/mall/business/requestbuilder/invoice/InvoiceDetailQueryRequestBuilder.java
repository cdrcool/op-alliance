package com.op.boot.mall.business.requestbuilder.invoice;

import com.op.boot.mall.constants.InvoiceType;
import com.op.boot.mall.constants.MallType;
import com.op.boot.mall.exception.MallException;
import com.op.boot.mall.request.invoice.InvoiceDetailQueryRequest;
import com.op.boot.mall.request.invoice.custom.JdInvoiceDetailQueryRequest;
import com.suning.api.entity.govbus.InvoicebatchDeleteRequest;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
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

    /**
     * 电商类型
     */
    private String mallType;

    /**
     * 电商账号名
     */
    private String accountName;

    /**
     * 发票类型
     */
    private Integer invoiceType;

    /**
     * 申请单号
     */
    private String markId;

    /**
     * 订单号列表
     */
    private List<Long> orderIds;

    public InvoiceDetailQueryRequestBuilder() {
        this.strategyMap.put(MallType.JD.getValue(), this::buildJdRequest);
        this.strategyMap.put(MallType.SN.getValue(), this::buildSnRequest);
    }

    public InvoiceDetailQueryRequest<?> buildRequest() {
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
    private InvoiceDetailQueryRequest<JdInvoiceDetailQueryRequest> buildJdRequest() {
        JdInvoiceDetailQueryRequest jdRequest = new JdInvoiceDetailQueryRequest();
        jdRequest.setInvoiceType(InvoiceType.ELECTRONIC_INVOICE.getValue());
        jdRequest.setOrderIds(Collections.singletonList(149561215491L));
        return new InvoiceDetailQueryRequest<>(MallType.JD, accountName, jdRequest);
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
    private InvoiceDetailQueryRequest<JdInvoiceDetailQueryRequest> buildZkhRequest() {
        return null;
    }
}

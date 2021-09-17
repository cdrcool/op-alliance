package com.op.mall.business.mall.requestbuilder;

import com.op.mall.business.dto.OrderInfo;
import com.op.mall.business.dto.SupplyOrderInvoiceInfo;
import com.op.mall.client.MallAuthenticationManager;
import com.op.mall.request.InvoiceQueryDetailRequest;
import com.op.mall.request.InvoiceSubmitApplyRequest;

import java.util.Map;
import java.util.function.Supplier;

/**
 * 电商发票请求提供者
 *
 * @author chengdr01
 */
public abstract class MallInvoiceRequestBuilder extends MallRequestBuilder {

    public MallInvoiceRequestBuilder(MallAuthenticationManager mallAuthenticationManager) {
        super(mallAuthenticationManager);
    }

    /**
     * 构建发票申请提交请求
     *
     * @param orderInfo   订单/供货单信息
     * @param invoiceInfo 供货单发票信息
     * @param supplierMap 扩展信息 supplier
     * @return 发票申请提交请求
     */
    public abstract InvoiceSubmitApplyRequest buildInvoiceSubmitApplyRequest(OrderInfo orderInfo, SupplyOrderInvoiceInfo invoiceInfo, Map<String, Supplier<Object>> supplierMap);

    /**
     * 构建发票查询详情请求
     *
     * @param invoiceInfo 供货单发票信息
     * @param supplierMap 扩展信息 supplier
     * @return 发票申请提交请求
     */
    public abstract InvoiceQueryDetailRequest buildInvoiceQueryDetailRequest(SupplyOrderInvoiceInfo invoiceInfo, Map<String, Supplier<Object>> supplierMap);
}

package com.op.mall.business.mall.requestbuilder;

import com.jd.open.api.sdk.request.vopfp.VopInvoiceSubmitInvoiceApplyRequest;
import com.op.mall.business.dto.OrderInfo;
import com.op.mall.business.dto.SupplyOrderInfo;
import com.op.mall.business.dto.SupplyOrderInvoiceInfo;
import com.op.mall.client.MallAuthentication;
import com.op.mall.client.MallAuthenticationManager;
import com.op.mall.constans.InvoiceType;
import com.op.mall.constans.MallType;
import com.op.mall.request.InvoiceQueryDetailRequest;
import com.op.mall.request.InvoiceSubmitApplyRequest;
import com.op.mall.request.jingdong.JdInvoiceQueryDetailRequest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 京东电商发票请求构造者
 *
 * @author chengdr01
 */
public class JdMallInvoiceRequestBuilder extends MallInvoiceRequestBuilder {

    public JdMallInvoiceRequestBuilder(MallAuthenticationManager mallAuthenticationManager) {
        super(mallAuthenticationManager);
    }

    @Override
    public InvoiceSubmitApplyRequest buildInvoiceSubmitApplyRequest(OrderInfo orderInfo, SupplyOrderInvoiceInfo invoiceInfo, Map<String, Supplier<Object>> supplierMap) {
        // 1. 获取京东电商身份认证凭据
        MallAuthentication mallAuthentication = super.loadMallAuthentication(MallType.JINGDONG, invoiceInfo.getEnterpriseTaxpayer());

        // 2. 构建京东电商发票提交申请请求
        List<SupplyOrderInfo> supplyOrders = orderInfo.getSupplyOrders();
        VopInvoiceSubmitInvoiceApplyRequest jdRequest = new VopInvoiceSubmitInvoiceApplyRequest();
        jdRequest.setInvoiceOrg(10);
        jdRequest.setBizInvoiceContent(1);
        jdRequest.setMarkId("MingYuan" + UUID.randomUUID().toString().replace("-", ""));
        jdRequest.setSettlementId(String.valueOf(orderInfo.getOrderId()));
        jdRequest.setSupplierOrder(supplyOrders.stream().map(SupplyOrderInfo::getThirdSubOrderId).map(String::valueOf).collect(Collectors.joining()));
        jdRequest.setInvoiceType(invoiceInfo.getInvoiceType());
        jdRequest.setInvoiceDate(new SimpleDateFormat("yyyy-MM-dd").format(invoiceInfo.getInvoiceDate()));
        jdRequest.setTitle(invoiceInfo.getInvoiceTitle());
        jdRequest.setEnterpriseTaxpayer(invoiceInfo.getEnterpriseTaxpayer());
        jdRequest.setEnterpriseRegPhone(invoiceInfo.getEnterpriseRegTel());
        jdRequest.setEnterpriseRegAddress(invoiceInfo.getEnterpriseRegAddress());
        jdRequest.setEnterpriseBankName(invoiceInfo.getEnterpriseBankName());
        jdRequest.setEnterpriseBankAccount(invoiceInfo.getEnterpriseBankAccount());
        jdRequest.setBillToer(invoiceInfo.getBillReceiver());
        jdRequest.setBillToContact(invoiceInfo.getBillToContact());
        jdRequest.setBillToAddress(invoiceInfo.getAddress());
        jdRequest.setTotalBatchInvoiceAmount(supplyOrders.stream().map(SupplyOrderInfo::getTotalPrice).reduce(new BigDecimal("0"), BigDecimal::add));
        jdRequest.setTotalBatch(1);
        jdRequest.setCurrentBatch(1);
        jdRequest.setInvoiceNum(orderInfo.getSupplyOrders().size());
        jdRequest.setInvoicePrice(supplyOrders.stream().map(SupplyOrderInfo::getTotalPrice).reduce(new BigDecimal("0"), BigDecimal::add));
        jdRequest.setInvoiceRemark("");

        // 如果是专用发票，还需传递四级地址信息
        if (InvoiceType.TAX_INVOICE.getValue().equals(invoiceInfo.getInvoiceType())) {
            // 省去了地址编码转换
            jdRequest.setBillToProvince(invoiceInfo.getProvinceCode());
            jdRequest.setBillToCity(invoiceInfo.getCityCode());
            jdRequest.setBillToCounty(invoiceInfo.getCountyCode());
            jdRequest.setBillToTown(invoiceInfo.getTownCode());
        }

        return new InvoiceSubmitApplyRequest(MallType.JINGDONG, mallAuthentication, jdRequest);
    }

    @Override
    public InvoiceQueryDetailRequest buildInvoiceQueryDetailRequest(SupplyOrderInvoiceInfo invoiceInfo, Map<String, Supplier<Object>> supplierMap) {
        // 1. 获取京东电商身份认证凭据
        MallAuthentication mallAuthentication = super.loadMallAuthentication(MallType.JINGDONG, invoiceInfo.getEnterpriseTaxpayer());

        // 2. 构建京东电商发票发票查询详情请求
        JdInvoiceQueryDetailRequest jdRequest = new JdInvoiceQueryDetailRequest();
        jdRequest.setInvoiceType(invoiceInfo.getInvoiceType());
        jdRequest.setMarkId(invoiceInfo.getMarkId());
        jdRequest.setSubOrderId(invoiceInfo.getThirdSubOrderId());

        return new InvoiceQueryDetailRequest(MallType.JINGDONG, mallAuthentication, jdRequest);
    }

    @Override
    public boolean supports(MallType mallType) {
        return MallType.JINGDONG.equals(mallType);
    }
}

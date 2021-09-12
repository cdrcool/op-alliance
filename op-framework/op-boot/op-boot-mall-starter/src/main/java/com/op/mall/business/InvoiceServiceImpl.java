package com.op.mall.business;

import com.jd.open.api.sdk.request.vopfp.VopInvoiceSubmitInvoiceApplyRequest;
import com.op.mall.MallRequestExecutor;
import com.op.mall.client.MallAuthentication;
import com.op.mall.client.MallAuthenticationProvider;
import com.op.mall.constans.MallMethodConstants;
import com.op.mall.constans.MallType;
import com.op.mall.request.InvoiceApplySubmitRequest;
import com.op.mall.request.MallRequestAction;
import com.op.mall.response.InvoiceApplySubmitResponse;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 发票 Service Impl
 *
 * @author cdrcool
 */
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {
    /**
     * 电商身份认证凭据管理者
     */
    private final MallAuthenticationProvider mallAuthenticationProvider;

    /**
     * 电商请求执行者
     */
    private final MallRequestExecutor mallRequestExecutor;

    public InvoiceServiceImpl(MallAuthenticationProvider mallAuthenticationProvider, MallRequestExecutor mallRequestExecutor) {
        this.mallAuthenticationProvider = mallAuthenticationProvider;
        this.mallRequestExecutor = mallRequestExecutor;
    }

    @Override
    public void submitInvoiceApply(InvoiceApplySubmitDTO submitDTO) {
        // 1. 执行业务校验，如：订单/供货单是否存在、供货单状态是否允许开票、第三方父/子订单是否存在
        // 模拟获取订单及订单下的供货单信息（订单中包含电商类型）
        OrderInfo order = new OrderInfo();

        // 2. 获取并校验其他信息，如：电商类型、开票金额、开票信息、收票信息等
        // 模拟获取订单的开票信息
        OrderInvoiceInfo invoiceInfo = new OrderInvoiceInfo();

        // 3. 构造发票申请提交的请求对象提供者 map
        Map<String, Supplier<InvoiceApplySubmitRequest>> requestSupplierMap = new HashMap<>(8);
        requestSupplierMap.put(MallType.JINGDONG.getValue(), () -> buildJdInvoiceApplySubmitParams(order, invoiceInfo));
        requestSupplierMap.put(MallType.SUNING.getValue(), () -> buildSnInvoiceApplySubmitParams(order, invoiceInfo));

        // 4. 设置电商请求动作 -> 发起电商请求
        MallRequestAction action = new MallRequestAction(order.getMallType(), MallMethodConstants.INVOICE_APPLY_SUBMIT);
        InvoiceApplySubmitResponse response = mallRequestExecutor.execute(action, requestSupplierMap);
        if (!response.isSuccess()) {
            // 请求失败 -> 输出异常日志 + 抛出异常
            String message = MessageFormat.format("提交发票申请异常，错误码：{0}，错误描述{1}",
                    response.getErrorCode(), response.getErrorMsg());
            log.error(message);
            throw new BusinessException(message);
        }

        // 5. 请求成功 -> 保存发票申请提交请求 + 更新供货单状态为开票中
    }

    private InvoiceApplySubmitRequest buildJdInvoiceApplySubmitParams(OrderInfo order, OrderInvoiceInfo invoiceInfo) {
        // 1. 获取京东电商身份认证凭据
        MallAuthentication mallAuthentication = mallAuthenticationProvider.getAuthentication(invoiceInfo.getEnterpriseTaxpayer());

        // 2. 构建京东电商发票申请提交的请求对象
        List<SupplyOrderInfo> supplyOrders = order.getSupplyOrders();
        VopInvoiceSubmitInvoiceApplyRequest jdRequest = new VopInvoiceSubmitInvoiceApplyRequest();
        jdRequest.setInvoiceOrg(10);
        jdRequest.setBizInvoiceContent(1);
        jdRequest.setMarkId("MingYuan" + UUID.randomUUID().toString().replace("-", ""));
        jdRequest.setSettlementId(String.valueOf(order.getOrderId()));
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
        jdRequest.setInvoiceNum(order.getSupplyOrders().size());
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

        return new InvoiceApplySubmitRequest(mallAuthentication, jdRequest);
    }

    private InvoiceApplySubmitRequest buildSnInvoiceApplySubmitParams(OrderInfo order, OrderInvoiceInfo invoiceInfo) {
        // 待补充
        return new InvoiceApplySubmitRequest(null, null);
    }
}

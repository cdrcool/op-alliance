package com.op.mall.business;

import com.op.mall.MallRequestExecutor;
import com.op.mall.constans.MallType;
import com.op.mall.request.InvoiceApplySubmitRequest;
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
     * 电商请求执行类
     */
    private final MallRequestExecutor mallRequestExecutor;

    public InvoiceServiceImpl(MallRequestExecutor mallRequestExecutor) {
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

        // 3. 构造电商的发票申请提交参数
        Map<String, Supplier<Map<String, Object>>> requestParamsMap = buildMallInvoiceApplySubmitParams(order, invoiceInfo);

        // 4. 创建电商发票申请提交对象 -> 发起请求
        InvoiceApplySubmitRequest request = new InvoiceApplySubmitRequest(order.getMallType());
        request.setRequestParams(requestParamsMap.getOrDefault(order.getMallType(), () -> new HashMap<>(0)).get());
        InvoiceApplySubmitResponse response = mallRequestExecutor.execute(request);
        if (!response.isSuccess()) {
            // 请求失败 -> 输出异常日志 + 抛出异常
            String message = MessageFormat.format("提交发票申请异常，错误码：{0}，错误描述{1}",
                    response.getErrorCode(), response.getErrorMsg());
            log.error(message);
            throw new BusinessException(message);
        }

        // 4. 请求成功 -> 保存发票申请提交请求 + 更新供货单状态为开票中
    }

    /**
     * 构建电商的发票申请提交参数
     *
     * @param order       订单及订单下的供货单信息
     * @param invoiceInfo 订单的开票信息
     * @return 电商的发票申请提交参数（key：电商类型；value：发票申请提交参数）
     */
    private Map<String, Supplier<Map<String, Object>>> buildMallInvoiceApplySubmitParams(OrderInfo order, OrderInvoiceInfo invoiceInfo) {
        Map<String, Supplier<Map<String, Object>>> map = new HashMap<>(8);
        map.put(MallType.JINGDONG.getValue(), () -> buildJdInvoiceApplySubmitParams(order, invoiceInfo));
        map.put(MallType.SUNING.getValue(), () -> buildSnInvoiceApplySubmitParams(order, invoiceInfo));
        return map;
    }

    private Map<String, Object> buildJdInvoiceApplySubmitParams(OrderInfo order, OrderInvoiceInfo invoiceInfo) {
        List<SupplyOrderInfo> supplyOrders = order.getSupplyOrders();

        Map<String, Object> params = new HashMap<>(32);
        params.put("invoiceOrg", 10);
        params.put("bizInvoiceContent", 1);
        params.put("markId", "MingYuan" + UUID.randomUUID().toString().replace("-", ""));
        params.put("settlementId", String.valueOf(order.getOrderId()));
        params.put("subOrderIds", supplyOrders.stream().map(SupplyOrderInfo::getThirdSubOrderId).collect(Collectors.toList()));
        params.put("invoiceType", invoiceInfo.getInvoiceType());
        params.put("invoiceDate", new SimpleDateFormat("yyyy-MM-dd").format(invoiceInfo.getInvoiceDate()));
        params.put("title", invoiceInfo.getInvoiceTitle());
        params.put("enterpriseTaxpayer", invoiceInfo.getEnterpriseTaxpayer());
        params.put("enterpriseRegPhone", invoiceInfo.getEnterpriseRegTel());
        params.put("enterpriseRegAddress", invoiceInfo.getEnterpriseRegAddress());
        params.put("enterpriseBankName", invoiceInfo.getEnterpriseBankName());
        params.put("enterpriseBankAccount", invoiceInfo.getEnterpriseBankAccount());
        params.put("billToer", invoiceInfo.getBillReceiver());
        params.put("billToContact", invoiceInfo.getBillToContact());
        params.put("billToAddress", invoiceInfo.getAddress());
        params.put("totalBatchInvoiceAmount", supplyOrders.stream().map(SupplyOrderInfo::getTotalPrice).reduce(BigDecimal::add));
        params.put("totalBatch", 1);
        params.put("currentBatch", 1);
        params.put("invoiceNum", order.getSupplyOrders().size());
        params.put("invoicePrice", supplyOrders.stream().map(SupplyOrderInfo::getTotalPrice).reduce(BigDecimal::add));
        params.put("Remark", "");

        // 如果是专用发票，还需传递四级地址信息
        if (InvoiceType.TAX_INVOICE.getValue().equals(invoiceInfo.getInvoiceType())) {
            // 省去了地址编码转换
            params.put("billToProvince", invoiceInfo.getProvinceCode());
            params.put("billToCity", invoiceInfo.getCityCode());
            params.put("billToCounty", invoiceInfo.getCountyCode());
            params.put("billToTown", invoiceInfo.getTownCode());
        }

        return params;
    }

    private Map<String, Object> buildSnInvoiceApplySubmitParams(OrderInfo order, OrderInvoiceInfo invoiceInfo) {
        return new HashMap<>(32);
    }
}

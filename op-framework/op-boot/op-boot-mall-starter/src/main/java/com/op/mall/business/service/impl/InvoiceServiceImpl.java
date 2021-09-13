package com.op.mall.business.service.impl;

import com.op.mall.MallRequestExecutor;
import com.op.mall.business.exception.BusinessException;
import com.op.mall.business.dto.OrderInfo;
import com.op.mall.business.dto.SupplyOrderInvoiceInfo;
import com.op.mall.business.dto.InvoiceApplySubmitDTO;
import com.op.mall.business.dto.InvoiceQueryDetailDTO;
import com.op.mall.business.mall.requestbuilder.MallInvoiceRequestBuilderProxy;
import com.op.mall.business.service.InvoiceService;
import com.op.mall.request.InvoiceQueryDetailRequest;
import com.op.mall.request.InvoiceSubmitApplyRequest;
import com.op.mall.response.InvoiceQueryDetailResponse;
import com.op.mall.response.InvoiceSubmitApplyResponse;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

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
    private final MallInvoiceRequestBuilderProxy mallInvoiceRequestBuilderProxy;

    /**
     * 电商请求执行者
     */
    private final MallRequestExecutor mallRequestExecutor;

    public InvoiceServiceImpl(MallInvoiceRequestBuilderProxy mallInvoiceRequestBuilderProxy, MallRequestExecutor mallRequestExecutor) {
        this.mallInvoiceRequestBuilderProxy = mallInvoiceRequestBuilderProxy;
        this.mallRequestExecutor = mallRequestExecutor;
    }

    @Override
    public void submitInvoiceApply(InvoiceApplySubmitDTO submitDTO) {
        // 1. 执行业务校验，如：订单/供货单是否存在、供货单状态是否允许开票、第三方父/子订单是否存在
        // 模拟获取订单及订单下的供货单信息（包含电商类型、第三方父子订单号）
        OrderInfo orderInfo = new OrderInfo();

        // 2. 获取并校验订单/供货单的开票信息，如：电商类型、开票金额、开票信息、收票信息等
        // 模拟获取订单的开票信息
        SupplyOrderInvoiceInfo invoiceInfo = new SupplyOrderInvoiceInfo();

        // 3. 构造发票提交申请请求
        InvoiceSubmitApplyRequest request = mallInvoiceRequestBuilderProxy.buildInvoiceSubmitApplyRequest(orderInfo.getMallType(), orderInfo, invoiceInfo, null);

        // 4. 发起发票提交申请请求
        InvoiceSubmitApplyResponse response = mallRequestExecutor.execute(request);

        // 5. 请求失败 -> 输出异常日志 + 抛出异常
        if (!response.isSuccess()) {
            String message = MessageFormat.format("提交发票申请异常，错误码：{0}，错误描述{1}",
                    response.getErrorCode(), response.getErrorMsg());
            log.error(message);
            throw new BusinessException(message);
        }

        // 6. 请求成功 -> 保存发票提交申请请求 + 更新供货单状态为开票中
    }

    @Override
    public InvoiceQueryDetailResponse viewInvoiceDetail(InvoiceQueryDetailDTO queryDetailDTO) {
        // 1. 获取供货单的开票信息
        // 模拟获取订单的开票信息
        SupplyOrderInvoiceInfo invoiceInfo = new SupplyOrderInvoiceInfo();

        // 2. 构造发票查询详情请求对象的提供者 map
        InvoiceQueryDetailRequest request = mallInvoiceRequestBuilderProxy.buildInvoiceQueryDetailRequest(invoiceInfo.getMallType(), invoiceInfo, null);

        // 3. 发起发票查询详情请求
        InvoiceQueryDetailResponse response = mallRequestExecutor.execute(request);

        // 5. 请求失败 -> 输出异常日志 + 抛出异常
        if (!response.isSuccess()) {
            String message = MessageFormat.format("提交发票申请异常，错误码：{0}，错误描述{1}",
                    response.getErrorCode(), response.getErrorMsg());
            log.error(message);
            throw new BusinessException(message);
        }

        // 6. 请求成功 -> 保存发票提交申请请求 + 更新供货单状态为开票中

        return null;
    }
}

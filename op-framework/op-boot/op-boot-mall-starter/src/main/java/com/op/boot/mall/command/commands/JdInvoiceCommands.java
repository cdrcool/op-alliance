package com.op.boot.mall.command.commands;

import com.jd.open.api.sdk.domain.vopfp.OperaInvoiceOpenProvider.response.submitInvoiceApply.OpenRpcResult;
import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryElectronicInvoiceDetail.InvoiceActualBillICOpenResp;
import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryElectronicInvoiceDetail.QueryElectronicInvoiceDetailOpenResp;
import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceDetail.QueryInvoiceDetailOpenResp;
import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceOutline.InvoiceOutlineOpenResp;
import com.jd.open.api.sdk.request.vopfp.*;
import com.jd.open.api.sdk.response.vopfp.*;
import com.op.boot.mall.authentication.JdMallAuthentication;
import com.op.boot.mall.command.MallCommand;
import com.op.boot.mall.constants.InvoiceType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.receiver.JdMallClient;
import com.op.boot.mall.receiver.JdMallRequest;
import com.op.boot.mall.request.invoice.InvoiceApplyCancelRequest;
import com.op.boot.mall.request.invoice.InvoiceApplySubmitRequest;
import com.op.boot.mall.request.invoice.InvoiceDetailQueryRequest;
import com.op.boot.mall.request.invoice.custom.JdInvoiceDetailQueryRequest;
import com.op.boot.mall.response.invoice.InvoiceApplyCancelResponse;
import com.op.boot.mall.response.invoice.InvoiceApplySubmitResponse;
import com.op.boot.mall.response.invoice.InvoiceDetailQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.op.boot.mall.constants.MallMethod.*;

/**
 * 京东发票类命令配置类
 *
 * @author chengdr01
 */
@Slf4j
@Configuration
public class JdInvoiceCommands {
    private final JdMallClient jdMallClient;

    public JdInvoiceCommands(JdMallClient jdMallClient) {
        this.jdMallClient = jdMallClient;
    }

    @Bean(MALL_JD + SEPARATOR + INVOICE_APPLY_SUBMIT)
    public MallCommand<InvoiceApplySubmitRequest<VopInvoiceSubmitInvoiceApplyRequest>,
            VopInvoiceSubmitInvoiceApplyRequest, InvoiceApplySubmitResponse> jdInvoiceApplySubmitCommand() {
        return (mallRequest -> {
            // 1. 获取京东身份认证凭据
            JdMallAuthentication authentication = (JdMallAuthentication) mallRequest.getAuthentication();

            // 2. 获取京东请求对象
            VopInvoiceSubmitInvoiceApplyRequest jdRequest = mallRequest.getRequestObj();

            // 3. 执行京东请求
            JdMallRequest<VopInvoiceSubmitInvoiceApplyResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
            VopInvoiceSubmitInvoiceApplyResponse jdResponse = jdMallClient.execute(jdMallRequest);
            OpenRpcResult result = jdResponse.getOpenRpcResult();
            if (!result.getSuccess()) {
                log.error("提交京东发票申请失败，错误码【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
                throw new JdMallException(result.getResultCode(), "提交京东发票申请失败：" + result.getResultMessage());
            }

            // 4. 转换为标准请求响应
            InvoiceApplySubmitResponse response = new InvoiceApplySubmitResponse();
            response.setSuccess(result.getSuccess());

            return new InvoiceApplySubmitResponse();
        });
    }

    @Bean(MALL_JD + SEPARATOR + INVOICE_APPLY_CANCEL)
    public MallCommand<InvoiceApplyCancelRequest<VopInvoiceCancelInvoiceApplyRequest>,
            VopInvoiceCancelInvoiceApplyRequest, InvoiceApplyCancelResponse> jdInvoiceApplyCancelCommand() {
        return (mallRequest -> {
            // 1. 获取京东请求凭证
            JdMallAuthentication authentication = (JdMallAuthentication) mallRequest.getAuthentication();

            // 2. 获取京东请求对象
            VopInvoiceCancelInvoiceApplyRequest jdRequest = mallRequest.getRequestObj();

            // 3. 执行京东请求
            JdMallRequest<VopInvoiceCancelInvoiceApplyResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
            VopInvoiceCancelInvoiceApplyResponse jdResponse = jdMallClient.execute(jdMallRequest);
            com.jd.open.api.sdk.domain.vopfp.OperaInvoiceOpenProvider.response.cancelInvoiceApply.OpenRpcResult result = jdResponse.getOpenRpcResult();
            if (!result.getSuccess()) {
                log.error("取消京东发票申请失败，错误码【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
                throw new JdMallException(result.getResultCode(), "取消京东发票申请失败：" + result.getResultMessage());
            }

            // 4. 转换为标准请求响应
            InvoiceApplyCancelResponse response = new InvoiceApplyCancelResponse();
            response.setSuccess(result.getSuccess());

            return response;
        });
    }

    @Bean(MALL_JD + SEPARATOR + INVOICE_DETAIL_QUERY)
    public MallCommand<InvoiceDetailQueryRequest<JdInvoiceDetailQueryRequest>,
            JdInvoiceDetailQueryRequest, InvoiceDetailQueryResponse> jdInvoiceDetailQueryCommand() {
        return (mallRequest -> {
            // 1. 获取京东请求凭证
            JdMallAuthentication authentication = (JdMallAuthentication) mallRequest.getAuthentication();

            // 2. 获取京东请求对象
            JdInvoiceDetailQueryRequest jdRequest = mallRequest.getRequestObj();

            // 3. 执行京东请求
            InvoiceType invoiceType = InvoiceType.getByValue(jdRequest.getInvoiceType());
            switch (invoiceType) {
                // 专票
                case TAX_INVOICE:
                    return queryTaxInvoiceDetail(authentication, jdRequest.getMarkId());
                // 电子票
                case ELECTRONIC_INVOICE:
                    return queryElectronicInvoiceDetail(authentication, jdRequest.getOrderIds());
                default:
                    String message = MessageFormat.format("不支持的发票类型：{0}", jdRequest.getInvoiceType());
                    log.error(message);
                    throw new JdMallException("未定义的发票类型【" + invoiceType.getDesc() + "】");
            }
        });
    }

    /**
     * 查询专票明细
     *
     * @param authentication 身份认证凭据
     * @param markId         申请单号
     * @return 发票详情查询响应
     */
    private InvoiceDetailQueryResponse queryTaxInvoiceDetail(JdMallAuthentication authentication, String markId) {
        // 1. 构建京东发票概要查询请求
        VopInvoiceQueryInvoiceOutlineRequest jdRequest = new VopInvoiceQueryInvoiceOutlineRequest();
        jdRequest.setMarkId(markId);

        // 2. 执行请求 -> 获取京东响应
        JdMallRequest<VopInvoiceQueryInvoiceOutlineResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
        VopInvoiceQueryInvoiceOutlineResponse jdResponse = jdMallClient.execute(jdMallRequest);
        com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceOutline.OpenRpcResult result = jdResponse.getOpenRpcResult();

        // 3. 解析京东响应
        if (!result.getSuccess()) {
            log.error("查询京东发票概要失败，错误码【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdMallException(result.getResultCode(), "查询京东发票概要失败：" + result.getResultMessage());
        }
        List<InvoiceOutlineOpenResp> respList = result.getResult();

        InvoiceDetailQueryResponse response = new InvoiceDetailQueryResponse();
        response.setDetailItems(Optional.ofNullable(respList).orElse(new ArrayList<>()).stream()
                .map(item -> {
                    InvoiceDetailQueryResponse.InvoiceDetailItem itemResponse = new InvoiceDetailQueryResponse.InvoiceDetailItem();
                    itemResponse.setInvoiceId(item.getInvoiceId());
                    itemResponse.setInvoiceCode(item.getInvoiceCode());
                    itemResponse.setInvoiceType(item.getInvoiceType());
                    itemResponse.setInvoiceDate(item.getInvoiceDate());
                    itemResponse.setInvoiceAmount(item.getInvoiceAmount());
                    itemResponse.setInvoiceNakedAmount(item.getInvoiceNakedAmount());
                    itemResponse.setInvoiceTaxAmount(item.getInvoiceTaxAmount());
                    itemResponse.setInvoiceTaxRate(item.getInvoiceTaxRate());
                    itemResponse.setFileUrl(item.getFileUrl());
                    itemResponse.setSuccess(item.getSuccess());

                    // 4. 构建京东专票明细查询请求
                    VopInvoiceQueryInvoiceDetailRequest jdDetailRequest = new VopInvoiceQueryInvoiceDetailRequest();
                    jdDetailRequest.setInvoiceId(item.getInvoiceId());
                    jdDetailRequest.setInvoiceCode(item.getInvoiceCode());

                    // 5. 执行请求 -> 获取京东响应
                    JdMallRequest<VopInvoiceQueryInvoiceDetailResponse> jdMallDetailRequest = new JdMallRequest<>(authentication, jdDetailRequest);
                    VopInvoiceQueryInvoiceDetailResponse jdDetailResponse = jdMallClient.execute(jdMallDetailRequest);
                    com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceDetail.OpenRpcResult jdDetailResult = jdDetailResponse.getOpenRpcResult();

                    // 6. 解析京东响应
                    if (!jdDetailResult.getSuccess()) {
                        log.error("查询京东发票明细失败，错误码【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
                        throw new JdMallException(result.getResultCode(), "查询京东发票明细失败：" + result.getResultMessage());
                    }
                    QueryInvoiceDetailOpenResp resp = jdDetailResult.getResult();
                    if (resp.getSkuDetailList() != null && resp.getSkuDetailList().size() > 0) {
                        itemResponse.setBusinessId(String.valueOf(resp.getSkuDetailList().get(0).getJdOrderId()));
                    }
                    itemResponse.setInvoiceTitle(resp.getTitle());
                    itemResponse.setRemark(resp.getRemark());

                    return itemResponse;
                }).collect(Collectors.toList()));

        return response;
    }

    /**
     * 查询电子发票明细
     *
     * @param authentication 身份认证凭据
     * @param orderIds       订单号列表
     * @return 发票详情查询响应
     */
    private InvoiceDetailQueryResponse queryElectronicInvoiceDetail(JdMallAuthentication authentication, List<Long> orderIds) {
        InvoiceDetailQueryResponse response = new InvoiceDetailQueryResponse();
        response.setDetailItems(orderIds.stream()
                .map(orderId -> {
                    // 1. 构建京东请求
                    VopInvoiceQueryElectronicInvoiceDetailRequest jdRequest = new VopInvoiceQueryElectronicInvoiceDetailRequest();
                    jdRequest.setJdOrderId(orderId);
                    jdRequest.setIvcType(3);

                    // 2. 执行请求 -> 获取京东响应
                    JdMallRequest<VopInvoiceQueryElectronicInvoiceDetailResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
                    VopInvoiceQueryElectronicInvoiceDetailResponse jdResponse = jdMallClient.execute(jdMallRequest);

                    // 3. 解析京东响应
                    com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryElectronicInvoiceDetail.OpenRpcResult result = jdResponse.getOpenRpcResult();
                    if (!result.getSuccess()) {
                        log.error("查询京东电子发票明细失败，错误码【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
                        throw new JdMallException(result.getResultCode(), "查询京东电子发票明细失败：" + result.getResultMessage());
                    }
                    QueryElectronicInvoiceDetailOpenResp resp = result.getResult();
                    List<InvoiceActualBillICOpenResp> respList = resp.getInvoiceActualBillICRespList();

                    return Optional.ofNullable(respList).orElse(new ArrayList<>()).stream()
                            .map(item -> {
                                InvoiceDetailQueryResponse.InvoiceDetailItem itemResponse = new InvoiceDetailQueryResponse.InvoiceDetailItem();
                                itemResponse.setBusinessId(item.getBusinessId());
                                itemResponse.setInvoiceId(item.getIvcNo());
                                itemResponse.setInvoiceCode(item.getIvcCode());
                                itemResponse.setInvoiceType(item.getIvcType());
                                itemResponse.setInvoiceDate(Date.from(LocalDate.parse(item.getInvoiceTime(),
                                        DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay(ZoneOffset.ofHours(8)).toInstant()));
                                itemResponse.setInvoiceTitle(item.getIvcTitle());
                                itemResponse.setInvoiceAmount(item.getTotalPrice());
                                itemResponse.setInvoiceTaxAmount(item.getTotalTaxPrice());
                                itemResponse.setInvoiceTaxRate(item.getTaxRate());
                                itemResponse.setRemark(item.getRemark());
                                itemResponse.setFileUrl(item.getFileUrl());

                                return itemResponse;
                            }).collect(Collectors.toList());
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));

        return response;
    }
}

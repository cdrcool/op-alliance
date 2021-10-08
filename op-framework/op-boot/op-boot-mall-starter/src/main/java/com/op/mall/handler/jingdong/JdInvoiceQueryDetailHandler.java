package com.op.mall.handler.jingdong;

import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryElectronicInvoiceDetail.InvoiceActualBillICOpenResp;
import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryElectronicInvoiceDetail.QueryElectronicInvoiceDetailOpenResp;
import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceDetail.QueryInvoiceDetailOpenResp;
import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceOutline.OpenRpcResult;
import com.jd.open.api.sdk.request.vopfp.VopInvoiceQueryElectronicInvoiceDetailRequest;
import com.jd.open.api.sdk.request.vopfp.VopInvoiceQueryInvoiceDetailRequest;
import com.jd.open.api.sdk.request.vopfp.VopInvoiceQueryInvoiceOutlineRequest;
import com.jd.open.api.sdk.response.vopfp.VopInvoiceQueryElectronicInvoiceDetailResponse;
import com.jd.open.api.sdk.response.vopfp.VopInvoiceQueryInvoiceDetailResponse;
import com.jd.open.api.sdk.response.vopfp.VopInvoiceQueryInvoiceOutlineResponse;
import com.op.mall.constans.InvoiceType;
import com.op.mall.client.jingdong.JdMallAuthentication;
import com.op.mall.client.jingdong.JdMallClient;
import com.op.mall.client.jingdong.JdMallRequest;
import com.op.mall.constans.MallMethodConstants;
import com.op.mall.constans.MallType;
import com.op.mall.exception.JdMallException;
import com.op.mall.handler.MallRequestHandlerRegistry;
import com.op.mall.request.InvoiceQueryDetailRequest;
import com.op.mall.request.MallRequest;
import com.op.mall.request.MallRequestAction;
import com.op.mall.request.jingdong.JdInvoiceQueryDetailRequest;
import com.op.mall.response.InvoiceQueryDetailResponse;
import com.op.mall.response.MallResponse;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 京东发票查看详情处理类
 *
 * @author chengdr01
 */
@Slf4j
public class JdInvoiceQueryDetailHandler extends JdMallRequestHandler {

    public JdInvoiceQueryDetailHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public <T extends MallRequest<R>, R extends MallResponse> R handle(T request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 1. 解析请求对象 -> 京东发票查询详情请求
        InvoiceQueryDetailRequest concreteRequest = (InvoiceQueryDetailRequest) request;
        Object requestObj = concreteRequest.getRequestObj();
        JdInvoiceQueryDetailRequest jdRequest = (JdInvoiceQueryDetailRequest) requestObj;

        // 2. 查询发票详情
        InvoiceType invoiceType = InvoiceType.getByValue(jdRequest.getInvoiceType());
        switch (invoiceType) {
            // 专票
            case TAX_INVOICE:
                return (R) queryTaxInvoiceDetail(authentication, jdRequest.getMarkId());
            // 电子票
            case ELECTRONIC_INVOICE:
                return (R) queryElectronicInvoiceDetail(authentication, jdRequest.getSubOrderId());
            default:
                String message = MessageFormat.format("不支持的发票类型：{0}", jdRequest.getInvoiceType());
                log.error(message);
                throw new JdMallException(message);
        }
    }

    /**
     * 查询京东专票明细
     *
     * @param authentication 京东电商身份认证凭据
     * @param markId         第三方申请单号
     * @return 发票查询详情响应
     */
    private InvoiceQueryDetailResponse queryTaxInvoiceDetail(JdMallAuthentication authentication, String markId) {
        // 1. 构建京东请求
        VopInvoiceQueryInvoiceOutlineRequest request = new VopInvoiceQueryInvoiceOutlineRequest();
        request.setMarkId(markId);

        // 2. 执行请求 -> 获取京东响应
        JdMallRequest<VopInvoiceQueryInvoiceOutlineResponse> jdMallRequest = new JdMallRequest<>(authentication, request);
        VopInvoiceQueryInvoiceOutlineResponse response = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = response.getOpenRpcResult();

        // 3. 解析京东响应
        if (result.getSuccess()) {
            InvoiceQueryDetailResponse mallResponse = new InvoiceQueryDetailResponse();
            mallResponse.setInvoiceItems(Optional.ofNullable(result.getResult()).orElse(new ArrayList<>()).stream()
                    .map(item -> {
                        InvoiceQueryDetailResponse.InvoiceQueryDetailItemResponse itemResponse = new InvoiceQueryDetailResponse.InvoiceQueryDetailItemResponse();
                        itemResponse.setInvoiceId(item.getInvoiceId());
                        itemResponse.setInvoiceCode(item.getInvoiceCode());
                        itemResponse.setInvoiceType(item.getInvoiceType());
                        itemResponse.setInvoiceDate(item.getInvoiceDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                        itemResponse.setInvoiceAmount(item.getInvoiceAmount());
                        itemResponse.setInvoiceNakedAmount(item.getInvoiceNakedAmount());
                        itemResponse.setInvoiceTaxAmount(item.getInvoiceTaxAmount());
                        itemResponse.setInvoiceTaxRate(item.getInvoiceTaxRate());
                        itemResponse.setFileUrl(item.getFileUrl());
                        itemResponse.setSuccess(item.getSuccess());

                        // 4. 构建京东请求
                        VopInvoiceQueryInvoiceDetailRequest request2 = new VopInvoiceQueryInvoiceDetailRequest();
                        request2.setInvoiceId(item.getInvoiceId());
                        request2.setInvoiceCode(item.getInvoiceCode());

                        // 5. 执行请求 -> 获取京东响应
                        JdMallRequest<VopInvoiceQueryInvoiceDetailResponse> jdMallRequest2 = new JdMallRequest<>(authentication, request2);
                        VopInvoiceQueryInvoiceDetailResponse response2 = getJdMallClient().execute(jdMallRequest2);
                        com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceDetail.OpenRpcResult result2 = response2.getOpenRpcResult();

                        // 6. 解析京东响应
                        if (result2.getSuccess()) {
                            QueryInvoiceDetailOpenResp resp = result2.getResult();
                            if (resp.getSkuDetailList() != null && resp.getSkuDetailList().size() > 0) {
                                itemResponse.setBusinessId(String.valueOf(resp.getSkuDetailList().get(0).getJdOrderId()));
                            }
                            itemResponse.setInvoiceTitle(resp.getTitle());
                            itemResponse.setRemark(resp.getRemark());
                        } else {
                            String message = MessageFormat.format("查询京东发票明细失败，错误码：{}，错误消息：{}",
                                    result.getResultCode(), result.getResultMessage());
                            log.error(message);
                            throw new JdMallException(message);
                        }

                        return itemResponse;
                    }).collect(Collectors.toList()));

            return mallResponse;
        } else {
            String message = MessageFormat.format("查询京东发票概要失败，错误码：{}，错误消息：{}",
                    result.getResultCode(), result.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    /**
     * 查询京东电子发票明细
     *
     * @param authentication 京东电商身份认证凭据
     * @param subOrderId     京东子单号
     * @return 发票查询详情响应
     */
    private InvoiceQueryDetailResponse queryElectronicInvoiceDetail(JdMallAuthentication authentication, Long subOrderId) {
        // 1. 构建京东请求
        VopInvoiceQueryElectronicInvoiceDetailRequest jdRequest = new VopInvoiceQueryElectronicInvoiceDetailRequest();
        jdRequest.setIvcType(3);
        jdRequest.setJdOrderId(subOrderId);

        // 2. 执行请求 -> 获取京东响应
        JdMallRequest<VopInvoiceQueryElectronicInvoiceDetailResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
        VopInvoiceQueryElectronicInvoiceDetailResponse response = getJdMallClient().execute(jdMallRequest);

        // 3. 解析京东响应
        com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryElectronicInvoiceDetail.OpenRpcResult result = response.getOpenRpcResult();
        if (result.getSuccess()) {
            QueryElectronicInvoiceDetailOpenResp resp = result.getResult();
            List<InvoiceActualBillICOpenResp> respList = resp.getInvoiceActualBillICRespList();

            InvoiceQueryDetailResponse mallResponse = new InvoiceQueryDetailResponse();
            mallResponse.setInvoiceItems(Optional.ofNullable(respList).orElse(new ArrayList<>()).stream()
                    .map(item -> {
                        InvoiceQueryDetailResponse.InvoiceQueryDetailItemResponse itemResponse = new InvoiceQueryDetailResponse.InvoiceQueryDetailItemResponse();
                        itemResponse.setBusinessId(item.getBusinessId());
                        itemResponse.setInvoiceId(item.getIvcNo());
                        itemResponse.setInvoiceCode(item.getIvcCode());
                        itemResponse.setInvoiceType(item.getIvcType());
                        itemResponse.setInvoiceDate(LocalDateTime.parse(item.getInvoiceTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
                        itemResponse.setInvoiceTitle(item.getIvcTitle());
                        itemResponse.setInvoiceAmount(item.getTotalPrice());
                        itemResponse.setInvoiceTaxAmount(item.getTotalTaxPrice());
                        itemResponse.setInvoiceTaxRate(item.getTaxRate());
                        itemResponse.setRemark(item.getRemark());
                        itemResponse.setFileUrl(item.getFileUrl());

                        return itemResponse;
                    }).collect(Collectors.toList()));
            return mallResponse;
        } else {
            String message = MessageFormat.format("查询京东电子发票明细失败，错误码：{}，错误消息：{}",
                    result.getResultCode(), result.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.INVOICE_QUERY_DETAIL), this);
    }
}

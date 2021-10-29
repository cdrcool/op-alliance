package com.op.boot.mall.handler.jingdong.invoice;

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
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.InvoiceType;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.invoice.InvoiceQueryDetailRequest;
import com.op.boot.mall.request.invoice.jingdong.JdInvoiceQueryDetailRequest;
import com.op.boot.mall.response.invoice.InvoiceQueryDetailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 京东发票查看详情处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdInvoiceQueryDetailHandler extends JdMallRequestHandler<InvoiceQueryDetailRequest<JdInvoiceQueryDetailRequest>,
        JdInvoiceQueryDetailRequest, InvoiceQueryDetailResponse> {

    public JdInvoiceQueryDetailHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public InvoiceQueryDetailResponse handle(InvoiceQueryDetailRequest<JdInvoiceQueryDetailRequest> request) {
        // 1. 获取京东请求凭证
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 2. 获取京东请求对象
        JdInvoiceQueryDetailRequest jdRequest = request.getRequestObj();

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
                throw new JdMallException(message);
        }
    }

    /**
     * 查询专票明细
     *
     * @param authentication 身份认证凭据
     * @param markId         第三方申请单号
     * @return 发票查询详情响应
     */
    private InvoiceQueryDetailResponse queryTaxInvoiceDetail(JdMallAuthentication authentication, String markId) {
        // 1. 构建京东请求
        VopInvoiceQueryInvoiceOutlineRequest jdRequest = new VopInvoiceQueryInvoiceOutlineRequest();
        jdRequest.setMarkId(markId);

        // 2. 执行请求 -> 获取京东响应
        JdMallRequest<VopInvoiceQueryInvoiceOutlineResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
        VopInvoiceQueryInvoiceOutlineResponse jdResponse = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = jdResponse.getOpenRpcResult();

        // 3. 解析京东响应
        if (result.getSuccess()) {
            InvoiceQueryDetailResponse response = new InvoiceQueryDetailResponse();
            response.setInvoiceItems(Optional.ofNullable(result.getResult()).orElse(new ArrayList<>()).stream()
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
                            log.error("查询京东发票明细失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
                            throw new JdMallException(result.getResultCode(), "查询京东发票明细失败：" + result.getResultMessage());
                        }

                        return itemResponse;
                    }).collect(Collectors.toList()));

            return response;
        } else {
            log.error("查询京东发票概要失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdMallException(result.getResultCode(), "查询京东发票概要失败：" + result.getResultMessage());
        }
    }

    /**
     * 查询电子发票明细
     *
     * @param authentication 电商身份认证凭据
     * @param orderIds       订单号列表
     * @return 发票查询详情响应
     */
    private InvoiceQueryDetailResponse queryElectronicInvoiceDetail(JdMallAuthentication authentication, List<Long> orderIds) {
        InvoiceQueryDetailResponse response = new InvoiceQueryDetailResponse();
        response.setInvoiceItems(orderIds.stream()
                .map(orderId -> {
                    // 1. 构建京东请求
                    VopInvoiceQueryElectronicInvoiceDetailRequest jdRequest = new VopInvoiceQueryElectronicInvoiceDetailRequest();
                    jdRequest.setIvcType(3);
                    jdRequest.setJdOrderId(orderId);

                    // 2. 执行请求 -> 获取京东响应
                    JdMallRequest<VopInvoiceQueryElectronicInvoiceDetailResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
                    VopInvoiceQueryElectronicInvoiceDetailResponse jdResponse = getJdMallClient().execute(jdMallRequest);

                    // 3. 解析京东响应
                    com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryElectronicInvoiceDetail.OpenRpcResult result = jdResponse.getOpenRpcResult();
                    if (result.getSuccess()) {
                        QueryElectronicInvoiceDetailOpenResp resp = result.getResult();
                        List<InvoiceActualBillICOpenResp> respList = resp.getInvoiceActualBillICRespList();

                        return Optional.ofNullable(respList).orElse(new ArrayList<>()).stream()
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
                                }).collect(Collectors.toList());
                    } else {
                        log.error("查询京东电子发票明细失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
                        throw new JdMallException(result.getResultCode(), "查询京东电子发票明细失败：" + result.getResultMessage());
                    }
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));

        return response;
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.INVOICE_QUERY_DETAIL), this);
    }
}

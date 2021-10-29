package com.op.boot.mall.handler.jingdong.invoice;

import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceDeliveryNo.InvoiceLogisticsInformationOpenResp;
import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceWaybill.InvoiceDeliveryOpenResp;
import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceWaybill.OpenRpcResult;
import com.jd.open.api.sdk.request.vopfp.VopInvoiceQueryInvoiceDeliveryNoRequest;
import com.jd.open.api.sdk.request.vopfp.VopInvoiceQueryInvoiceWaybillRequest;
import com.jd.open.api.sdk.response.vopfp.VopInvoiceQueryInvoiceDeliveryNoResponse;
import com.jd.open.api.sdk.response.vopfp.VopInvoiceQueryInvoiceWaybillResponse;
import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.invoice.InvoiceQueryDeliveryRequest;
import com.op.boot.mall.request.invoice.jingdong.JdInvoiceQueryDeliveryRequest;
import com.op.boot.mall.response.invoice.InvoiceQueryDeliveryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 京东发票查询物流处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdInvoiceQueryDeliveryHandler extends JdMallRequestHandler<InvoiceQueryDeliveryRequest<JdInvoiceQueryDeliveryRequest>,
        JdInvoiceQueryDeliveryRequest, InvoiceQueryDeliveryResponse> {

    public JdInvoiceQueryDeliveryHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public InvoiceQueryDeliveryResponse handle(InvoiceQueryDeliveryRequest<JdInvoiceQueryDeliveryRequest> request) {
        // 1. 获取京东请求凭证
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 2. 获取京东请求对象
        JdInvoiceQueryDeliveryRequest jdRequest = request.getRequestObj();

        // 3. 查询发票运单号
        List<InvoiceDeliveryOpenResp> wayBillInfoList = queryWayBillInfo(authentication, jdRequest.getMarkId());

        // 4. 查询发票物流信息
        List<InvoiceLogisticsInformationOpenResp> logisticsInfoList = queryLogisticsInfo(authentication, jdRequest.getOrderIds());
        Map<String, List<InvoiceLogisticsInformationOpenResp>> logisticsInfoMap = Optional.ofNullable(logisticsInfoList).orElse(new ArrayList<>()).stream()
                .collect(Collectors.groupingBy(InvoiceLogisticsInformationOpenResp::getWaybillCode));

        // 5. 转换为标准请求响应
        InvoiceQueryDeliveryResponse response = new InvoiceQueryDeliveryResponse();
        response.setDeliveryItems(wayBillInfoList.stream().map(wayBillInfo -> {
            InvoiceQueryDeliveryResponse.InvoiceQueryDeliveryItemResponse deliveryResponse = new InvoiceQueryDeliveryResponse.InvoiceQueryDeliveryItemResponse();
            deliveryResponse.setPostCompany(wayBillInfo.getPostCompany());
            deliveryResponse.setDeliveryId(wayBillInfo.getDeliveryId());
            deliveryResponse.setPostTime(wayBillInfo.getPostTime());
            deliveryResponse.setIvcPostId(wayBillInfo.getIvcPostId());
            deliveryResponse.setState(wayBillInfo.getState());

            List<InvoiceLogisticsInformationOpenResp> curLogisticsInfoList = logisticsInfoMap.getOrDefault(wayBillInfo.getDeliveryId(), new ArrayList<>());
            deliveryResponse.setDetails(curLogisticsInfoList.stream().map(logisticsInfo -> {
                InvoiceQueryDeliveryResponse.InvoiceQueryDeliveryItemDetailResponse detailResponse = new InvoiceQueryDeliveryResponse.InvoiceQueryDeliveryItemDetailResponse();
                detailResponse.setOpeTitle(logisticsInfo.getOpeTitle());
                detailResponse.setOpeTime(logisticsInfo.getOpeTime());
                detailResponse.setOpeRemark(logisticsInfo.getOpeRemark());
                detailResponse.setOpeName(logisticsInfo.getOpeName());
                detailResponse.setWaybillCode(logisticsInfo.getWaybillCode());
                return detailResponse;
            }).collect(Collectors.toList()));

            return deliveryResponse;
        }).collect(Collectors.toList()));

        return response;
    }

    private List<InvoiceDeliveryOpenResp> queryWayBillInfo(MallAuthentication authentication, String markId) {
        VopInvoiceQueryInvoiceWaybillRequest jdRequest = new VopInvoiceQueryInvoiceWaybillRequest();
        jdRequest.setMarkId(markId);
        JdMallRequest<VopInvoiceQueryInvoiceWaybillResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);

        VopInvoiceQueryInvoiceWaybillResponse jdResponse = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = jdResponse.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("查询京东发票运单号信息失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdMallException(result.getResultCode(), "查询京东发票运单号信息失败：" + result.getResultMessage());
        }
        return result.getResult();
    }

    private List<InvoiceLogisticsInformationOpenResp> queryLogisticsInfo(MallAuthentication authentication, List<Long> orderIds) {
        return orderIds.stream()
                .map(orderId -> {
                    VopInvoiceQueryInvoiceDeliveryNoRequest jdRequest = new VopInvoiceQueryInvoiceDeliveryNoRequest();
                    jdRequest.setJdOrderId(String.valueOf(orderId));
                    JdMallRequest<VopInvoiceQueryInvoiceDeliveryNoResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);

                    VopInvoiceQueryInvoiceDeliveryNoResponse jdResponse = getJdMallClient().execute(jdMallRequest);
                    com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceDeliveryNo.OpenRpcResult result = jdResponse.getOpenRpcResult();
                    if (!result.getSuccess()) {
                        log.error("查询京东发票物流信息失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
                        throw new JdMallException(result.getResultCode(), "查询京东发票物流信息失败：" + result.getResultMessage());
                    }
                    return result.getResult();
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.INVOICE_QUERY_DELIVERY), this);
    }
}

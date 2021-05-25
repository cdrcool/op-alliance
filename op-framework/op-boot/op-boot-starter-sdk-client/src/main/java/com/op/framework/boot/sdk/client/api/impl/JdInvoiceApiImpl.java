package com.op.framework.boot.sdk.client.api.impl;

import com.jd.open.api.sdk.domain.vopfp.OperaInvoiceOpenProvider.response.submitInvoiceApply.OpenRpcResult;
import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryElectronicInvoiceDetail.QueryElectronicInvoiceDetailOpenResp;
import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceDeliveryNo.InvoiceLogisticsInformationOpenResp;
import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceDetail.QueryInvoiceDetailOpenResp;
import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceOutline.InvoiceOutlineOpenResp;
import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceWaybill.InvoiceDeliveryOpenResp;
import com.jd.open.api.sdk.request.vopfp.*;
import com.jd.open.api.sdk.response.vopfp.*;
import com.op.framework.boot.sdk.client.api.InvoiceApi;
import com.op.framework.boot.sdk.client.base.JdSdkClient;
import com.op.framework.boot.sdk.client.base.JdSdkRequest;
import com.op.framework.boot.sdk.client.exception.JdInvokeException;
import com.op.framework.boot.sdk.client.request.*;
import com.op.framework.boot.sdk.client.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 京东发票 API 接口
 *
 * @author cdrcool
 */
@Slf4j
@Service
public class JdInvoiceApiImpl implements InvoiceApi {
    private final JdSdkClient jdSdkClient;

    public JdInvoiceApiImpl(JdSdkClient jdSdkClient) {
        this.jdSdkClient = jdSdkClient;
    }

    @Override
    public boolean submitInvoiceApply(String token, InvoiceApplySubmitRequest invoiceApplySubmitRequest) {
        VopInvoiceSubmitInvoiceApplyRequest request = invoiceApplySubmitRequest.toJdInvoiceApplySubmitRequest();

        VopInvoiceSubmitInvoiceApplyResponse response = jdSdkClient.execute(new JdSdkRequest<>(token, request));
        OpenRpcResult result = response.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("提交京东发票申请失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdInvokeException(result.getResultCode(), "提交京东发票申请失败：" + result.getResultMessage());
        }

        return result.getResult();
    }

    @Override
    public Boolean cancelInvoiceApply(String token, InvoiceApplyCancelRequest invoiceApplyCancelRequest) {
        VopInvoiceCancelInvoiceApplyRequest request = new VopInvoiceCancelInvoiceApplyRequest();
        request.setMarkId(invoiceApplyCancelRequest.getMarkId());

        VopInvoiceCancelInvoiceApplyResponse response = jdSdkClient.execute(new JdSdkRequest<>(token, request));
        com.jd.open.api.sdk.domain.vopfp.OperaInvoiceOpenProvider.response.cancelInvoiceApply.OpenRpcResult result = response.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("取消京东发票申请失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdInvokeException(result.getResultCode(), "取消京东发票申请失败：" + result.getResultMessage());
        }

        return result.getResult();
    }

    @Override
    public List<InvoiceOutlineResponse> queryInvoiceOutline(String token, InvoiceOutlineRequest invoiceOutlineRequest) {
        VopInvoiceQueryInvoiceOutlineRequest request = new VopInvoiceQueryInvoiceOutlineRequest();
        request.setMarkId(invoiceOutlineRequest.getMarkId());
        VopInvoiceQueryInvoiceOutlineResponse response = jdSdkClient.execute(new JdSdkRequest<>(token, request));

        com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceOutline.OpenRpcResult result = response.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("查询京东发票概要失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdInvokeException(result.getResultCode(), "查询京东发票概要失败：" + result.getResultMessage());
        }

        List<InvoiceOutlineOpenResp> respList = result.getResult();
        return respList.stream().map(InvoiceOutlineResponse::buildFrom).collect(Collectors.toList());
    }

    @Override
    public InvoiceDetailResponse queryInvoiceDetail(String token, InvoiceDetailRequest invoiceDetailRequest) {
        VopInvoiceQueryInvoiceDetailRequest request = new VopInvoiceQueryInvoiceDetailRequest();
        request.setInvoiceId(invoiceDetailRequest.getInvoiceId());
        request.setInvoiceCode(invoiceDetailRequest.getInvoiceCode());

        VopInvoiceQueryInvoiceDetailResponse response = jdSdkClient.execute(new JdSdkRequest<>(token, request));
        com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceDetail.OpenRpcResult result = response.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("查询京东发票明细失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdInvokeException(result.getResultCode(), "查询京东发票明细失败：" + result.getResultMessage());
        }

        QueryInvoiceDetailOpenResp resp = result.getResult();
        return InvoiceDetailResponse.buildFrom(resp);
    }

    @Override
    public List<InvoiceElectronicDetailResponse> queryElectronicInvoiceDetail(String token, InvoiceElectronicDetailRequest invoiceElectronicDetailRequest) {
        VopInvoiceQueryElectronicInvoiceDetailRequest request = new VopInvoiceQueryElectronicInvoiceDetailRequest();
        request.setJdOrderId(Long.valueOf(invoiceElectronicDetailRequest.getSubOrderIds().get(0)));
        request.setIvcType(3);
        VopInvoiceQueryElectronicInvoiceDetailResponse response = jdSdkClient.execute(new JdSdkRequest<>(token, request));

        com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryElectronicInvoiceDetail.OpenRpcResult result = response.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("查询京东电子发票明细失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdInvokeException(result.getResultCode(), "查询京东电子发票明细失败：" + result.getResultMessage());
        }

        QueryElectronicInvoiceDetailOpenResp resp = result.getResult();
        return resp.getInvoiceActualBillICRespList().stream().map(InvoiceElectronicDetailResponse::buildFrom).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceWaybillNoResponse> queryInvoiceWaybillNo(String token, InvoiceWaybillNoRequest invoiceWaybillNoRequest) {
        VopInvoiceQueryInvoiceWaybillRequest request = new VopInvoiceQueryInvoiceWaybillRequest();
        request.setMarkId(invoiceWaybillNoRequest.getMarkId());

        VopInvoiceQueryInvoiceWaybillResponse response = jdSdkClient.execute(new JdSdkRequest<>(token, request));
        com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceWaybill.OpenRpcResult result = response.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("查询京东发票物流信息失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdInvokeException(result.getResultCode(), "查询京东发票物流信息失败：" + result.getResultMessage());
        }

        List<InvoiceDeliveryOpenResp> respList = result.getResult();
        return respList.stream().map(InvoiceWaybillNoResponse::buildFrom).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDeliveryResponse> queryInvoiceDeliveryNo(String token, InvoiceDeliveryRequest invoiceDeliveryRequest) {
        String markId = queryInvoiceThirdApplyNo(token, invoiceDeliveryRequest.getSubOrderIds().get(0));
        InvoiceWaybillNoRequest invoiceWaybillNoRequest = new InvoiceWaybillNoRequest();
        invoiceWaybillNoRequest.setMarkId(markId);
        List<InvoiceWaybillNoResponse> waybillNoResponses = queryInvoiceWaybillNo(token, invoiceWaybillNoRequest);

        List<InvoiceLogisticsInformationOpenResp> respList = invoiceDeliveryRequest.getSubOrderIds().stream().map(subOrderId -> {
            VopInvoiceQueryInvoiceDeliveryNoRequest request = new VopInvoiceQueryInvoiceDeliveryNoRequest();
            request.setJdOrderId(subOrderId);

            VopInvoiceQueryInvoiceDeliveryNoResponse response = jdSdkClient.execute(new JdSdkRequest<>(token, request));
            com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceDeliveryNo.OpenRpcResult result = response.getOpenRpcResult();
            if (!result.getSuccess()) {
                log.error("根据订单号查询京东发票物流信息失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
                throw new JdInvokeException(result.getResultCode(), "根据订单号查询京东发票物流信息失败：" + result.getResultMessage());
            }

            return result.getResult();
        }).flatMap(Collection::stream).collect(Collectors.toList());

        Map<String, List<InvoiceLogisticsInformationOpenResp>> respListMap =
                respList.stream().collect(Collectors.groupingBy(InvoiceLogisticsInformationOpenResp::getWaybillCode));

        return waybillNoResponses.stream().map(response -> {
            InvoiceDeliveryResponse deliveryResponse = new InvoiceDeliveryResponse();
            deliveryResponse.setPostCompany(response.getPostCompany());
            deliveryResponse.setDeliveryId(response.getDeliveryId());
            deliveryResponse.setPostTime(response.getPostTime());
            deliveryResponse.setIvcPostId(response.getIvcPostId());
            deliveryResponse.setState(response.getState());

            List<InvoiceLogisticsInformationOpenResp> respListItem = respListMap.getOrDefault(response.getDeliveryId(), new ArrayList<>());
            deliveryResponse.setDetails(respListItem.stream().map(InvoiceLogisticsResponse::buildFrom).collect(Collectors.toList()));

            return deliveryResponse;
        }).collect(Collectors.toList());
    }

    private String queryInvoiceThirdApplyNo(String token, String orderId) {
        VopInvoiceQueryInvoiceThirdApplyNoRequest request = new VopInvoiceQueryInvoiceThirdApplyNoRequest();
        request.setJdOrderId(orderId);

        VopInvoiceQueryInvoiceThirdApplyNoResponse response = jdSdkClient.execute(new JdSdkRequest<>(token, request));
        com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceThirdApplyNo.OpenRpcResult result = response.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("查询京东发票第三方申请单号失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdInvokeException(result.getResultCode(), "查询京东发票第三方申请单号失败：" + result.getResultMessage());
        }

        return result.getResult();
    }
}

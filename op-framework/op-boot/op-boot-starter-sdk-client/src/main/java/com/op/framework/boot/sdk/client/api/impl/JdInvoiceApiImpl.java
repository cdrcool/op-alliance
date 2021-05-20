package com.op.framework.boot.sdk.client.api.impl;

import com.jd.open.api.sdk.domain.vopfp.OperaInvoiceOpenProvider.response.submitInvoiceApply.OpenRpcResult;
import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceDeliveryNo.InvoiceLogisticsInformationOpenResp;
import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceDetail.QueryInvoiceDetailOpenResp;
import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceWaybill.InvoiceDeliveryOpenResp;
import com.jd.open.api.sdk.request.vopfp.*;
import com.jd.open.api.sdk.response.vopfp.*;
import com.op.framework.boot.sdk.client.api.InvoiceApi;
import com.op.framework.boot.sdk.client.base.JdSdkClient;
import com.op.framework.boot.sdk.client.exception.JdInvokeException;
import com.op.framework.boot.sdk.client.request.InvoiceApplySubmitRequest;
import com.op.framework.boot.sdk.client.response.InvoiceDeliveryResponse;
import com.op.framework.boot.sdk.client.response.InvoiceDetailResponse;
import com.op.framework.boot.sdk.client.response.InvoiceLogisticsResponse;
import lombok.extern.slf4j.Slf4j;

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
public class JdInvoiceApiImpl implements InvoiceApi {
    private final JdSdkClient jdSdkClient;

    public JdInvoiceApiImpl(JdSdkClient jdSdkClient) {
        this.jdSdkClient = jdSdkClient;
    }

    @Override
    public boolean submitInvoiceApply(InvoiceApplySubmitRequest invoiceApplySubmitRequest) {
        VopInvoiceSubmitInvoiceApplyRequest request = invoiceApplySubmitRequest.toJdInvoiceApplySubmitRequest();

        VopInvoiceSubmitInvoiceApplyResponse response = jdSdkClient.execute(request);
        OpenRpcResult result = response.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("提交京东发票申请失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdInvokeException(result.getResultCode(), "提交京东发票申请失败：" + result.getResultMessage());
        }

        return result.getResult();
    }

    @Override
    public Boolean cancelInvoiceApply(String markId) {
        VopInvoiceCancelInvoiceApplyRequest request = new VopInvoiceCancelInvoiceApplyRequest();
        request.setMarkId(markId);

        VopInvoiceCancelInvoiceApplyResponse response = jdSdkClient.execute(request);
        com.jd.open.api.sdk.domain.vopfp.OperaInvoiceOpenProvider.response.cancelInvoiceApply.OpenRpcResult result = response.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("取消京东发票申请失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdInvokeException(result.getResultCode(), "取消京东发票申请失败：" + result.getResultMessage());
        }

        return result.getResult();
    }

    @Override
    public List<InvoiceDeliveryResponse> queryInvoiceWaybillNo(String markId) {
        VopInvoiceQueryInvoiceWaybillRequest request = new VopInvoiceQueryInvoiceWaybillRequest();
        request.setMarkId(markId);

        VopInvoiceQueryInvoiceWaybillResponse response = jdSdkClient.execute(request);
        com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceWaybill.OpenRpcResult result = response.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("查询京东发票物流信息失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdInvokeException(result.getResultCode(), "查询京东发票物流信息失败：" + result.getResultMessage());
        }

        List<InvoiceDeliveryOpenResp> respList = result.getResult();
        return respList.stream().map(InvoiceDeliveryResponse::buildFrom).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDeliveryResponse> queryInvoiceDeliveryNo(String orderId, List<String> subOrderIds) {
        String markId = queryInvoiceThirdApplyNo(orderId);
        List<InvoiceDeliveryResponse> deliveryResponses = queryInvoiceWaybillNo(markId);

        List<InvoiceLogisticsInformationOpenResp> respList = subOrderIds.stream().map(subOrderId -> {
            VopInvoiceQueryInvoiceDeliveryNoRequest request = new VopInvoiceQueryInvoiceDeliveryNoRequest();
            request.setJdOrderId(subOrderId);

            VopInvoiceQueryInvoiceDeliveryNoResponse response = jdSdkClient.execute(request);
            com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceDeliveryNo.OpenRpcResult result = response.getOpenRpcResult();
            if (!result.getSuccess()) {
                log.error("根据订单号查询京东发票物流信息失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
                throw new JdInvokeException(result.getResultCode(), "根据订单号查询京东发票物流信息失败：" + result.getResultMessage());
            }

            return result.getResult();
        }).flatMap(Collection::stream).collect(Collectors.toList());

        Map<String, List<InvoiceLogisticsInformationOpenResp>> respListMap =
                respList.stream().collect(Collectors.groupingBy(InvoiceLogisticsInformationOpenResp::getWaybillCode));

        deliveryResponses.forEach(deliveryResponse -> {
            List<InvoiceLogisticsInformationOpenResp> respListItem = respListMap.getOrDefault(deliveryResponse.getDeliveryId(), new ArrayList<>());
            deliveryResponse.setDetails(respListItem.stream().map(InvoiceLogisticsResponse::buildFrom).collect(Collectors.toList()));
        });

        return deliveryResponses;
    }

    private String queryInvoiceThirdApplyNo(String orderId) {
        VopInvoiceQueryInvoiceThirdApplyNoRequest request = new VopInvoiceQueryInvoiceThirdApplyNoRequest();
        request.setJdOrderId(orderId);

        VopInvoiceQueryInvoiceThirdApplyNoResponse response = jdSdkClient.execute(request);
        com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceThirdApplyNo.OpenRpcResult result = response.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("查询京东发票第三方申请单号失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdInvokeException(result.getResultCode(), "查询京东发票第三方申请单号失败：" + result.getResultMessage());
        }

        return result.getResult();
    }

    @Override
    public InvoiceDetailResponse queryInvoiceDetail(String invoiceCode, String invoiceId) {
        VopInvoiceQueryInvoiceDetailRequest request = new VopInvoiceQueryInvoiceDetailRequest();
        request.setInvoiceCode(invoiceCode);
        request.setInvoiceId(invoiceId);

        VopInvoiceQueryInvoiceDetailResponse response = jdSdkClient.execute(request);
        com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceDetail.OpenRpcResult result = response.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("查询京东发票明细失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdInvokeException(result.getResultCode(), "查询京东发票明细失败：" + result.getResultMessage());
        }

        QueryInvoiceDetailOpenResp resp = result.getResult();
        return InvoiceDetailResponse.buildFrom(resp);
    }
}

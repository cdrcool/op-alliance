package com.op.framework.boot.sdk.client.api.impl;

import com.op.framework.boot.sdk.client.api.InvoiceApi;
import com.op.framework.boot.sdk.client.base.SnSdkClient;
import com.op.framework.boot.sdk.client.exception.SnInvokeException;
import com.op.framework.boot.sdk.client.request.InvoiceApplySubmitRequest;
import com.op.framework.boot.sdk.client.response.InvoiceDeliveryResponse;
import com.op.framework.boot.sdk.client.response.InvoiceDetailResponse;
import com.op.framework.boot.sdk.client.response.InvoiceElectronicDetailResponse;
import com.op.framework.boot.sdk.client.response.InvoiceOutlineResponse;
import com.suning.api.SuningResponse;
import com.suning.api.entity.govbus.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 苏宁发票 API 接口
 *
 * @author cdrcool
 */
@Slf4j
public class SnInvoiceApiImpl implements InvoiceApi {
    private final SnSdkClient snSdkClient;

    public SnInvoiceApiImpl(SnSdkClient snSdkClient) {
        this.snSdkClient = snSdkClient;
    }

    @Override
    public boolean submitInvoiceApply(InvoiceApplySubmitRequest invoiceApplySubmitRequest) {
        InvoicesupplementConfirmRequest request = invoiceApplySubmitRequest.toSnInvoiceApplySubmitRequest();

        InvoicesupplementConfirmResponse response = snSdkClient.execute(request);
        SuningResponse.SnError snError = response.getSnerror();
        if (snError != null) {
            log.error("提交苏宁发票申请失败，错误码：【{}】，错误消息：【{}】", snError.getErrorCode(), snError.getErrorMsg());
            throw new SnInvokeException(snError.getErrorCode(), "提交苏宁发票申请失败：" + snError.getErrorMsg());
        }

        InvoicesupplementConfirmResponse.SnBody snBody = response.getSnbody();
        InvoicesupplementConfirmResponse.ConfirmInvoicesupplement confirmInvoicesupplement = snBody.getConfirmInvoicesupplement();
        if ("N".equals(confirmInvoicesupplement.getCampSuccess())) {
            log.error("提交苏宁发票申请失败");
            throw new SnInvokeException("提交苏宁发票申请失败");
        }

        return true;
    }

    @Override
    public Boolean cancelInvoiceApply(String markId) {
        InvoicebatchDeleteRequest.BatchInfos batchInfos = new InvoicebatchDeleteRequest.BatchInfos();
        batchInfos.setBatchNo(markId);
        InvoicebatchDeleteRequest request = new InvoicebatchDeleteRequest();
        request.setBatchInfos(Collections.singletonList(batchInfos));

        InvoicebatchDeleteResponse response = snSdkClient.execute(request);
        SuningResponse.SnError snError = response.getSnerror();
        if (snError != null) {
            log.error("取消京东发票申请失败，错误码：【{}】，错误消息【{}】", snError.getErrorCode(), snError.getErrorMsg());
            throw new SnInvokeException(snError.getErrorCode(), "取消京东发票申请失败：" + snError.getErrorMsg());
        }

        InvoicebatchDeleteResponse.SnBody snBody = response.getSnbody();
        InvoicebatchDeleteResponse.DeleteInvoicebatch deleteInvoicebatch = snBody.getDeleteInvoicebatch();
        Optional<InvoicebatchDeleteResponse.FailBatchInfos> optional = Optional.ofNullable(deleteInvoicebatch.getFailBatchInfos())
                .orElse(new ArrayList<>()).stream().filter(item -> markId.equals(item.getBatchNo())).findAny();
        if (optional.isPresent()) {
            InvoicebatchDeleteResponse.FailBatchInfos failBatchInfos = optional.get();
            log.error("取消京东发票申请失败，错误码：【{}】，错误消息【{}】", failBatchInfos.getErrCode(), failBatchInfos.getErrMessage());
            throw new SnInvokeException(failBatchInfos.getErrCode(), "取消京东发票申请失败：" + failBatchInfos.getErrMessage());
        }

        return true;
    }

    @Override
    public List<InvoiceOutlineResponse> queryInvoiceOutline(String markId) {
        GetmarkidinvoiceQueryRequest request = new GetmarkidinvoiceQueryRequest();
        request.setMarkId(markId);
        request.setCurrentPage("1");
        request.setPageNumber("100");

        GetmarkidinvoiceQueryResponse response = snSdkClient.execute(request);
        SuningResponse.SnError snError = response.getSnerror();
        if (snError != null) {
            log.error("查询苏宁发票概要失败，错误码：【{}】，错误消息【{}】", snError.getErrorCode(), snError.getErrorMsg());
            throw new SnInvokeException(snError.getErrorCode(), "查询苏宁发票概要失败：" + snError.getErrorMsg());
        }

        GetmarkidinvoiceQueryResponse.SnBody snBody = response.getSnbody();
        GetmarkidinvoiceQueryResponse.QueryGetmarkidinvoice queryGetmarkidinvoice = snBody.getQueryGetmarkidinvoice();
        return queryGetmarkidinvoice.getInvoiceList().stream()
                .map(GetmarkidinvoiceQueryResponse.InvoiceList::getInvoiceInfo)
                .flatMap(Collection::stream)
                .map(InvoiceOutlineResponse::buildFrom)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDetailResponse queryInvoiceDetail(String invoiceCode, String invoiceId) {
        GetinvoicesurfaceinfoQueryRequest request = new GetinvoicesurfaceinfoQueryRequest();
        request.setInvoiceCode(invoiceCode);
        request.setInvoiceId(invoiceId);

        GetinvoicesurfaceinfoQueryResponse response = snSdkClient.execute(request);
        SuningResponse.SnError snError = response.getSnerror();
        if (snError != null) {
            log.error("查询苏宁发票明细失败，错误码：【{}】，错误消息【{}】", snError.getErrorCode(), snError.getErrorMsg());
            throw new SnInvokeException(snError.getErrorCode(), "查询苏宁发票明细失败：" + snError.getErrorMsg());
        }

        GetinvoicesurfaceinfoQueryResponse.SnBody snBody = response.getSnbody();
        GetinvoicesurfaceinfoQueryResponse.QueryGetinvoicesurfaceinfo queryGetinvoicesurfaceinfo = snBody.getQueryGetinvoicesurfaceinfo();
        return InvoiceDetailResponse.buildFrom(queryGetinvoicesurfaceinfo.getInvoiceList().get(0));
    }

    @Override
    public List<InvoiceElectronicDetailResponse> queryElectronicInvoiceDetail(String orderId, List<String> subOrderIds) {
        EleinvoiceGetRequest request = new EleinvoiceGetRequest();
        request.setOrderId(orderId);
        request.setOrderItems(subOrderIds.stream().map(subOrderId -> {
            EleinvoiceGetRequest.OrderItems orderItems = new EleinvoiceGetRequest.OrderItems();
            orderItems.setOrderItemId(subOrderId);

            return orderItems;
        }).collect(Collectors.toList()));

        EleinvoiceGetResponse response = snSdkClient.execute(request);
        SuningResponse.SnError snError = response.getSnerror();
        if (snError != null) {
            log.error("查询苏宁电子发票明细失败，错误码：【{}】，错误消息【{}】", snError.getErrorCode(), snError.getErrorMsg());
            throw new SnInvokeException(snError.getErrorCode(), "查询苏宁电子发票明细失败：" + snError.getErrorMsg());
        }

        EleinvoiceGetResponse.SnBody snBody = response.getSnbody();
        EleinvoiceGetResponse.GetEleinvoice getEleinvoice = snBody.getGetEleinvoice();
        return getEleinvoice.getEleInvoices().stream().map(InvoiceElectronicDetailResponse::buildFrom).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDeliveryResponse> queryInvoiceWaybillNo(String markId) {
        InvoicelogistGetRequest request = new InvoicelogistGetRequest();
        request.setType("1");
        request.setParameter(markId);

        InvoicelogistGetResponse response = snSdkClient.execute(request);
        SuningResponse.SnError snError = response.getSnerror();
        if (snError != null) {
            log.error("查询苏宁发票物流信息失败，错误码：【{}】，错误消息【{}】", snError.getErrorCode(), snError.getErrorMsg());
            throw new SnInvokeException(snError.getErrorCode(), "查询苏宁发票物流信息失败：" + snError.getErrorMsg());
        }

        InvoicelogistGetResponse.SnBody snBody = response.getSnbody();
        InvoicelogistGetResponse.GetInvoicelogist getInvoicelogist = snBody.getGetInvoicelogist();
        if ("N".equals(getInvoicelogist.getIslogistics())) {
            log.error("提交苏宁发票申请失败");
            throw new SnInvokeException("提交苏宁发票申请失败");
        }

        return getInvoicelogist.getLogistics().stream().map(InvoiceDeliveryResponse::buildFrom).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDeliveryResponse> queryInvoiceDeliveryNo(String orderId, List<String> subOrderIds) {
        LogistdetailGetRequest request = new LogistdetailGetRequest();
        request.setOrderId(orderId);
        request.setOrderItemIds(subOrderIds.stream().map(subOrderId -> {
            LogistdetailGetRequest.OrderItemIds orderItemIds = new LogistdetailGetRequest.OrderItemIds();
            orderItemIds.setOrderItemId(subOrderId);
            return orderItemIds;
        }).collect(Collectors.toList()));

        LogistdetailGetResponse response = snSdkClient.execute(request);
        SuningResponse.SnError snError = response.getSnerror();
        if (snError != null) {
            log.error("根据订单号查询苏宁发票物流信息失败，错误码：【{}】，错误消息【{}】", snError.getErrorCode(), snError.getErrorMsg());
            throw new SnInvokeException(snError.getErrorCode(), "根据订单号查询苏宁发票物流信息失败：" + snError.getErrorMsg());
        }

        LogistdetailGetResponse.SnBody snBody = response.getSnbody();
        LogistdetailGetResponse.GetLogistdetail getLogistdetail = snBody.getGetLogistdetail();
        return getLogistdetail.getLogistics().stream().map(InvoiceDeliveryResponse::buildFrom).collect(Collectors.toList());
    }
}

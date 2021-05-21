package com.op.framework.boot.sdk.client.api;

import com.op.framework.boot.sdk.client.request.InvoiceApplySubmitRequest;
import com.op.framework.boot.sdk.client.response.InvoiceDeliveryResponse;
import com.op.framework.boot.sdk.client.response.InvoiceDetailResponse;
import com.op.framework.boot.sdk.client.response.InvoiceElectronicDetailResponse;
import com.op.framework.boot.sdk.client.response.InvoiceOutlineResponse;

import java.util.List;

/**
 * 发票 API 接口
 *
 * @author cdrcool
 */
public interface InvoiceApi {

    /**
     * 提交发票申请
     *
     * @param invoiceApplySubmitRequest 发票申请提交对象
     * @return 是否成功
     */
    boolean submitInvoiceApply(InvoiceApplySubmitRequest invoiceApplySubmitRequest);

    /**
     * 取消发票申请
     *
     * @param markId 申请单号
     * @return 是否成功
     */
    Boolean cancelInvoiceApply(String markId);

    /**
     * 查询发票概要
     * <p>
     * 苏宁不支持电子票查询
     *
     * @param markId 申请单号
     * @return 发票概要列表
     */
    List<InvoiceOutlineResponse> queryInvoiceOutline(String markId);

    /**
     * 查询发票明细
     *
     * @param invoiceCode 发票代码
     * @param invoiceId   发票号码
     * @return 发票明细
     */
    InvoiceDetailResponse queryInvoiceDetail(String invoiceCode, String invoiceId);

    /**
     * 查询电子发票明细
     *
     * @param orderId     订单号
     * @param subOrderIds 子订单号
     * @return 电子发票明细
     */
    List<InvoiceElectronicDetailResponse> queryElectronicInvoiceDetail(String orderId, List<String> subOrderIds);

    /**
     * 查询发票运单号
     *
     * @param markId 申请单号
     * @return 发票运单号列表
     */
    List<InvoiceDeliveryResponse> queryInvoiceWaybillNo(String markId);

    /**
     * 查询发票物流信息
     *
     * @param orderId     订单号
     * @param subOrderIds 子订单号
     * @return 发票物流信息列表
     */
    List<InvoiceDeliveryResponse> queryInvoiceDeliveryNo(String orderId, List<String> subOrderIds);

}

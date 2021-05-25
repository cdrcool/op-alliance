package com.op.framework.boot.sdk.client.api;

import com.op.framework.boot.sdk.client.request.*;
import com.op.framework.boot.sdk.client.response.*;

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
     * @param token                     访问令牌
     * @param invoiceApplySubmitRequest 发票申请提交对象
     * @return 是否成功
     */
    boolean submitInvoiceApply(String token, InvoiceApplySubmitRequest invoiceApplySubmitRequest);

    /**
     * 取消发票申请
     *
     * @param token                     访问令牌
     * @param invoiceApplyCancelRequest 发票申请取消对象
     * @return 是否成功
     */
    Boolean cancelInvoiceApply(String token, InvoiceApplyCancelRequest invoiceApplyCancelRequest);

    /**
     * 查询发票概要
     * <p>
     * 苏宁不支持电子票查询
     *
     * @param token                 访问令牌
     * @param invoiceOutlineRequest 发票概要请求对象
     * @return 发票概要列表
     */
    List<InvoiceOutlineResponse> queryInvoiceOutline(String token, InvoiceOutlineRequest invoiceOutlineRequest);

    /**
     * 查询发票明细
     *
     * @param token                访问令牌
     * @param invoiceDetailRequest 发票详情请求对象
     * @return 发票明细
     */
    InvoiceDetailResponse queryInvoiceDetail(String token, InvoiceDetailRequest invoiceDetailRequest);

    /**
     * 查询电子发票明细
     *
     * @param token                          访问令牌
     * @param invoiceElectronicDetailRequest 电子发票明细请求对象
     * @return 电子发票明细
     */
    List<InvoiceElectronicDetailResponse> queryElectronicInvoiceDetail(String token, InvoiceElectronicDetailRequest invoiceElectronicDetailRequest);

    /**
     * 查询发票运单号
     *
     * @param token                   访问令牌
     * @param invoiceWaybillNoRequest 发票运单号请求对象
     * @return 发票运单号列表
     */
    List<InvoiceWaybillNoResponse> queryInvoiceWaybillNo(String token, InvoiceWaybillNoRequest invoiceWaybillNoRequest);

    /**
     * 查询发票物流信息
     *
     * @param token                  访问令牌
     * @param invoiceDeliveryRequest 发票物流请求对象
     * @return 发票物流信息列表
     */
    List<InvoiceDeliveryResponse> queryInvoiceDeliveryNo(String token, InvoiceDeliveryRequest invoiceDeliveryRequest);

}

package com.op.framework.boot.sdk.client.response;

import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryElectronicInvoiceDetail.InvoiceActualBillICOpenResp;
import com.suning.api.entity.govbus.EleinvoiceGetResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * 电子发票明细响应对象
 *
 * @author cdrcool
 */
@Slf4j
@Data
public class InvoiceElectronicDetailResponse {
    /**
     * 订单号
     */
    private String businessId;

    /**
     * 发票号
     */
    private String ivcNo;

    /**
     * 发票代码
     */
    private String ivcCode;

    /**
     * 开票日期
     */
    private String invoiceTime;

    /**
     * 发票类型
     */
    private Integer ivcType;

    /**
     * 发票类别（1-正数，代表蓝票；2-负数，代表红票）
     */
    private Integer iflag;

    /**
     * 开票内容
     */
    private Integer ivcContentType;

    /**
     * 开票内容名称
     */
    private String ivcContentName;

    /**
     * 发票抬头
     */
    private String ivcTitle;

    /**
     * 发票金额（裸价）
     */
    private BigDecimal invoiceNakedAmount;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 税额
     */
    private BigDecimal totalTaxPrice;

    /**
     * 开票金额，订单在本发票中的总金额
     */
    private BigDecimal totalPrice;

    /**
     * 红票对应蓝票序列号
     */
    private String blueIsn;

    /**
     * 电子票下载地址
     */
    private String fileUrl;

    /**
     * 发票ISN
     */
    private String isn;

    /**
     * 作废标识(1,有效；0.作废)
     * --不作为判断是否是冲红的标志
     */
    private Integer yn;

    /**
     * 备注
     */
    private String remark;

    public static InvoiceElectronicDetailResponse buildFrom(InvoiceActualBillICOpenResp resp) {
        InvoiceElectronicDetailResponse response = new InvoiceElectronicDetailResponse();
        response.setBusinessId(resp.getBusinessId());
        response.setIvcNo(resp.getIvcNo());
        response.setIvcCode(resp.getIvcCode());
        response.setInvoiceTime(resp.getInvoiceTime());
        response.setIvcType(resp.getIvcType());
        response.setIflag(resp.getIflag());
        response.setIvcContentType(resp.getIvcContentType());
        response.setIvcContentName(resp.getIvcContentName());
        response.setIvcTitle(resp.getIvcTitle());
        response.setTaxRate(resp.getTaxRate());
        response.setTotalTaxPrice(resp.getTotalTaxPrice());
        response.setTotalPrice(resp.getTotalPrice());
        response.setBlueIsn(resp.getBlueIsn());
        response.setFileUrl(resp.getFileUrl());
        response.setIsn(resp.getIsn());
        response.setYn(resp.getYn());
        response.setRemark(resp.getRemark());

        return response;
    }

    public static InvoiceElectronicDetailResponse buildFrom(EleinvoiceGetResponse.EleInvoices resp) {
        InvoiceElectronicDetailResponse response = new InvoiceElectronicDetailResponse();
        response.setBusinessId(resp.getOrderItemId());
        response.setIvcNo(resp.getInvoiceId());
        response.setIvcCode(resp.getInvoiceCode());
        response.setInvoiceTime(resp.getInvoiceDate());
        response.setIvcType(Integer.parseInt(resp.getInvoiceType()));
        response.setInvoiceNakedAmount(new BigDecimal(resp.getInvoiceNakedAmount()));
        response.setTotalPrice(new BigDecimal(resp.getInvoiceAmount()));
        response.setFileUrl(resp.getInvoiceBlueUrl());

        return response;
    }
}

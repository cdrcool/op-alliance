package com.op.framework.boot.sdk.client.response;

import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceOutline.InvoiceOutlineOpenResp;
import com.suning.api.entity.govbus.GetmarkidinvoiceQueryResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 发票概要响应对象
 *
 * @author cdrcool
 */
@Slf4j
@Data
public class InvoiceOutlineResponse {
    /**
     * 发票号码
     */
    private String invoiceId;

    /**
     * 发票代码
     */
    private String invoiceCode;

    /**
     * 发票日期
     */
    private Date invoiceDate;

    /**
     * 发票类型2：专票 3：电子发票
     */
    private Integer invoiceType;

    /**
     * 发票金额（裸价）（专票有值）
     */
    private BigDecimal invoiceNakedAmount;

    /**
     * 发票税率（专票有值）
     */
    private BigDecimal invoiceTaxRate;

    /**
     * 发票税额（专票有值）
     */
    private BigDecimal invoiceTaxAmount;

    /**
     * 价税合计
     */
    private BigDecimal invoiceAmount;

    /**
     * 电子票下载地址
     */
    private String fileUrl;

    /**
     * 开票状态
     */
    private Boolean success;

    public static InvoiceOutlineResponse buildFrom(InvoiceOutlineOpenResp resp) {
        InvoiceOutlineResponse response = new InvoiceOutlineResponse();
        response.setInvoiceId(resp.getInvoiceId());
        response.setInvoiceCode(resp.getInvoiceCode());
        response.setInvoiceDate(resp.getInvoiceDate());
        response.setInvoiceType(response.getInvoiceType());
        response.setInvoiceNakedAmount(resp.getInvoiceNakedAmount());
        response.setInvoiceTaxRate(resp.getInvoiceTaxRate());
        response.setInvoiceTaxAmount(resp.getInvoiceTaxAmount());
        response.setInvoiceAmount(resp.getInvoiceAmount());
        response.setFileUrl(resp.getFileUrl());
        response.setSuccess(resp.getSuccess());

        return response;
    }

    public static InvoiceOutlineResponse buildFrom(GetmarkidinvoiceQueryResponse.InvoiceInfo resp) {
        InvoiceOutlineResponse response = new InvoiceOutlineResponse();
        response.setInvoiceId(resp.getInvoiceid());
        response.setInvoiceCode(resp.getInvoiceCode());
        try {
            response.setInvoiceDate(new SimpleDateFormat("yyyy-MM-dd").parse(resp.getInvoiceDate()));
        } catch (ParseException e) {
            log.error("格式化发票日期异常", e);
        }
        response.setInvoiceType(response.getInvoiceType());
        response.setInvoiceNakedAmount(new BigDecimal(resp.getInvoiceNakedAmount()));
        response.setInvoiceAmount(new BigDecimal(resp.getInvoiceAmount()));

        return response;
    }


}

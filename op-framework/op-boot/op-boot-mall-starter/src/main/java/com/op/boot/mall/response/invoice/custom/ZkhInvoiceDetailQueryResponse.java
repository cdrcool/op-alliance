package com.op.boot.mall.response.invoice.custom;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 震坤行发票详情查询响应
 *
 * @author chengdr01
 */
@Data
public class ZkhInvoiceDetailQueryResponse {
    /**
     * 发票号码
     */
    private String invoiceId;

    /**
     * 发票代码
     */
    private String invoiceCode;

    /**
     * 发票状态
     */
    private Integer state;

    /**
     * 开票日期
     */
    private Date invoiceDate;

    /**
     * 发票抬头
     */
    private String title;

    /**
     * 纳税人识别号
     */
    private String enterpriseTaxpayer;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String tel;

    /**
     * 开户行
     */
    private String bank;

    /**
     * 账号
     */
    private String account;
    /**
     * 发票含税金额
     */
    private BigDecimal invoiceAmount;

    /**
     * 发票未税金额
     */
    private BigDecimal invoiceNakedAmount;

    /**
     * 发票税额
     */
    private BigDecimal invoiceTaxAmount;

    /***
     * 发票税率
     */
    private BigDecimal invoiceTaxRate;

    /**
     * 发票类型
     * <p>
     * 1-增值税普通发票；2-增值税专用发票；3-电子发票
     */
    private Integer invoiceType;

    /**
     * 电子发票 url
     */
    private String invoiceUrl;

    /**
     * 备注
     */
    private String remark;
}

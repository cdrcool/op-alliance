package com.op.mall.business;

import lombok.Data;

import java.util.Date;

/**
 * 订单/供货单的开票信息
 *
 * @author cdrcool
 */
@Data
public class SupplyOrderInvoiceInfo {
    /**
     * 电商类型
     */
    private String mallType;

    /**
     * 请求开票日期
     */
    private Date invoiceDate;

    /**
     * 发票类型
     */
    private Integer invoiceType;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 企业纳税人识别号
     */
    private String enterpriseTaxpayer;

    /**
     * 企业注册电话
     */
    private String enterpriseRegTel;

    /**
     * 企业注册地址
     */
    private String enterpriseRegAddress;

    /**
     * 企业开户行名称
     */
    private String enterpriseBankName;

    /**
     * 企业开户行账号
     */
    private String enterpriseBankAccount;

    /**
     * 收票人
     */
    private String billReceiver;

    /**
     * 收票人电话
     */
    private String billToContact;

    /**
     * 收票人地址-省编码
     */
    private Integer provinceCode;

    /**
     * 收票人地址-省名称
     */
    private String provinceName;

    /**
     * 收票人地址-市编码
     */
    private Integer cityCode;

    /**
     * 收票人地址-市名称
     */
    private String cityName;

    /**
     * 收票人地址-区编码
     */
    private Integer countyCode;

    /**
     * 收票人地址-区名称
     */
    private String countyName;

    /**
     * 收票人地址-街道编码
     */
    private Integer townCode;

    /**
     * 收票人地址-街道名称
     */
    private String townName;

    /**
     * 收票人地址-详细地址
     */
    private String address;

    /**
     * 第三方申请单号（申请开票之后有值）
     */
    private String markId;

    /**
     * 第三方子单号（申请开票之后有值）
     */
    private Long thirdSubOrderId;
}

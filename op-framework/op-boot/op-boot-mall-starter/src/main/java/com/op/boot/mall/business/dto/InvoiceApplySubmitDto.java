package com.op.boot.mall.business.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 发票申请提交 dto
 *
 * @author chengdr01
 */
@Data
public class InvoiceApplySubmitDto {
    /**
     * 电商类型
     */
    private String mallType;

    /**
     * 电商账号名
     */
    private String accountName;

    /**
     * 订单
     */
    private Long orderId;

    /**
     * 供货单
     */
    private List<Long> supplyIds;

    /**
     * 开票信息
     */
    private BillingInfo billingInfo;

    /**
     * 收票信息
     */
    private ReceivingInfo receivingInfo;

    /**
     * 开票信息
     */
    @Data
    public static class BillingInfo {

        private String markId;

        private Date invoiceDate;

        private Integer invoiceType;

        private String title;

        private String enterpriseTaxpayer;

        private String enterpriseRegPhone;

        private String enterpriseRegAddress;

        private String enterpriseBankName;

        private String enterpriseBankAccount;

        private Boolean isMerge;
    }

    /**
     * 收票信息
     */
    @Data
    public static class ReceivingInfo {

        private String billReceiver;

        private String billToContact;

        private Integer provinceId;

        private String provinceName;

        private Integer cityId;

        private String cityName;

        private Integer countyId;

        private String countyName;

        private Integer townId;

        private String townName;

        private String address;
    }
}

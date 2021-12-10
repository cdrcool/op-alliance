package com.op.boot.mall.request.invoice.custom;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 震坤行发票申请提交请求
 *
 * @author chengdr01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ZkhMallInvoiceApplySubmitRequest extends ZkhMallBaseRequest {
    /**
     * 申请单号
     */
    @NotNull
    private String markId;

    /**
     * 结算单号（一个结算单号可对应多个第三方申请单号；如果一对一可以是申请单号）
     */
    @NotNull
    private String settlementId;

    /**
     * 结算订单总数
     */
    private Integer settlementNum;

    /**
     * 结算单不含税总金额
     */
    private BigDecimal settlementNakedPrice;

    /**
     * 结算单总税额
     */
    private BigDecimal settlementTaxPrice;

    /**
     * 申请开票订单信息
     */
    @NotNull
    private List<SupplierOrder> supplierOrderList;

    /**
     * 发票类型
     * <p>
     * 1-增值税普通发票；2-增值税专用发票；3-电子发票
     */
    @NotNull
    private Integer invoiceType;

    /**
     * 期望开票时间，格式：yyyy-MM-dd HH:mm:ss
     */
    @NotNull
    private String invoiceDate;

    /**
     * 发票抬头
     */
    @NotNull
    private String title;

    /**
     * 纳税人识别号
     */
    @NotNull
    private String enterpriseTaxpayer;

    /**
     * 开户行
     */
    private String bank;

    /**
     * 开户行银行账号
     */
    private String account;

    /**
     * 开户行银行电话
     */
    private String tel;

    /**
     * 开户行银行地址
     */
    private String address;

    /**
     * 开票内容
     * <p>
     * 明细；办公用品；劳保用品；耗材
     */
    @NotNull
    private String content;

    /**
     * 含税总金额
     */
    @NotNull
    private BigDecimal invoicePrice;

    /**
     * 不含税总金额
     */
    @NotNull
    private BigDecimal invoiceNakedPrice;

    /**
     * 总税额
     */
    @NotNull
    private BigDecimal invoiceTaxPrice;

    /**
     * 订单总数
     */
    private Integer invoiceNum;

    /**
     * 收票单位/组织
     */
    @NotNull
    private String billToParty;

    /**
     * 收票人
     */
    @NotNull
    private String billToer;

    /**
     * 收票人联系方式
     */
    @NotNull
    private String billToContact;

    /**
     * 收票人省市区（专票必须）
     */
    private Region billToRegion;

    /**
     * 收票人地址（详细地址，务必包含省市区中文信息）
     */
    @NotNull
    private String billToAddress;

    /**
     * 合并markId开票（不传默认为合并开票）
     */
    private Boolean isMerge;

    /**
     * 备注
     */
    private String invoiceRemark;

    /**
     * 供货单信息
     */
    @Data
    public static class SupplierOrder {
        /**
         * 震坤⾏订单号
         */
        @NotNull
        private String orderId;

        /**
         * 客户订单号
         */
        private String thirdOrderId;

        /**
         * 订单结算⾦额
         */
        @NotNull
        private BigDecimal settleAmt;

        /**
         * 商品列表
         */
        private List<Sku> skuInfos;
    }

    /**
     * 商品信息
     */
    @Data
    public static class Sku {
        /**
         * sku ID
         */
        @NotNull
        private String skuId;

        /**
         * 商品数量
         */
        @NotNull
        private Integer skuNum;

        /**
         * 商品数量
         */
        @NotNull
        private BigDecimal sellPrice;
    }

    /**
     * 地址信息
     */
    @Data
    public static class Region {
        /**
         * 省
         */
        @NotNull
        private String province;

        /**
         * 市
         */
        @NotNull
        private String city;

        /**
         * 区
         */
        @NotNull
        private String county;

        /**
         * 乡/镇
         * <p>
         * 专票尽量传
         */
        private String town;
    }
}

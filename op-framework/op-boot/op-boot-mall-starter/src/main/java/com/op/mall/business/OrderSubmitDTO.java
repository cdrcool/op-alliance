package com.op.mall.business;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单提交 DTO
 *
 * @author cdrcool
 */
@Data
public class OrderSubmitDTO {
    /**
     * 纳税人识别号
     */
    private String taxpayerId;

    /**
     * 商品 SKU 列表
     */
    private List<SkuInfo> skuInfoList;

    /**
     * 订单收货信息
     */
    private ConsigneeInfo consigneeInfo;

    /**
     * 发票信息
     */
    private InvoiceInfo invoiceInfo;

    /**
     * 商品 SKU 信息
     */
    @Data
    public static class SkuInfo {
        /**
         * 电商类型
         */
        private String mallType;

        /**
         * 商品编号
         */
        private Long skuId;

        /**
         * 商品单价
         */
        private BigDecimal skuPrice;

        /**
         * 购买数量
         */
        private Integer num;
    }

    /**
     * 订单收货信息
     */
    private static class ConsigneeInfo {

    }

    /**
     * 发票信息
     */
    private static class InvoiceInfo {

    }
}

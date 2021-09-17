package com.op.mall.business.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单提交 DTO
 *
 * @author cdrcool
 */
@Data
public class OrderSubmitDTO implements Cloneable {
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
    public static class SkuInfo implements Cloneable {
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
    private static class ConsigneeInfo implements Cloneable {
        @Override
        public ConsigneeInfo clone() throws CloneNotSupportedException {
            return (ConsigneeInfo) super.clone();
        }
    }

    /**
     * 发票信息
     */
    private static class InvoiceInfo implements Cloneable {
        @Override
        public InvoiceInfo clone() throws CloneNotSupportedException {
            return (InvoiceInfo) super.clone();
        }
    }

    @Override
    public OrderSubmitDTO clone() throws CloneNotSupportedException {
        OrderSubmitDTO submitDTO = (OrderSubmitDTO) super.clone();
        if (submitDTO != null) {
            submitDTO.setConsigneeInfo(consigneeInfo.clone());
            submitDTO.setInvoiceInfo(invoiceInfo.clone());
        }
        return submitDTO;
    }

}

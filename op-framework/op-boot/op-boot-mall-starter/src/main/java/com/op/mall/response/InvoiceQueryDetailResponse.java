package com.op.mall.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 发票查询发票详情响应
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InvoiceQueryDetailResponse extends MallResponse {
    private List<InvoiceQueryDetailItemResponse> items;

    @Data
    public static class InvoiceQueryDetailItemResponse {
        /**
         * 第三方订单号
         */
        private String businessId;

        /**
         * 发票号码
         */
        private String invoiceId;

        /**
         * 发票代码
         */
        private String invoiceCode;

        /**
         * 发票类型
         */
        private Integer invoiceType;

        /**
         * 开票日期
         */
        private LocalDateTime invoiceDate;

        /**
         * 发票抬头
         */
        private String invoiceTitle;

        /**
         * 开票金额
         */
        private BigDecimal totalPrice;

        /**
         * 税额
         */
        private BigDecimal totalTaxPrice;

        /**
         * 税率
         */
        private BigDecimal taxRate;

        /**
         * 备注
         */
        private String remark;

        /**
         * 电子发票下载地址
         */
        private String fileUrl;
    }
}

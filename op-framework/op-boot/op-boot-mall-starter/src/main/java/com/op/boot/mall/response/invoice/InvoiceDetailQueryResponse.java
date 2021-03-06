package com.op.boot.mall.response.invoice;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 发票详情查询响应
 *
 * @author chengdr01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InvoiceDetailQueryResponse extends MallResponse {
    /**
     * 发票详情项列表
     */
    private List<InvoiceDetailItem> detailItems;

    /**
     * 发票详情项
     */
    @Data
    public static class InvoiceDetailItem {
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
         * 发票日期
         */
        private Date invoiceDate;

        /**
         * 发票抬头
         */
        private String invoiceTitle;

        /**
         * 开票金额（价税合计）
         */
        private BigDecimal invoiceAmount;

        /**
         * 开票金额（裸价）
         */
        private BigDecimal invoiceNakedAmount;

        /**
         * 税额
         */
        private BigDecimal invoiceTaxAmount;

        /**
         * 税率
         */
        private BigDecimal invoiceTaxRate;

        /**
         * 备注
         */
        private String remark;

        /**
         * 电子发票下载地址
         */
        private String fileUrl;

        /**
         * 是否成功
         */
        private Boolean success;
    }
}

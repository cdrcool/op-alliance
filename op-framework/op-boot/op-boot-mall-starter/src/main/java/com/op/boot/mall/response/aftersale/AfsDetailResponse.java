package com.op.boot.mall.response.aftersale;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AfsDetailResponse extends MallResponse {
    /**
     * 售后申请单号
     */
    private String thirdApplyId;

    /**
     * 原始订单号
     */
    private String originalOrderId;

    /**
     * 退款
     */
    private BigDecimal refundAmount;

    /**
     * 完成时间
     */
    private Date modifyDate;

    /**
     * 退款商品明细列表
     */
    private List<RefundWareOpenInfo> refundWareOpenInfoList;

    /**
     * 退款明细涉及的
     */
    private List<RefundPayOpenInfo> refundPayOpenInfoList;

    /**
     * 退款明细列表
     */
    private List<RefundDetailOpenInfo> refundDetailOpenInfoList;

    /**
     * 退款商品明细
     */
    @Data
    public static class RefundWareOpenInfo {
        private Long skuId;
        private String skuName;
        private Integer wareNumber;
    }

    /**
     * 退款明细涉及的
     */
    @Data
    public static class RefundPayOpenInfo {
        private String payId;
        private BigDecimal refundableAmount;
        private Integer payType;
        private Integer busPayType;
        private Integer payEnum;
        private Integer payFlag;
        private Long refundSourceId;
    }

    /**
     * 退款明细
     */
    @Data
    public static class RefundDetailOpenInfo {
        private String payId;
        private BigDecimal refundAmount;
        private Integer refundJdBankId;
        private Integer refundType;
        private Long refundSourceId;
        private String refundJdBankName;
    }

}

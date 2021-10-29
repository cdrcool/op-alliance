package com.op.boot.mall.response.aftersale;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AfsOutlineResponse extends MallResponse {
    private List<AfsOutlineItemResponse> afsOutlineItemResponseList;

    @Data
    public static class AfsOutlineItemResponse {
        /**
         * 第三方申请单号
         */
        private String thirdApplyId;

        /**
         * 京东订单号
         */
        private Long orderId;

        /**
         * 售后换新单对应的原始订单编号
         */
        private Long originalOrderId;

        /**
         * 售后环节。10申请中待审核；20审核完成待收货；30收货完成待处理；40处理完成（如需退款则等待退款）；50待用户确认，60用户确认完成，70取消
         */
        private Integer applyStep;

        /**
         * 服务单申请时间,2020-09-14 11:05:05
         */
        private String applyTime;

        /**
         * 申请途径
         */
        private boolean offline;

        /**
         * 是否可以取消
         */
        private boolean canCancel;

        /**
         * 是否可以确认
         */
        private boolean canConfirm;

        /**
         * 是否可以填写发运信息
         */
        private boolean canSendSku;

        /**
         * 申请中数量
         */
        private Integer applicationNum;

        /**
         * 原始申请数量
         */
        private Integer applyNum;

        /**
         * 待确认数量
         */
        private Integer confirmNum;

        /**
         * 是否完成
         */
        private Integer isComplete;

        /**
         * 当前取件方式,4上门取件，7客户送货，40客户发货
         */
        private Integer newPickWareType;

        /**
         * 未税价金额
         */
        private BigDecimal nakedPriceAmount;

        /**
         * 退款金额
         */
        private BigDecimal realRefundAmount;

        /**
         * 退款数量
         */
        private Integer refundNum;

        /**
         * 退货商品金额
         */
        private BigDecimal shouldRefundAmount;

        /**
         * 驳回或取消数量
         */
        private Integer wareCancelNum;

        /**
         * 完成数量
         */
        private Integer wareCompleteNum;

        /**
         * 服务中数量
         */
        private Integer wareServiceNum;
    }

}

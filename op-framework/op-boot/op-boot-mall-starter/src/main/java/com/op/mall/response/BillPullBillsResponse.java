package com.op.mall.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 账单拉取对账单响应
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BillPullBillsResponse extends MallResponse {
    private List<BillPullBillsItemResponse> items;

    /**
     * 对账单项响应
     */
    @Data
    public static class BillPullBillsItemResponse {
        /**
         * 订单号
         */
        private String orderNo;

        /**
         * 订单金额
         */
        private BigDecimal orderAmount;

        /**
         * 退款金额
         */
        private BigDecimal refundAmount;
    }
}

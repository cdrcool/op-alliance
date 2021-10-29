package com.op.boot.mall.response.order;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 查询配送信息请求
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderDeliveryResponse extends MallResponse {
    /**
     * 订单号
     */
    private String jdOrderId;

    /**
     * 订单配送信息响应列表
     */
    private List<OrderTrackDetailResponse> orderTrack;

    /**
     * 订单运单信息响应列表。当入参的 waybillCode=1 时，返回此字段
     */
    private List<OrderTrackBillCodeResponse> waybillCode;

    /**
     * 订单配送信息响应
     */
    @Data
    public static class OrderTrackDetailResponse {
        /**
         * 操作内容明细
         */
        private String content;

        /**
         * 操作时间，格式：yyyy-MM-dd hh:mm:ss
         */
        private Date msgTime;

        /**
         * 操作员名称
         */
        private String operator;
    }

    /**
     * 订单运单信息响应
     */
    @Data
    public static class OrderTrackBillCodeResponse {
        /**
         * 订单号
         */
        private Long orderId;

        /**
         * 父订单号。此字段为0 未拆单
         */
        private Long parentId;

        /**
         * 承运商。可以为“京东快递”或者商家自行录入的承运商名称
         */
        private String carrier;

        /**
         * 运单号
         */
        private String deliveryOrderId;
    }
}

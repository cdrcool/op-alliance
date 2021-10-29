package com.op.boot.mall.response.aftersale;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * @author cdrcool
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class AfsWaybillResponse extends MallResponse {
    private List<AfsWaybillItemResponse> afsWaybillItemResponseList;

    @Data
    public static class AfsWaybillItemResponse {
        /**
         * 物流操作信息列表
         */
        private List<OrderTrackOpenInfo> orderTrackOpenInfoList;

        /**
         * 物流单信息列表
         */
        private List<WaybillCodeOpenInfo> waybillCodeOpenInfoList;

        /**
         * 物流操作信息
         */
        @Data
        public static class OrderTrackOpenInfo {
            /**
             * 操作内容明细:您提交了订单，请等待系统确认
             */
            private String content;
            /**
             * 扩展字段
             */
            private Map<String, String> extMap;

            /**
             * 操作时间。日期格式为“yyyy-MM-dd hh:mm:ss”
             */
            private String msgTime;

            /**
             * 操作员名称
             */
            private String operatorName;
        }

        /**
         * 物流单信息
         */
        @Data
        public static class WaybillCodeOpenInfo {
            /**
             * 扩展字段
             */
            private Map<String, String> extMap;

            /**
             * 运单号:JD0024688106798
             */
            private String deliveryOrderId;

            /**
             * 承运商:京东快递
             */
            private String carrier;
        }
    }
}

package com.op.boot.mall.response.invoice;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 发票查询物流响应
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InvoiceQueryDeliveryResponse extends MallResponse {
    private List<InvoiceQueryDeliveryItemResponse> deliveryItems;

    /**
     * 物流项响应
     *
     * @author cdrcool
     */
    @Data
    public static class InvoiceQueryDeliveryItemResponse {
        /**
         * 配送公司
         */
        private String postCompany;

        /**
         * 运单号
         */
        private String deliveryId;

        /**
         * 配送时间
         */
        private Date postTime;

        /**
         * 邮寄id
         * <p>
         * 京东特有
         */
        private Long ivcPostId;

        /**
         * 配送状态（1-已邮寄；2-已取消）
         * <p>
         * 京东特有
         */
        private Integer state;

        /**
         * 发票物流详情列表
         */
        private List<InvoiceQueryDeliveryItemDetailResponse> details;
    }

    /**
     * 物流项详情响应
     *
     * @author cdrcool
     */
    @Data
    public static class InvoiceQueryDeliveryItemDetailResponse {
        /**
         * 操作主题
         */
        private String opeTitle;

        /**
         * 操作时间
         */
        private String opeTime;

        /**
         * 操作备注
         */
        private String opeRemark;

        /**
         * 操作用户
         * <p>
         * 京东特有
         */
        private String opeName;

        /**
         * 物流单号
         */
        private String waybillCode;
    }
}

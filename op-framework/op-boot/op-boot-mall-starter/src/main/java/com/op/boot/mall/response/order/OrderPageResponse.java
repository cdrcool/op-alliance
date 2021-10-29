package com.op.boot.mall.response.order;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单根据订单状态分页查询响应
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderPageResponse extends MallResponse {
    /**
     * 订单总数
     */
    private Integer total;

    /**
     * 总页码数
     */
    private Integer totalPage;

    /**
     * 当前页码
     */
    private Integer curPage;

    /**
     * 订单信息列表
     */
    private List<OrderItemResponse> orderItemResponseList;

    /**
     * 订单响应
     */
    @Data
    public static class OrderItemResponse {
        /**
         * 订单号
         */
        private Long orderId;

        /**
         * 订单状态：0-新建；1-妥投；2-拒收
         */
        private Integer state;

        /**
         * 是否挂起：0-未挂起；2-挂起
         */
        private Integer hangUpState;

        /**
         * 开票方式：1-随货开票；0-订单预借；2-集中开票
         */
        private Integer invoiceState;

        /**
         * 订单金额
         */
        private BigDecimal orderPrice;

        /**
         * 订单创建时间，格式：yyyy-MM-dd HH:mm:ss
         */
        private Date time;
    }

}

package com.op.boot.mall.response.order;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单查询状态响应
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderQueryStatusResponse extends MallResponse {
    /**
     * 订单确认状态
     */
    private Integer confirmState;

    /**
     * 订单取消状态
     */
    private Integer cancelOrderState;

    /**
     * 物流配送状态
     */
    private Integer deliveryState;

    /**
     * 订单状态
     */
    private Integer orderState;

    /**
     * 订单状态名称
     */
    private String orderStateName;
}

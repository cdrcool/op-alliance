package com.op.mall.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单提交响应
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderSubmitResponse extends MallResponse {
    /**
     * 第三方订单号
     */
    private Long thirdOrderId;
}

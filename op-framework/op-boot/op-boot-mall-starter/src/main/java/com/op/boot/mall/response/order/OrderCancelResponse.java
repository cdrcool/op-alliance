package com.op.boot.mall.response.order;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单取消响应
 *
 * @author cdrcool
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderCancelResponse extends MallResponse {
    /**
     * 是否成功
     */
    private Boolean result;
}

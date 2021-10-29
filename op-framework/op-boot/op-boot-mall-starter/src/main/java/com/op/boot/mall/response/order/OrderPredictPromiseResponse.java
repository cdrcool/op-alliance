package com.op.boot.mall.response.order;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单查询配送预计送达时间响应
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderPredictPromiseResponse extends MallResponse {
    /**
     * 预计送达时间说明
     */
    private String predictContent;
}

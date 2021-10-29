package com.op.boot.mall.request.aftersale.jingdong;

import lombok.Data;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
@Data
public class JdAfsQueryRequest {
    private String  thirdApplyId;
    private Long orderId;
}

package com.op.boot.mall.request.product.jingdong;

import lombok.Data;

import java.util.List;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */

@Data
public class JdSkuQueryRequest {

    private List<Long> skuIdList;

    private Long skuId;

    private String poolNum;

}

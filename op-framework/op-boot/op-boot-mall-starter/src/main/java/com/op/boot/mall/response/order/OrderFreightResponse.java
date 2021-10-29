package com.op.boot.mall.response.order;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 订单查询商品运费响应
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderFreightResponse extends MallResponse {
    /**
     * 总运费
     */
    private BigDecimal freight;

    /**
     * 基础运费
     */
    private BigDecimal baseFreight;

    /**
     * 偏远地区加收运费
     */
    private BigDecimal remoteRegionFreight;

    /**
     * 需收取偏远运费的sku
     */
    private String remoteSku;
}

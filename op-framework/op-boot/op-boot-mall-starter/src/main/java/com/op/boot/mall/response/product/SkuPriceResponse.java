package com.op.boot.mall.response.product;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SkuPriceResponse extends MallResponse {

    private List<SkuPriceItemResponse> skuPriceItemResponseList;

    /**
     * 商品售卖价
     */
    @Data
    public static class SkuPriceItemResponse {
        /**
         * skuId
         */
        private Long skuId;

        /**
         * 原始价。仅供参考
         */
        private BigDecimal skuPrice;

        /**
         * 销售价
         */
        private BigDecimal price;

        /**
         * 税率
         */
        private BigDecimal tax;

        /**
         * 税额
         */
        private BigDecimal taxPrice;

        /**
         * 未税价
         */
        private BigDecimal nakedPrice;
    }
}

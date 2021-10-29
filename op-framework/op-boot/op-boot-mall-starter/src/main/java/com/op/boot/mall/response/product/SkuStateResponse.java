package com.op.boot.mall.response.product;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SkuStateResponse extends MallResponse {

    private List<SkuStateItemResponse> skuStateItemResponseList;

    @Data
    public static class SkuStateItemResponse {
        /**
         * skuId
         */
        private Long skuId;

        /**
         * 1：上架，0：下架
         */
        private Integer state;
    }

}

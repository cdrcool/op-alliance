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
public class SkuCheckAreaLimitResponse extends MallResponse {

    private List<SkuCheckAreaLimitItemResponse> skuCheckAreaLimitItemResponseList;

    @Data
    public static class SkuCheckAreaLimitItemResponse {
        /**
         * skuId
         */
        private Long skuId;

        /**
         * true代表区域受限 false 区域不受限
         */
        private Boolean areaRestrict;

    }

}

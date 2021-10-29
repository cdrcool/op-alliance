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
public class SkuCategoryResponse extends MallResponse {
    private List<SkuCategoryItemResponse> skuCategoryItemResponseList;

    @Data
    public static class SkuCategoryItemResponse {
        /**
         * 分类ID
         */
        private String catId;

        /**
         * 父分类ID
         */
        private String parentId;

        /**
         * 分类名称
         */
        private String catName;

        /**
         * 1：一级分类；2：二级分类；3：三级分类；4：四级分类；
         */
        private Integer catClass;

        /**
         * 1：有效；0：无效；
         */
        private Integer state;
    }
}

package com.op.boot.mall.response.product;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SkuImageResponse extends MallResponse {
    private Map<Long, List<SkuImageItemResponse>> skuImageItemResponseMap;

    @Data
    public static class SkuImageItemResponse {
        /**
         * 编号
         */
        private Long id;

        /**
         * sku编号
         */
        private Long skuId;

        /**
         * 图片路径 如3.3商品详情页面返回的图片地址一致
         */
        private String path;

        /**
         * 创建日期
         */
        private Date created;

        /**
         * 更新时间
         */
        private Date modified;

        /**
         * 0:不可用;1:可用
         */
        private Integer yn;

        /**
         * 是否主图 1：是 0：否
         */
        private Integer isPrimary;

        /**
         * 排序
         */
        private Integer orderSort;

        /**
         * 位置
         */
        private Integer position;

        /**
         * 类型（0方图  1长图【服装】）
         */
        private Integer type;

        /**
         * 特征
         */
        private String features;
    }
}

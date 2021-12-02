package com.op.boot.recommendation.dto;

import lombok.Data;

/**
 * 推荐请求
 *
 * @author chengdr01
 */
@Data
public class RecommendRequest {
    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 分类 id
     */
    private Long categoryId;

    /**
     * 物品 id
     */
    private Long itemId;

    /**
     * 页面类型
     */
    private PageType pageType = PageType.ITEM_DETAIL;

    /**
     * 页下标
     */
    private Integer pageIndex = 0;

    /**
     * 页大小
     */
    private Integer pageSize = 10;

    /**
     * 页面类型
     */
    public enum PageType {
        /**
         * 物品详情页
         */
        ITEM_DETAIL(1),

        ;

        /**
         * 值
         */
        private Integer value;

        PageType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }
}

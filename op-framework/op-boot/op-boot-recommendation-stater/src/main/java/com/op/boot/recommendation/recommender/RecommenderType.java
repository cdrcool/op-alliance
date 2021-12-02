package com.op.boot.recommendation.recommender;

import lombok.Getter;

/**
 * 推荐类型
 *
 * @author chengdr01
 */
@Getter
public enum RecommenderType {
    /**
     * 基于用户的协同过滤
     */
    USER_BASED(1, "基于用户的协同过滤"),

    /**
     * 基于物品的协同过滤
     */
    ITEM_BASED(2, "基于物品的协同过滤"),

    /**
     * 基于物品的协同过滤（只考虑余弦相似度）
     */
    ITEM_BASED_COSINE(3, "基于物品的协同过滤（只考虑余弦相似度）"),

    /**
     * 基于热度过滤
     */
    HEAT_BASED(4, "基于热度过滤"),

    /**
     * 基于条形码推荐
     */
    BAR_CODE_BASED(5, "基于条形码推荐"),

    /**
     * 组合模式
     */
    COMPOSITE(99, ""),

    ;

    /**
     * 值
     */
    private final Integer value;

    /**
     * 描述
     */
    private final String desc;

    RecommenderType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}

package com.op.boot.recommendation.sorter;

import java.util.List;
import java.util.Map;

/**
 * 推荐结果排序策略接口
 *
 * @author chengdr01
 */
public interface RecommendsSorterStrategy {

    /**
     * 对原始推荐结果集进行排序
     *
     * @param originResults 原始推荐结果集
     * @return 排序后的推荐结果集
     */
    <T> List<T> sort(Map<Integer, List<T>> originResults);
}

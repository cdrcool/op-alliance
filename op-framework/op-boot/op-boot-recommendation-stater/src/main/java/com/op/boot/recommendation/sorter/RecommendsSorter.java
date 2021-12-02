package com.op.boot.recommendation.sorter;

import com.op.boot.recommendation.RecommendationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 推荐结果排序类
 *
 * @author chengdr01
 */
@Component
public class RecommendsSorter {
    private final RecommendationProperties properties;
    private final Map<String, RecommendsSorterStrategy> strategyMap;

    public RecommendsSorter(RecommendationProperties properties, Map<String, RecommendsSorterStrategy> strategyMap) {
        this.properties = properties;
        this.strategyMap = strategyMap;
    }

    /**
     * 对原始推荐结果集进行排序
     *
     * @param originResults 原始推荐结果集
     * @return 排序后的推荐结果集
     */
    public <T> List<T> sort(Map<Integer, List<T>> originResults) {
        String strategyName = properties.getSorterStrategy();
        RecommendsSorterStrategy strategy = strategyMap.getOrDefault(strategyName, strategyMap.get("ordinalRecommendsSorterStrategy"));
        return strategy.sort(originResults);
    }
}

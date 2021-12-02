package com.op.boot.recommendation.sorter;

import com.op.boot.recommendation.RecommendationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 按指定的 recommender s 的顺序排序
 *
 * @author chengdr01
 */
@Component
public class OrdinalRecommendsSorterStrategy implements RecommendsSorterStrategy {
    private final RecommendationProperties properties;

    public OrdinalRecommendsSorterStrategy(RecommendationProperties properties) {
        this.properties = properties;
    }

    @Override
    public <T> List<T> sort(Map<Integer, List<T>> originResults) {
        return Arrays.stream(properties.getAcceptOrdinal().split(","))
                .mapToInt(Integer::valueOf)
                .mapToObj(originResults::get)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}

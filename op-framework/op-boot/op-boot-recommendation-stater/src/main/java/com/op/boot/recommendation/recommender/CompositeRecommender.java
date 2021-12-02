package com.op.boot.recommendation.recommender;

import com.op.boot.recommendation.dto.RecommendRequest;
import com.op.boot.recommendation.dto.RecommendResponse;
import com.op.boot.recommendation.sorter.RecommendsSorter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import static com.op.boot.recommendation.RecommendationAutoConfig.RECOMMEND_EXECUTOR;

/**
 * 组合的物品推荐类
 *
 * @author chengdr01
 */
@Slf4j
@Component
public class CompositeRecommender implements Recommender {
    private final List<Recommender> recommenders;
    private final RecommendsSorter recommendsSorter;
    private final Executor executor;

    public CompositeRecommender(List<Recommender> recommenders,
                                RecommendsSorter recommendsSorter,
                                @Qualifier(RECOMMEND_EXECUTOR) Executor executor) {
        this.recommenders = recommenders;
        this.recommendsSorter = recommendsSorter;
        this.executor = executor;
    }

    @Override
    public RecommenderType recommenderType() {
        return RecommenderType.COMPOSITE;
    }

    @Override
    public void initialize() {
        recommenders.forEach(recommender -> executor.execute(recommender::initialize));
    }

    @Override
    public List<Long> recommend(RecommendRequest request) {
        Map<Integer, List<Long>> map = recommenders.stream()
                .collect(Collectors.toMap(o -> o.recommenderType().getValue(), o -> o.recommend(request)));
        List<Long> results = recommendsSorter.sort(map);
        return results.stream()
                .distinct()
                .skip((long) request.getPageIndex() * request.getPageSize())
                .limit(request.getPageSize())
                .collect(Collectors.toList());
    }

    @Override
    public List<RecommendResponse> recommendWithDesc(RecommendRequest request) {
        Map<Integer, List<RecommendResponse>> map = recommenders.stream()
                .collect(Collectors.toMap(o -> o.recommenderType().getValue(), o -> o.recommendWithDesc(request)));
        List<RecommendResponse> results = recommendsSorter.sort(map);
        List<RecommendResponse> distinctResults = results.stream()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(RecommendResponse::getItemId))), ArrayList::new));
        return distinctResults.stream()
                .skip((long) request.getPageIndex() * request.getPageSize())
                .limit(request.getPageSize())
                .collect(Collectors.toList());

    }
}

package com.op.boot.recommendation.recommender;

import com.op.boot.recommendation.dto.RecommendRequest;
import com.op.boot.recommendation.dto.RecommendResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 物品推荐接口
 *
 * @author chengdr01
 */
public interface Recommender {

    /**
     * 指定推荐类型
     *
     * @return 推荐类型
     */
    RecommenderType recommenderType();

    /**
     * 初始计算并存储所有用户的推荐物品
     * 同时会计算并存储所有物品的相关物品
     */
    void initialize();

    /**
     * 推荐物品集
     *
     * @param request 推荐请求
     * @return 推荐的物品 ids
     */
    List<Long> recommend(RecommendRequest request);

    /**
     * 推荐物品集
     *
     * @param request 推荐请求
     * @return 推荐的物品集
     */
    default List<RecommendResponse> recommendWithDesc(RecommendRequest request) {
        return recommend(request).stream()
                .map(v -> RecommendResponse.builder()
                        .itemId(v)
                        .recommenderType(recommenderType().getValue())
                        .recommenderDesc(recommenderType().getDesc())
                        .build())
                .collect(Collectors.toList());
    }
}

package com.op.boot.recommendation.store;

import com.op.boot.recommendation.entity.ItemSimilarities;
import com.op.boot.recommendation.entity.UserRecommends;

import java.util.List;
import java.util.Map;

/**
 * 推荐结果 Repository
 *
 * @author chengdr01
 */
public interface RecommendsRepository {

    /**
     * 保存用户推荐物品集
     *
     * @param recommends 推荐物品集
     */
    void saveUserRecommends(UserRecommends recommends);

    /**
     * 获取用户推荐物品集
     *
     * @param userId     用户 id
     * @param categoryId 分类 id
     * @return 各个 recommender 推荐的物品 ids（key: recommender type; value: item ids）
     */
    Map<Integer, List<Long>> getUserRecommends(long userId, long categoryId);

    /**
     * 获取给定 recommender type 下用户的推荐物品集
     *
     * @param userId          用户 id
     * @param categoryId      分类 id
     * @param recommenderType 推荐类型
     * @return 推荐的物品 ids
     */
    List<Long> getUserRecommends(long userId, long categoryId, int recommenderType);

    /**
     * 保存物品相似物品集
     *
     * @param similarities 相似物品集
     */
    void saveItemSimilarities(ItemSimilarities similarities);

    /**
     * 获取物品相似物品集
     *
     * @param itemId     物品 id
     * @param categoryId 分类 id
     * @return 各个 recommender 推荐的物品 ids（key: recommender type; value: item ids）
     */
    Map<Integer, List<Long>> getItemSimilarities(long itemId, long categoryId);

    /**
     * 获取给定 recommender type 下物品的相似物品集
     *
     * @param itemId          物品 id
     * @param categoryId      分类 id
     * @param recommenderType 推荐类型
     * @return 相似物品ids
     */
    List<Long> getItemSimilarities(long itemId, long categoryId, int recommenderType);
}

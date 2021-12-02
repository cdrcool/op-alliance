package com.op.boot.recommendation.recommender;

import com.op.boot.recommendation.RecommendationProperties;
import com.op.boot.recommendation.entity.ItemSimilarities;
import com.op.boot.recommendation.rescorer.CategoryItemIdsRescorer;
import com.op.boot.recommendation.store.RecommendsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Rescorer;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.common.LongPair;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import static com.op.boot.recommendation.RecommendationAutoConfig.RECOMMEND_EXECUTOR;

/**
 * 基于物品的协同过滤推荐
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class ItemBasedCfRecommender extends AbstractCfRecommender {

    public ItemBasedCfRecommender(ReloadFromJDBCDataModel dataModel,
                                  RecommendationProperties properties,
                                  RecommendsRepository recommendsRepository,
                                  @Qualifier(RECOMMEND_EXECUTOR) Executor executor) {
        super(properties, dataModel, recommendsRepository, executor);
    }

    @Override
    public RecommenderType recommenderType() {
        return RecommenderType.ITEM_BASED;
    }

    @Override
    public void initialize() {
        Boolean recEachCategory = super.getProperties().getRecEachCategory();

        // 获取各分类下的物品 ids
        Map<Long, List<Long>> categoryItemIdsMap = Objects.equals(recEachCategory, true) ?
                super.getDataModel().getCategoryItemsMap() : new HashMap<>(1);
        // -1 时，CategoryItemIdsRescorer 不进行过滤，用于计算不局限于某个分类下的推荐结果
        categoryItemIdsMap.put(-1L, null);

        // 创建物品相似性算法
        RecommendationProperties.SimilarityAlg similarityAlg = super.getProperties().getItemSimilarityAlg();
        ItemSimilarity itemSimilarity = similarityAlg.getItemSimilarity(super.getDataModel());

        // 创建用户-物品推荐类
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(super.getDataModel(), itemSimilarity);

        // 计算并存储每个物品的相似物品集
        super.getItemIds().forEachRemaining(itemId ->
                super.getExecutor().execute(() -> {
                    // 遍历所有分类
                    categoryItemIdsMap.forEach((categoryId, categoryItemIds) -> {
                        // 用于过滤非当前分类下的物品
                        Rescorer<LongPair> rescorer = recEachCategory && !Objects.equals(categoryId, -1L) ?
                                new CategoryItemIdsRescorer(categoryItemIds) : null;
                        try {
                            log.info("开始计算物品【{}】在分类【{}】下的相似物品...", itemId, categoryId);
                            List<RecommendedItem> recommendedItems = recommender.mostSimilarItems(itemId,
                                    super.getProperties().getCfRecommendNum(), rescorer);
                            log.info("计算得到物品【{}】在分类【{}】下的相似物品【{}】", itemId, categoryId, recommendedItems);

                            if (CollectionUtils.isNotEmpty(recommendedItems)) {
                                ItemSimilarities itemSimilarities = ItemSimilarities.builder()
                                        .itemId(itemId)
                                        .categoryId(categoryId)
                                        .itemIds(recommendedItems.stream()
                                                .map(String::valueOf)
                                                .collect(Collectors.joining(",")))
                                        .recommenderType(recommenderType().getValue())
                                        .build();
                                super.saveItemSimilarities(itemSimilarities);
                            }
                        } catch (TasteException e) {
                            log.error("计算物品【{}】的相似物品异常", itemId, e);
                        }
                    });
                })
        );

        // 遍历所有用户，计算并存储每个用户的推荐物品
        super.calcAndPersist(super.getUserIds(), recommender);
    }
}

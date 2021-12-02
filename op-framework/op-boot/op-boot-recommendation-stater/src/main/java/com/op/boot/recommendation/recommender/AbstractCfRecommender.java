package com.op.boot.recommendation.recommender;

import com.op.boot.recommendation.RecommendationProperties;
import com.op.boot.recommendation.dto.RecommendRequest;
import com.op.boot.recommendation.entity.UserRecommends;
import com.op.boot.recommendation.rescorer.CategoryItemIdsIDRescorer;
import com.op.boot.recommendation.store.RecommendsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveArrayIterator;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import static com.op.boot.recommendation.RecommendationAutoConfig.RECOMMEND_EXECUTOR;

/**
 * 协同过滤推荐抽象类
 *
 * @author chengdr01
 */
@Slf4j
public abstract class AbstractCfRecommender extends AbstractRecommender {

    public AbstractCfRecommender(RecommendationProperties properties,
                                 ReloadFromJDBCDataModel dataModel,
                                 RecommendsRepository recommendsRepository,
                                 @Qualifier(RECOMMEND_EXECUTOR) Executor executor) {
        super(properties, dataModel, recommendsRepository, executor);
    }

    @Override
    public List<Long> recommend(RecommendRequest request) {
        if (request.getCategoryId() == null) {
            request.setCategoryId(-1L);
        }

        return super.getRecommendsRepository().getUserRecommends(request.getUserId(),
                request.getCategoryId(), recommenderType().getValue());
    }

    protected void calcAndPersist(LongPrimitiveIterator userIds, Recommender recommender) {
        Boolean recEachCategory = super.getProperties().getRecEachCategory();

        // 获取各分类下的物品 ids
        Map<Long, List<Long>> categoryItemIdsMap = Objects.equals(recEachCategory, true) ?
                super.getDataModel().getCategoryItemsMap() : new HashMap<>(1);
        // -1 时，CategoryItemIdsRescorer 不进行过滤，用于计算不局限于某个分类下的推荐结果
        categoryItemIdsMap.put(-1L, null);

        // 遍历所有用户
        userIds.forEachRemaining(userId ->
                super.getExecutor().execute(() -> {
                    // 遍历所有分类
                    categoryItemIdsMap.forEach((categoryId, categoryItemIds) -> {
                        // 用于过滤非当前分类下的物品
                        IDRescorer idRescorer = recEachCategory && !Objects.equals(categoryId, -1L) ? new CategoryItemIdsIDRescorer(categoryItemIds) : null;

                        // 计算推荐结果
                        List<RecommendedItem> list;
                        try {
                            log.info("开始计算用户【{}】在分类【{}】下的推荐物品...", userId, categoryId);
                            list = recommender.recommend(userId, super.getProperties().getCfRecommendNum(), idRescorer);
                            log.info("计算得到用户【{}】在分类【{}】下的推荐物品【{}】", userId, categoryId, list);
                        } catch (TasteException e) {
                            log.error("计算用户【{}】在分类【{}】下的推荐物品异常", userId, categoryId);
                            return;
                        }

                        // 如果有推荐物品，就持久化
                        if (CollectionUtils.isNotEmpty(list)) {
                            List<Long> itemIds = list.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());

                            UserRecommends result = UserRecommends.builder()
                                    .userId(userId)
                                    .categoryId(categoryId)
                                    .itemIds(itemIds.stream().map(String::valueOf).collect(Collectors.joining(",")))
                                    .recommenderType(recommenderType().getValue())
                                    .build();
                            super.saveUserRecommends(result);
                        }
                    });
                })
        );
    }

    /**
     * 获取所有用户 ids
     *
     * @return 所有用户 ids
     */
    protected LongPrimitiveIterator getUserIds() {
        try {
            return super.getDataModel().getUserIDs();
        } catch (TasteException e) {
            log.error("获取所有用户 ids 异常", e);
            return new LongPrimitiveArrayIterator(new long[]{});
        }
    }

    /**
     * 获取所有物品 ids
     *
     * @return 所有物品 ids
     */
    protected LongPrimitiveIterator getItemIds() {
        try {
            return super.getDataModel().getItemIDs();
        } catch (TasteException e) {
            log.error("获取所有物品 ids 异常", e);
            return new LongPrimitiveArrayIterator(new long[]{});
        }
    }
}

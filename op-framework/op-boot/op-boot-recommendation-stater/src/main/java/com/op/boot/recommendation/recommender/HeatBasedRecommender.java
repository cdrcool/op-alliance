package com.op.boot.recommendation.recommender;

import com.op.boot.recommendation.RecommendationProperties;
import com.op.boot.recommendation.dto.RecommendRequest;
import com.op.boot.recommendation.entity.UserRecommends;
import com.op.boot.recommendation.store.RecommendsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import static com.op.boot.recommendation.RecommendationAutoConfig.RECOMMEND_EXECUTOR;

/**
 * 基于物品热度推荐
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class HeatBasedRecommender extends AbstractRecommender {

    public HeatBasedRecommender(RecommendationProperties properties,
                                ReloadFromJDBCDataModel dataModel,
                                RecommendsRepository recommendsRepository,
                                @Qualifier(RECOMMEND_EXECUTOR) Executor executor) {
        super(properties, dataModel, recommendsRepository, executor);
    }

    @Override
    public RecommenderType recommenderType() {
        return RecommenderType.HEAT_BASED;
    }

    @Override
    public void initialize() {
        // 获取各分类下热门的物品 ids
        Map<Long, List<String>> categoryHeatItems = super.getDataModel().
                getCategoryHeatItems(super.getProperties().getHeatRecommendNum(), super.getProperties().getHeatRecommendThreshold());

        categoryHeatItems.forEach((categoryId, itemIds) -> {
            UserRecommends result = UserRecommends.builder()
                    .userId(-1L)
                    .categoryId(categoryId)
                    .itemIds(itemIds.stream().map(String::valueOf).collect(Collectors.joining(",")))
                    .recommenderType(recommenderType().getValue())
                    .build();
            log.info("计算得到在分类【{}】下的热门物品【{}】", categoryId, itemIds);
            super.saveUserRecommends(result);
        });
    }

    @Override
    public List<Long> recommend(RecommendRequest request) {
        // TOTO 暂不支持所有分类的热度推荐
        if (request.getCategoryId() == null) {
            return new ArrayList<>();
        }

        return super.getRecommendsRepository().getUserRecommends(-1L, request.getCategoryId(),
                recommenderType().getValue());
    }
}

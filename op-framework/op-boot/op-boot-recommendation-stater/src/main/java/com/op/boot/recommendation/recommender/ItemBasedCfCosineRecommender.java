package com.op.boot.recommendation.recommender;

import com.op.boot.recommendation.RecommendationProperties;
import com.op.boot.recommendation.dto.RecommendRequest;
import com.op.boot.recommendation.store.RecommendsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import static com.op.boot.recommendation.RecommendationAutoConfig.RECOMMEND_EXECUTOR;

/**
 * 基于物品的协同过滤推荐
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class ItemBasedCfCosineRecommender extends AbstractRecommender {

    public ItemBasedCfCosineRecommender(ReloadFromJDBCDataModel dataModel,
                                        RecommendationProperties properties,
                                        RecommendsRepository recommendsRepository,
                                        @Qualifier(RECOMMEND_EXECUTOR) Executor executor) {
        super(properties, dataModel, recommendsRepository, executor);
    }

    @Override
    public RecommenderType recommenderType() {
        return RecommenderType.ITEM_BASED_COSINE;
    }

    /**
     * 由于在 {@link ItemBasedCfRecommender} 中执行过，所以这里不再处理
     */
    @Override
    public void initialize() {
    }

    @Override
    public List<Long> recommend(RecommendRequest request) {
        if (request.getItemId() == null) {
            return new ArrayList<>();
        }

        if (request.getCategoryId() == null) {
            request.setCategoryId(-1L);
        }

        return super.getRecommendsRepository().getItemSimilarities(request.getItemId(), request.getCategoryId(),
                recommenderType().getValue());
    }
}

package com.op.boot.recommendation.recommender;

import com.op.boot.recommendation.RecommendationProperties;
import com.op.boot.recommendation.dto.RecommendRequest;
import com.op.boot.recommendation.entity.ItemSimilarities;
import com.op.boot.recommendation.store.RecommendsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * 基于物品条形码推荐
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class BarCodeBaseRecommender extends AbstractRecommender {

    protected BarCodeBaseRecommender(RecommendationProperties properties,
                                     ReloadFromJDBCDataModel dataModel,
                                     RecommendsRepository recommendsRepository,
                                     @Qualifier(RECOMMEND_EXECUTOR) Executor executor) {
        super(properties, dataModel, recommendsRepository, executor);
    }

    @Override
    public RecommenderType recommenderType() {
        return RecommenderType.BAR_CODE_BASED;
    }

    @Override
    public void initialize() {
        // 获取各条形码相同的物品 ids
        Map<String, List<Long>> barCodeItemsMap = super.getDataModel().getBarCodeItemsMap();

        barCodeItemsMap.forEach((barCode, itemIds) ->
                itemIds.forEach(itemId -> {
                    ItemSimilarities itemSimilarities = ItemSimilarities.builder()
                            .itemId(itemId)
                            .categoryId(-1L)
                            .itemIds(itemIds.stream()
                                    .filter(v -> !v.equals(itemId))
                                    .map(String::valueOf)
                                    .collect(Collectors.joining(",")))
                            .recommenderType(recommenderType().getValue())
                            .build();
                    log.info("计算得到与物品【{}】具有相同69码的物品【{}】", itemId, itemSimilarities.getItemIds());

                    if (StringUtils.isNotBlank(itemSimilarities.getItemIds())) {
                        super.saveItemSimilarities(itemSimilarities);
                    }
                }));
    }

    @Override
    public List<Long> recommend(RecommendRequest request) {
        if (request.getItemId() == null) {
            return new ArrayList<>();
        }

        return super.getRecommendsRepository().getItemSimilarities(request.getItemId(), -1L,
                recommenderType().getValue());
    }
}

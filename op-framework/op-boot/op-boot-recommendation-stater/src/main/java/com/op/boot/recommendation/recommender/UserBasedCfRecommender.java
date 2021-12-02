package com.op.boot.recommendation.recommender;

import com.op.boot.recommendation.RecommendationProperties;
import com.op.boot.recommendation.store.RecommendsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.Executor;

import static com.op.boot.recommendation.RecommendationAutoConfig.RECOMMEND_EXECUTOR;

/**
 * 基于用户的协同过滤推荐
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class UserBasedCfRecommender extends AbstractCfRecommender {

    public UserBasedCfRecommender(ReloadFromJDBCDataModel dataModel,
                                  RecommendationProperties properties,
                                  RecommendsRepository recommendsRepository,
                                  @Qualifier(RECOMMEND_EXECUTOR) Executor executor) {
        super(properties, dataModel, recommendsRepository, executor);
    }

    @Override
    public RecommenderType recommenderType() {
        return RecommenderType.USER_BASED;
    }

    @Override
    public void initialize() {
        // 创建用户相似性算法
        RecommendationProperties.SimilarityAlg similarityAlg = super.getProperties().getUserSimilarityAlg();
        UserSimilarity userSimilarity = similarityAlg.getUserSimilarity(super.getDataModel());

        // 创建用户最近邻对象
        NearestNUserNeighborhood neighborhood;
        try {
            neighborhood = new NearestNUserNeighborhood(super.getProperties().getUserNeighborNum(), userSimilarity, super.getDataModel());
        } catch (TasteException e) {
            log.error("创建用户最近邻对象异常", e);
            return;
        }

        // 创建用户-物品推荐类
        GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(super.getDataModel(), neighborhood, userSimilarity);

        // 遍历所有用户，输出每个用户的最近邻及与其最近邻的相似度
        if (log.isDebugEnabled()) {
            super.getExecutor().execute(() ->
                    super.getUserIds().forEachRemaining(userId -> {
                        // 输出当前用户的最近邻
                        try {
                            log.debug("开始计算用户【{}】的最近邻...", userId);
                            long[] userNeighborhood = neighborhood.getUserNeighborhood(userId);
                            log.info("计算得到用户【{}】的最近邻【{}】", userId, userNeighborhood);

                            Arrays.stream(userNeighborhood).forEach(userId2 -> {
                                try {
                                    assert userSimilarity != null;
                                    double similarity = userSimilarity.userSimilarity(userId, userId2);
                                    log.info("计算得到用户【{}-{}】的相似度【{}】", userId, userId2, similarity);
                                } catch (TasteException e) {
                                    log.error("计算用户【{}-{}】的相似度异常", userId, userId2, e);
                                }
                            });
                        } catch (TasteException e) {
                            log.error("计算用户【{}】的最近邻异常", userId, e);
                        }
                    })
            );
        }

        // 遍历所有用户，计算并存储每个用户的推荐物品
        super.calcAndPersist(super.getUserIds(), recommender);
    }
}

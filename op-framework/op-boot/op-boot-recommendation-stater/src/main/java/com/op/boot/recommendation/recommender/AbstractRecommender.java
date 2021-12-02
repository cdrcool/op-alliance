package com.op.boot.recommendation.recommender;

import com.op.boot.recommendation.RecommendationProperties;
import com.op.boot.recommendation.datamodel.MysqlJdbcDataModel;
import com.op.boot.recommendation.entity.ItemSimilarities;
import com.op.boot.recommendation.entity.UserRecommends;
import com.op.boot.recommendation.store.RecommendsRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.concurrent.Executor;

import static com.op.boot.recommendation.RecommendationAutoConfig.RECOMMEND_EXECUTOR;

/**
 * 物品推荐抽象类
 *
 * @author chengdr01
 */
@Slf4j
@Getter
public abstract class AbstractRecommender implements Recommender {
    private final RecommendationProperties properties;
    private final ReloadFromJDBCDataModel dataModel;
    private final RecommendsRepository recommendsRepository;
    private final Executor executor;

    protected AbstractRecommender(RecommendationProperties properties,
                                  ReloadFromJDBCDataModel dataModel,
                                  RecommendsRepository recommendsRepository,
                                  @Qualifier(RECOMMEND_EXECUTOR) Executor executor) {
        this.properties = properties;
        this.dataModel = dataModel;
        this.recommendsRepository = recommendsRepository;
        this.executor = executor;
    }

    /**
     * 保存用户推荐物品集
     *
     * @param recommends 推荐物品集
     */
    protected void saveUserRecommends(UserRecommends recommends) {
        recommendsRepository.saveUserRecommends(recommends);
    }

    /**
     * 保存物品相似物品集
     *
     * @param similarities 相似物品集
     */
    protected void saveItemSimilarities(ItemSimilarities similarities) {
        recommendsRepository.saveItemSimilarities(similarities);
    }

    /**
     * 获取代理的 {@linkplain DataModel}
     *
     * @return {@link MysqlJdbcDataModel}
     */
    protected MysqlJdbcDataModel getDataModel() {
        return (MysqlJdbcDataModel) dataModel.getDelegate();
    }
}

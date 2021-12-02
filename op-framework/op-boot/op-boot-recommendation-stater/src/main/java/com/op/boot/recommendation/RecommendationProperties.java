package com.op.boot.recommendation;

import lombok.Data;
import lombok.SneakyThrows;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 推荐配置属性对象
 *
 * @author chengdr01
 */
@Data
@ConfigurationProperties(prefix = "recommend")
public class RecommendationProperties {
    /**
     * 数据库配置
     */
    private JdbcConfig jdbc = new JdbcConfig();

    /**
     * 线程池配置
     */
    private PoolConfig pool = new PoolConfig();

    /**
     * 推荐结果采纳顺序（逗号分隔）
     */
    private String acceptOrdinal = "5,3,2,1,4";

    /**
     * 推荐结果排序策略
     */
    private String sorterStrategy = "ordinalRecommendsSorterStrategy";

    /**
     * 协同过滤时，是否按分类获取推荐物品
     */
    private Boolean recEachCategory = true;

    /**
     * 基于协同过滤推荐物品的个数
     */
    private Integer cfRecommendNum = 30;

    /**
     * 用户最近邻个数
     */
    private Integer userNeighborNum = 30;

    /**
     * 用户相似性算法
     */
    private SimilarityAlg userSimilarityAlg = SimilarityAlg.uncenteredCosine;

    /**
     * 物品相似性算法
     */
    private SimilarityAlg itemSimilarityAlg = SimilarityAlg.uncenteredCosine;

    /**
     * 相似物品个数
     */
    private Integer similarItemsNum = 30;

    /**
     * 基于热度推荐物品的阈值
     */
    private Integer heatRecommendThreshold = 30;

    /**
     * 基于热度推荐物品的个数
     */
    private Integer heatRecommendNum = 30;

    /**
     * 数据库配置
     */
    @Data
    public static class JdbcConfig {
        /**
         * 表名
         */
        private String tableName = "taste_preferences";

        /**
         * 用户ID字段名
         */
        private String userIdColumn = "user_id";

        /**
         * 物品ID字段名
         */
        private String itemIdColumn = "item_id";

        /**
         * 偏好字段名
         */
        private String preferenceColumn = "preference";

        /**
         * 时间戳字段名
         */
        private String timeStampColumn = "timeStamp";
    }

    /**
     * 线程池配置
     */
    @Data
    public static class PoolConfig {
        /**
         * 最大线程数
         */
        private Integer maxPoolSize = Runtime.getRuntime().availableProcessors();

        /**
         * 核心线程数
         */
        private Integer corePoolSize = Runtime.getRuntime().availableProcessors();
    }

    /**
     * 相似性算法
     */
    public enum SimilarityAlg {
        /**
         * tanimoto 系数
         */
        tanimotoCoefficient,

        /**
         * 曼哈顿距离
         */
        cityBlock,

        /**
         * 对数似然相似度
         */
        logLikelihood,

        /**
         * 皮尔逊相似度
         */
        pearsonCorrelation,

        /**
         * 余弦相似度
         */
        uncenteredCosine,

        /**
         * 欧几里德相似度
         */
        euclideanDistance,

        /**
         * 斯皮尔曼相似度（仅用于 User-Based）
         */
        spearmanCorrelation,

        ;

        @SneakyThrows
        public UserSimilarity getUserSimilarity(DataModel dataModel) {
            switch (this) {
                case tanimotoCoefficient:
                    return new TanimotoCoefficientSimilarity(dataModel);
                case cityBlock:
                    return new CityBlockSimilarity(dataModel);
                case logLikelihood:
                    return new LogLikelihoodSimilarity(dataModel);
                case pearsonCorrelation:
                    return new PearsonCorrelationSimilarity(dataModel);
                case uncenteredCosine:
                    return new UncenteredCosineSimilarity(dataModel);
                case euclideanDistance:
                    return new EuclideanDistanceSimilarity(dataModel);
                case spearmanCorrelation:
                    return new SpearmanCorrelationSimilarity(dataModel);
                default:
                    return null;
            }
        }

        @SneakyThrows
        public ItemSimilarity getItemSimilarity(DataModel dataModel) {
            switch (this) {
                case tanimotoCoefficient:
                    return new TanimotoCoefficientSimilarity(dataModel);
                case cityBlock:
                    return new CityBlockSimilarity(dataModel);
                case logLikelihood:
                    return new LogLikelihoodSimilarity(dataModel);
                case pearsonCorrelation:
                    return new PearsonCorrelationSimilarity(dataModel);
                case uncenteredCosine:
                    return new UncenteredCosineSimilarity(dataModel);
                case euclideanDistance:
                    return new EuclideanDistanceSimilarity(dataModel);
                default:
                    return null;
            }
        }
    }
}

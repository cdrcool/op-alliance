package com.op.boot.recommendation;

import com.op.boot.recommendation.datamodel.MysqlJdbcDataModel;
import lombok.SneakyThrows;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.concurrent.Executor;

/**
 * 推荐配置类
 *
 * @author chengdr01
 */
@EnableConfigurationProperties(RecommendationProperties.class)
@Configuration
public class RecommendationAutoConfig {
    public static final String RECOMMEND_EXECUTOR = "recommendExecutor";
    private final DataSource dataSource;
    private final RecommendationProperties properties;

    public RecommendationAutoConfig(DataSource dataSource, RecommendationProperties properties) {
        this.dataSource = dataSource;
        this.properties = properties;
    }

    @SneakyThrows
    @Bean
    public ReloadFromJDBCDataModel dataModel() {
        RecommendationProperties.JdbcConfig jdbcConfig = properties.getJdbc();
        MysqlJdbcDataModel jdbcDataModel = new MysqlJdbcDataModel(
                dataSource,
                jdbcConfig.getTableName(),
                jdbcConfig.getUserIdColumn(),
                jdbcConfig.getItemIdColumn(),
                jdbcConfig.getPreferenceColumn(),
                jdbcConfig.getTimeStampColumn());
        return new ReloadFromJDBCDataModel(jdbcDataModel);
    }

    @Bean(RECOMMEND_EXECUTOR)
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("recommend-task-");
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() << 1 + 1);
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        return executor;
    }
}

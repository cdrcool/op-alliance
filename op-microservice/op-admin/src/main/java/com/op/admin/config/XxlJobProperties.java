package com.op.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * xxl-job 配置类
 *
 * @author cdrcool
 */
@ConfigurationProperties(prefix = "xxl.job")
@Data
public class XxlJobProperties {
    private Admin admin;
    private Executor executor;

    @Data
    public static class Admin {
        private String addresses;

    }

    @Data
    public static class Executor {
        private String accessToken;

        private String appName;

        private String logPath;

        private int logRetentionDays;
    }
}

package com.op.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 定时任务 Cron 表达式配置类
 *
 * @author cdrcool
 */
@RefreshScope
@Component
@ConfigurationProperties(prefix = "task.cron")
@Data
public class TaskCronProperties {
    /**
     * xx 定时任务 cron 表达式
     */
    private String xxTask;
}

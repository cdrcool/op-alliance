package com.op.samples.job.springscheduler.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 定时任务 Cron 表达式配置类
 *
 * @author cdrcool
 */
@Component
@ConfigurationProperties(prefix = "task.cron")
@Data
public class TaskCronProperties {
    private Map<String, String> cronMap = new HashMap<>();
}

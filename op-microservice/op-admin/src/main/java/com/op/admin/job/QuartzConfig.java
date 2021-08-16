package com.op.admin.job;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Quartz 任务调度配置类
 *
 * @author chengdr01
 */
@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail xxJobDetail() {
        return JobBuilder.newJob(XxJob.class).withIdentity("xxJob").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger(JobDetail xxJobDetail) {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("*/10 * * * * ?");
        return TriggerBuilder.newTrigger().forJob(xxJobDetail)
                .withIdentity("xxJob")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}

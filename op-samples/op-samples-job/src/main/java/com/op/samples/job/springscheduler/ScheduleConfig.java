package com.op.samples.job.springscheduler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 任务调度配置类
 *
 * @author cdrcool
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                9,
                9,
                200L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("schedule-task-pool-%d").build());
        taskRegistrar.setScheduler(threadPoolExecutor);
    }
}

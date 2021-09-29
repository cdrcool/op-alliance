package com.op.samples.job.springscheduler.core.config;

import com.op.samples.job.springscheduler.core.ScheduledRunnable;
import com.op.samples.job.springscheduler.core.definition.JobDefinition;
import com.op.samples.job.springscheduler.core.definition.JobDefinitionRepository;
import com.op.samples.job.springscheduler.typical.entity.TimingTask;
import com.op.samples.job.springscheduler.typical.service.TaskService;
import com.op.samples.job.utils.lock.DistributedLock;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.task.TaskSchedulingProperties;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.ScheduledMethodRunnable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 任务调度配置类
 *
 * @author cdrcool
 */
@Slf4j
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
    private final TaskSchedulingProperties taskSchedulingProperties;
    private final JobDefinitionRepository jobDefinitionRepository;
    private final DistributedLock distributedLock;
    private final TaskService taskService;

    public ScheduleConfig(TaskSchedulingProperties taskSchedulingProperties, JobDefinitionRepository jobDefinitionRepository,
                          DistributedLock distributedLock, TaskService taskService) {
        this.taskSchedulingProperties = taskSchedulingProperties;
        this.jobDefinitionRepository = jobDefinitionRepository;
        this.distributedLock = distributedLock;
        this.taskService = taskService;
    }

    @Override
    public void configureTasks(@NonNull ScheduledTaskRegistrar taskRegistrar) {
        /*// 使用以下方式设置 scheduler，要求 scheduler 不能暴露为 Spring Bean，不然会导致 @Async 的线程池不被初始化
        ThreadPoolTaskScheduler scheduler = taskSchedulerBuilder().build();
        // 使用以下方式需要显示调用 initialize 方法
        scheduler.initialize();
        taskRegistrar.setScheduler(scheduler);*/


        List<JobDefinition> definitions = jobDefinitionRepository.listALl();
        definitions.forEach(definition -> {
            Runnable runnable = new ScheduledRunnable(definition, taskService, distributedLock);
            TimingTask timingTask = taskService.findByJobName(definition.getJobName());

            taskRegistrar.addTriggerTask(runnable, triggerContext -> {
                CronTrigger trigger = new CronTrigger(timingTask.getCronExpression());
                return trigger.nextExecutionTime(triggerContext);
            });
        });
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return taskSchedulerBuilder().build();
    }

    @Bean
    public TaskSchedulerBuilder taskSchedulerBuilder() {
        return new TaskSchedulerBuilder()
                // 设置线程池线程的前缀
                .threadNamePrefix(taskSchedulingProperties.getThreadNamePrefix())
                // 设置线程池大小
                .poolSize(taskSchedulingProperties.getPool().getSize())
                // 执行器是否在关闭时等待计划的任务完成
                .awaitTermination(taskSchedulingProperties.getShutdown().isAwaitTermination())
                // 执行器等待剩余任务完成的最大时间。
                .awaitTerminationPeriod(taskSchedulingProperties.getShutdown().getAwaitTerminationPeriod())
                .customizers(taskScheduler -> {
                    // 设置线程池饱和策略：终止策略，抛出RejectedExecutionException
                    taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
                    // 设置任务取消执行策略：立即移除该任务
                    taskScheduler.setRemoveOnCancelPolicy(true);
                });
    }
}

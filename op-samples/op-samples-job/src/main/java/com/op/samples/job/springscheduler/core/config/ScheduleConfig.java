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
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
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


        List<TimingTask> tasks = taskService.findAll();
        tasks.forEach(task -> {
            try {
                String jobName = task.getJobName();
                JobDefinition jobDefinition = jobDefinitionRepository.get(jobName);
                if (jobDefinition == null) {
                    log.warn("未找到定时任务：{}", jobName);

                    // 可能是由于代码中移除了该任务，导致数据库中有记录，但应用程序中没有，此时需在数据库记录中标记该认为为已移除
                    task.setStatus(TimingTask.TaskStatus.REMOVED.getValue());
                    taskService.save(task);

                    return;
                }

                Runnable runnable = new ScheduledRunnable(jobDefinition, taskService, distributedLock);
                // 自定义 Trigger：使用 CronTrigger 计算任务的下一次执行时间
                taskRegistrar.addTriggerTask(runnable, triggerContext -> {
                    CronTrigger cronTrigger = new CronTrigger(task.getCronExpression());

                    // 更新定时任务的下次执行时间与状态
                    Date nextExecuteTime = cronTrigger.nextExecutionTime(new SimpleTriggerContext());
                    if (nextExecuteTime != null) {
                        task.setNextExecuteTime(nextExecuteTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                    }
                    task.setStatus(TimingTask.TaskStatus.WAITING.getValue());
                    taskService.save(task);

                    return cronTrigger.nextExecutionTime(triggerContext);
                });
            } catch (Exception e) {
                log.error("调度定时任务【{}】失败", task.getJobName(), e);
            }
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
                    // 设置任务取消执行策略：执行完该任务之后移除
                    taskScheduler.setRemoveOnCancelPolicy(false);
                });
    }
}

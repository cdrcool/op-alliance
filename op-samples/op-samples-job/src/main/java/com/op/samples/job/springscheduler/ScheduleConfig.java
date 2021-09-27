package com.op.samples.job.springscheduler;

import org.springframework.boot.autoconfigure.task.TaskSchedulingProperties;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 任务调度配置类
 *
 * @author cdrcool
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
    private final TaskSchedulingProperties taskSchedulingProperties;

    public ScheduleConfig(TaskSchedulingProperties taskSchedulingProperties) {
        this.taskSchedulingProperties = taskSchedulingProperties;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler scheduler = taskSchedulerBuilder().build();
        scheduler.initialize();

        taskRegistrar.setScheduler(scheduler);

        taskRegistrar.getCronTaskList().forEach(
                        cronTask -> {
                            // 手动使用线程池调度器调度这些任务，并保存每一个任务调度的scheduledFuture，在对Future的实现类scheduledFuture中可以实现对任务的取消
                            ScheduledFuture<?> scheduledFuture = scheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
                            // 拿到Runnable信息，提供给用户查看，同时用Runnable的MethodName作为每个任务的唯一标识
                            ScheduledMethodRunnable scheduledMethodRunnable = (ScheduledMethodRunnable) cronTask.getRunnable();

                            //添加到维护的map中
                            cronTaskScheduledFutureMap.put(cronTask, scheduledFuture);

                            taskList.add(new Task(ATOMIC_INTEGER.getAndIncrement(), scheduledMethodRunnable.getMethod().toGenericString(), cronTask.getExpression(), Task.State.WRITTING_NEXT, cronTask.getTrigger().nextExecutionTime(new SimpleTriggerContext())));

                            //设置spring托管的任务列表为空
                            taskRegistrar.setCronTasksList(null);
                        }
                );
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

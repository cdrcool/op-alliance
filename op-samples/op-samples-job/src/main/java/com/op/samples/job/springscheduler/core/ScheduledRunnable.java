package com.op.samples.job.springscheduler.core;

import com.op.samples.job.springscheduler.core.definition.JobDefinition;
import com.op.samples.job.springscheduler.typical.entity.TimingTask;
import com.op.samples.job.springscheduler.typical.service.TaskService;
import com.op.samples.job.utils.lock.DistributedLock;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 任务调度 runnable
 *
 * @author chengdr01
 */
@Slf4j
public class ScheduledRunnable implements Runnable {
    private final JobDefinition jobDefinition;
    private final TaskService taskService;
    private final DistributedLock distributedLock;

    public ScheduledRunnable(JobDefinition jobDefinition, TaskService taskService, DistributedLock distributedLock) {
        this.jobDefinition = jobDefinition;
        this.taskService = taskService;
        this.distributedLock = distributedLock;
    }

    @Override
    public void run() {
        String jobName = jobDefinition.getJobName();

        TimingTask task = taskService.findByJobName(jobName);
        task.setStatus(TimingTask.TaskStatus.RUNNING.getValue());
        LocalDateTime now = LocalDateTime.now();
        if (task.getLastExecuteTime() == null) {
            task.setFirstExecuteTime(now);
        }
        task.setLastExecuteTime(now);
        task.setExecuteCount(Optional.ofNullable(task.getExecuteCount()).orElse(0) + 1);
        taskService.save(task);

        boolean success = distributedLock.lock(jobName);
        if (success) {
            log.info("任务【{}】开始执行", jobName);
            jobDefinition.getConsumer().accept(null);
            log.info("任务【{}】执行结束", jobName);
            distributedLock.releaseLock(jobName);
        } else {
            log.warn("获取分布式锁失败，任务【{}】此次不执行", jobName);
        }

        task.setStatus(TimingTask.TaskStatus.WAITING.getValue());
        taskService.save(task);
    }
}

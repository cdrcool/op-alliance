package com.op.samples.job.springscheduler.core;

import com.op.samples.job.springscheduler.core.definition.JobDefinition;
import com.op.samples.job.springscheduler.typical.entity.TimingTask;
import com.op.samples.job.springscheduler.typical.service.TaskService;
import com.op.samples.job.utils.lock.DistributedLock;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * 任务调度 runnable
 *
 * @author cdrcool
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

        TimingTask timingTask = taskService.findByJobName(jobName);
        if (Objects.equals(timingTask.getStatus(), TimingTask.TaskStatus.PAUSED.getValue())) {
            log.info("任务【{}】已暂停，直接返回", jobName);
            return;
        }

        timingTask.setStatus(TimingTask.TaskStatus.RUNNING.getValue());
        LocalDateTime now = LocalDateTime.now();
        if (timingTask.getLastExecuteTime() == null) {
            timingTask.setFirstExecuteTime(now);
        }
        timingTask.setLastExecuteTime(now);
        timingTask.setExecuteCount(Optional.ofNullable(timingTask.getExecuteCount()).orElse(0) + 1);
        taskService.save(timingTask);

        boolean success = distributedLock.lock(jobName);
        if (success) {
            try{
                log.info("任务【{}】开始执行", jobName);
                jobDefinition.getConsumer().accept(null);
                log.info("任务【{}】执行结束", jobName);
                distributedLock.releaseLock(jobName);
            } finally {
                distributedLock.releaseLock(jobName);
            }
        } else {
            log.warn("获取分布式锁失败，任务【{}】此次不执行", jobName);
        }

        timingTask.setStatus(TimingTask.TaskStatus.WAITING.getValue());
        taskService.save(timingTask);
    }
}

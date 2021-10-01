package com.op.samples.job.springscheduler.core.manager;

import com.op.samples.job.springscheduler.core.ScheduledRunnable;
import com.op.samples.job.springscheduler.core.definition.JobDefinition;
import com.op.samples.job.springscheduler.core.definition.JobDefinitionRepository;
import com.op.samples.job.springscheduler.typical.entity.TimingTask;
import com.op.samples.job.springscheduler.typical.service.TaskService;
import com.op.samples.job.utils.lock.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

/**
 * 任务管理 Service Impl
 *
 * @author chengdr01
 */
@Slf4j
@Service
public class TaskManagerImpl implements TaskManager {
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private final JobDefinitionRepository jobDefinitionRepository;
    private final DistributedLock distributedLock;
    private final TaskService taskService;

    public TaskManagerImpl(ThreadPoolTaskScheduler threadPoolTaskScheduler,
                           JobDefinitionRepository jobDefinitionRepository,
                           DistributedLock distributedLock,
                           TaskService taskService) {
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.jobDefinitionRepository = jobDefinitionRepository;
        this.distributedLock = distributedLock;
        this.taskService = taskService;
    }

    @Override
    public void scheduleAllTasks() {
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

                CronTrigger cronTrigger = new CronTrigger(task.getCronExpression());
                ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(new ScheduledRunnable(jobDefinition, taskService, distributedLock), cronTrigger);
                jobDefinition.setScheduledFuture(scheduledFuture);

                // 更新定时任务的下次执行时间与状态
                Date nextExecuteTime = cronTrigger.nextExecutionTime(new SimpleTriggerContext());
                if (nextExecuteTime != null) {
                    task.setNextExecuteTime(nextExecuteTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                }
                task.setStatus(TimingTask.TaskStatus.WAITING.getValue());
                taskService.save(task);
            } catch (Exception e) {
                log.error("调度定时任务【{}】失败", task.getJobName(), e);
            }
        });
    }

    @Override
    public void createTask(String jobName, String cronExpression, boolean executeOnce) {
        JobDefinition jobDefinition = getJobDefinition(jobName);

        if (jobDefinition.getScheduledFuture() != null) {
            log.info("任务【{}】已存在，无需重复创建", jobName);
            return;
        }

        // 1. 调度任务
        Runnable runnable = new ScheduledRunnable(jobDefinition, taskService, distributedLock);
        CronTrigger cronTrigger = new CronTrigger(cronExpression);
        ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(runnable, cronTrigger);

        // 2. 更新任务的 scheduledFuture（取消任务时会使用到 scheduledFuture）
        jobDefinition.setScheduledFuture(scheduledFuture);

        // 3. 保存任务
        TimingTask task = new TimingTask();
        task.setJobName(jobName);
        task.setCronExpression(cronExpression);
        Date nextExecuteTime = cronTrigger.nextExecutionTime(new SimpleTriggerContext());
        if (nextExecuteTime != null) {
            task.setNextExecuteTime(nextExecuteTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        task.setStatus(TimingTask.TaskStatus.WAITING.getValue());
        taskService.save(task);

        // 4. 如果 executeOnce 为 true，则立即执行一次任务
        if (executeOnce) {
            threadPoolTaskScheduler.execute(runnable);
        }
    }

    @Override
    public void updateTask(String jobName, String cronExpression, boolean interruptIfRunning, boolean executeOnce) {
        JobDefinition jobDefinition = getJobDefinition(jobName);

        // 1. 取消之前的任务
        pauseTask(jobName, interruptIfRunning);

        // 2. 调度新任务
        Runnable runnable = new ScheduledRunnable(jobDefinition, taskService, distributedLock);
        CronTrigger cronTrigger = new CronTrigger(cronExpression);
        ScheduledFuture<?> curScheduledFuture = threadPoolTaskScheduler.schedule(runnable, cronTrigger);

        // 3. 更新任务的 scheduledFuture
        jobDefinition.setScheduledFuture(curScheduledFuture);

        // 4. 更新任务
        TimingTask task = taskService.findByJobName(jobName);
        task.setCronExpression(cronExpression);
        Date nextExecuteTime = cronTrigger.nextExecutionTime(new SimpleTriggerContext());
        if (nextExecuteTime != null) {
            task.setNextExecuteTime(nextExecuteTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        task.setStatus(TimingTask.TaskStatus.WAITING.getValue());
        taskService.save(task);

        // 5. 如果 executeOnce 为 true，则立即执行一次任务
        if (executeOnce) {
            threadPoolTaskScheduler.execute(runnable);
        }
    }

    @Override
    public void pauseTask(String jobName, boolean interruptIfRunning) {
        JobDefinition jobDefinition = getJobDefinition(jobName);

        ScheduledFuture<?> scheduledFuture = jobDefinition.getScheduledFuture();
        if (scheduledFuture.isCancelled()) {
            log.info("任务【{}】已取消，无需再次取消", jobName);
            return;
        }

        scheduledFuture.cancel(interruptIfRunning);

        TimingTask task = taskService.findByJobName(jobName);
        task.setStatus(TimingTask.TaskStatus.PAUSED.getValue());
        taskService.save(task);
    }

    @Override
    public void resumeTask(String jobName, boolean executeOnce) {
        JobDefinition jobDefinition = getJobDefinition(jobName);

        ScheduledFuture<?> scheduledFuture = jobDefinition.getScheduledFuture();
        if (!scheduledFuture.isCancelled()) {
            log.info("任务【{}】正在运行，无需恢复", jobName);
            return;
        }

        TimingTask task = taskService.findByJobName(jobName);

        // 1. 调度任务
        Runnable runnable = new ScheduledRunnable(jobDefinition, taskService, distributedLock);
        CronTrigger cronTrigger = new CronTrigger(task.getCronExpression());
        ScheduledFuture<?> curScheduledFuture = threadPoolTaskScheduler.schedule(runnable, cronTrigger);

        // 2. 更新任务的 scheduledFuture
        jobDefinition.setScheduledFuture(curScheduledFuture);

        // 3. 更新任务
        Date nextExecuteTime = cronTrigger.nextExecutionTime(new SimpleTriggerContext());
        if (nextExecuteTime != null) {
            task.setNextExecuteTime(nextExecuteTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        task.setStatus(TimingTask.TaskStatus.WAITING.getValue());
        taskService.save(task);

        // 4. 如果 executeOnce 为 true，则立即执行一次任务
        if (executeOnce) {
            threadPoolTaskScheduler.execute(runnable);
        }
    }

    @Override
    public void triggerTask(String jobName) {
        JobDefinition jobDefinition = getJobDefinition(jobName);

        Runnable runnable = new ScheduledRunnable(jobDefinition, taskService, distributedLock);
        threadPoolTaskScheduler.execute(runnable);
    }

    @Override
    public void removeTask(String jobName, boolean interruptIfRunning) {
        // 1. 取消任务
        pauseTask(jobName, interruptIfRunning);

        // 2. 移除任务
        jobDefinitionRepository.remove(jobName);

        // 3. 删除任务
        taskService.deleteByJobName(jobName);
    }

    @Override
    public List<TimingTask> findAll() {
        return taskService.findAll();
    }

    /**
     * 获取定时任务
     *
     * @param jobName 任务名称
     * @return 定时任务
     */
    private JobDefinition getJobDefinition(String jobName) {
        return Optional.ofNullable(jobDefinitionRepository.get(jobName)).orElseThrow(() -> new RuntimeException("未找到定时任务：" + jobName));
    }
}

package com.op.samples.job.springscheduler.core.manager;

import com.op.samples.job.springscheduler.typical.entity.TimingTask;

import java.util.List;

/**
 * 定时任务管理接口
 *
 * @author cdrcool
 */
public interface TaskManager {

    /**
     * 调度所有任务
     */
    void scheduleAllTasks();

    /**
     * 创建定时任务
     *
     * @param jobName        任务名称
     * @param cronExpression 任务 cron 表达式
     * @param executeOnce    创建后是否立即执行一次
     */
    void createTask(String jobName, String cronExpression, boolean executeOnce);

    /**
     * 更新任务(更新 cron 表达式)
     *
     * @param jobName            任务名称
     * @param cronExpression     任务 cron 表达式
     * @param interruptIfRunning 是否中断运行中的任务
     * @param executeOnce        更新后是否立即执行一次
     */
    void updateTask(String jobName, String cronExpression, boolean interruptIfRunning, boolean executeOnce);

    /**
     * 暂停任务
     *
     * @param jobName            任务名称
     * @param interruptIfRunning 是否中断运行中的任务
     */
    void pauseTask(String jobName, boolean interruptIfRunning);

    /**
     * 恢复任务
     *
     * @param jobName     任务名称
     * @param executeOnce 恢复后是否立即执行一次
     */
    void resumeTask(String jobName, boolean executeOnce);

    /**
     * 触发一次任务
     *
     * @param jobName 任务名称
     */
    void triggerTask(String jobName);

    /**
     * 删除任务
     *
     * @param jobName            任务名称
     * @param interruptIfRunning 是否中断运行中的任务
     */
    void removeTask(String jobName, boolean interruptIfRunning);

    /**
     * 查找所有定时任务
     *
     * @return 定时任务列表
     */
    List<TimingTask> findAll();
}

package com.op.samples.job.springscheduler.typical.service;

import com.op.samples.job.springscheduler.typical.entity.TimingTask;

import java.util.List;

/**
 * 定时任务 service
 *
 * @author chengdr01
 */
public interface TaskService {

    /**
     * 保存定时任务
     *
     * @param timingTask 定时任务
     */
    void save(TimingTask timingTask);

    /**
     * 根据任务名称删除定时任务
     *
     * @param jobName 任务名称
     */
    void deleteByJobName(String jobName);

    /**
     * 根据任务名称查找定时任务
     *
     * @param jobName 任务名称
     * @return 定时任务
     */
    TimingTask findByJobName(String jobName);

    /**
     * 查找所有定时任务
     *
     * @return 定时任务列表
     */
    List<TimingTask> findAll();
}

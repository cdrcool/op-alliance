package com.op.samples.job.springscheduler.core.definition;

import java.util.List;

/**
 * 任务定义 repository
 *
 * @author chengdr01
 */
public interface JobDefinitionRepository {
    /**
     * 添加任务定义
     *
     * @param jobName       任务名称
     * @param jobDefinition 任务定义
     */
    void add(String jobName, JobDefinition jobDefinition);

    /**
     * 移除任务定义
     *
     * @param jobName 任务名称
     */
    void remove(String jobName);

    /**
     * 获取任务定义
     *
     * @param jobName 任务名称
     * @return jobDefinition 任务定义
     */
    JobDefinition get(String jobName);

    /**
     * 是否已存在任务定义
     *
     * @param jobName 任务名称
     * @return true or false
     */
    boolean exist(String jobName);

    /**
     * 返回所有任务定义
     * @return 任务定义列表
     */
    List<JobDefinition> listALl();
}

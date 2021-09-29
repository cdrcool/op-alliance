package com.op.samples.job.springscheduler.typical.service;

import com.op.samples.job.springscheduler.typical.entity.TimingTask;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 定时任务 service impl
 *
 * @author chengdr01
 */
@Service
public class TaskServiceImpl implements TaskService {
    private static final TimingTask TIMING_TASK = new TimingTask();
    static {
        TIMING_TASK.setJobName("example");
        TIMING_TASK.setCronExpression("*/10 * * * * ?");
    }

    @Override
    public List<TimingTask> findAll() {
        return Collections.singletonList(TIMING_TASK);
    }

    @Override
    public void save(TimingTask timingTask) {
        TIMING_TASK.setCronExpression(timingTask.getCronExpression());
    }

    @Override
    public void deleteByJobName(String jobName) {

    }

    @Override
    public TimingTask findByJobName(String jobName) {
        return TIMING_TASK;
    }
}

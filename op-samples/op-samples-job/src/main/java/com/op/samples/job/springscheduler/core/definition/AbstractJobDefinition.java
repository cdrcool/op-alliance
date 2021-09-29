package com.op.samples.job.springscheduler.core.definition;

import java.util.concurrent.ScheduledFuture;

/**
 * @author chengdr01
 */
public abstract class AbstractJobDefinition implements JobDefinition {
    /**
     * 任务名称
     */
    private final String jobName;

    /**
     * @see ScheduledFuture
     */
    private ScheduledFuture<?> scheduledFuture;

    protected AbstractJobDefinition(String jobName) {
        this.jobName = jobName;
    }

    @Override
    public String getJobName() {
        return this.jobName;
    }

    @Override
    public ScheduledFuture<?> getScheduledFuture() {
        return this.scheduledFuture;
    }

    @Override
    public void setScheduledFuture(ScheduledFuture<?> scheduledFuture) {
        this.scheduledFuture = scheduledFuture;
    }
}

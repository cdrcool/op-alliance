package com.op.samples.job.springscheduler.core.definition;

import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

/**
 * 任务定义接口
 *
 * @author chengdr01
 */
public interface JobDefinition {

    /**
     * 返回任务名称
     *
     * @return 任务名称
     */
    String getJobName();

    /**
     * 返回 consumer
     *
     * @return Consumer
     */
    Consumer<Void> getConsumer();

    /**
     * 返回 scheduledFuture
     *
     * @return ScheduledFuture
     */
    ScheduledFuture<?> getScheduledFuture();

    /**
     * 设置 scheduledFuture
     *
     * @param scheduledFuture ScheduledFuture<?>
     */
    void setScheduledFuture(ScheduledFuture<?> scheduledFuture);
}

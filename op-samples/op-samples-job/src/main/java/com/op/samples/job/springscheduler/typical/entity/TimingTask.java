package com.op.samples.job.springscheduler.typical.entity;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 定时任务
 *
 * @author cdrcool
 */
@Data
public class TimingTask {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务 cron 表达式
     */
    private String cronExpression;

    /**
     * 首次执行时间
     */
    private LocalDateTime firstExecuteTime;

    /**
     * 最近执行时间
     */
    private LocalDateTime lastExecuteTime;

    /**
     * 下次执行时间
     */
    private LocalDateTime nextExecuteTime;

    /**
     * 累计执行次数
     */
    private Integer executeCount;

    /**
     * 任务状态
     *
     * @see TaskStatus
     */
    private Integer status;

    /**
     * 任务状态
     */
    @Getter
    public enum TaskStatus {
        /**
         * 已移除
         */
        REMOVED(-1),

        /**
         * 已暂停
         */
        PAUSED(0),

        /**
         * 等待运行
         */
        WAITING(1),

        /**
         * 运行中
         */
        RUNNING(2);

        /**
         * 值
         */
        private final int value;

        TaskStatus(int value) {
            this.value = value;
        }
    }
}

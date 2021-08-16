package com.op.admin.job;

import com.op.admin.config.TaskCronProperties;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * xx 定时任务
 *
 * @author cdrcool
 */
//@Component
public class XxTask implements SchedulingConfigurer {
    private final TaskCronProperties properties;

    public XxTask(TaskCronProperties properties) {
        this.properties = properties;
    }

    public void scheduleCronTask() {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(this::scheduleCronTask, triggerContext -> {
            CronTrigger trigger = new CronTrigger(properties.getXxTask());
            return trigger.nextExecutionTime(triggerContext);
        });
    }
}

package com.op.samples.job.springscheduler.typical.controller;

import com.op.samples.job.springscheduler.core.manager.TaskManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务 controller
 *
 * @author chengdr01
 */
@RequestMapping("task")
@RestController
public class TaskController {
    private final TaskManager taskManager;

    public TaskController(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @PostMapping("createTask")
    public void createTask(String jobName, String cronExpression, boolean executeOnce) {
        taskManager.createTask(jobName, cronExpression, executeOnce);
    }

    @PostMapping("updateTask")
    public void updateTask(String jobName, String cronExpression, boolean interruptIfRunning, boolean executeOnce) {
        taskManager.updateTask(jobName, cronExpression, interruptIfRunning, executeOnce);
    }

    @PostMapping("pauseTask")
    public void pauseTask(String jobName, boolean interruptIfRunning) {
        taskManager.pauseTask(jobName, interruptIfRunning);
    }

    @PostMapping("resumeTask")
    public void resumeTask(String jobName, boolean executeOnce) {
        taskManager.resumeTask(jobName, executeOnce);
    }

    @PostMapping("triggerTask")
    public void triggerTask(String jobName) {
        taskManager.triggerTask(jobName);
    }

    @PostMapping("removeTask")
    public void removeTask(String jobName, boolean interruptIfRunning) {
        taskManager.removeTask(jobName, interruptIfRunning);
    }
}

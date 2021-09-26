package com.op.samples.job.java;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * 任务调度类
 *
 * @author cdrcool
 */
@Slf4j
public class TaskSchedule {
    public static void executeByTimer() {
        Timer timer = new Timer("time-task");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("do something");
            }
        }, 1, 1000);
    }

    public static void executeByScheduledThreadPoolExecutor() throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(10,
                new ThreadFactoryBuilder().setNameFormat("scheduled-task-pool-%d").build());
        scheduledExecutorService.schedule(() -> log.info("do something"), 1, TimeUnit.SECONDS);

        ScheduledFuture<String> future = scheduledExecutorService.schedule(() -> "success", 1, TimeUnit.SECONDS);
        log.info("result: {}", future.get());

        scheduledExecutorService.scheduleAtFixedRate(() -> log.info("do something else"), 1, 1, TimeUnit.SECONDS);

        scheduledExecutorService.scheduleWithFixedDelay(() -> log.info("do something else funny"), 1, 1, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        executeByTimer();
        executeByScheduledThreadPoolExecutor();
    }
}

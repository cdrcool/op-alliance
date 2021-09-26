package com.op.samples.job.springscheduler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 任务调度类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class TaskSchedule {

    @Scheduled(cron = "0 */1 * * * ?")
    public void executeTask1() {
        log.info("spring task1 begin");
    }

    @Scheduled(fixedRate = 2 * 1000)
    public void executeTask2() {
        log.info("spring task2 begin");
    }

    @Scheduled(fixedRate = 3 * 1000, initialDelay = 1000)
    public void executeTask3() {
        log.info("spring task3 begin");
    }
}

package com.op.samples.job.springscheduler.demo;


import com.op.samples.job.springscheduler.core.DynamicScheduled;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 任务样例
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class ExampleTask {
    private final XxComponent xxComponent;

    public ExampleTask(XxComponent xxComponent) {
        this.xxComponent = xxComponent;
    }

    @SneakyThrows
    @Scheduled(cron = "*/5 * * * * ?")
    public void executeTask1() {
        log.info("spring task1 begin");
        xxComponent.execute();
        Thread.sleep(3000);
        log.info("spring task1 end");
    }

    @DynamicScheduled("example")
    public void executeTask2() {
        log.info("spring task2 begin");
    }
//
//    @Scheduled(fixedRate = 3 * 1000, initialDelay = 1000)
//    public void executeTask3() {
//        log.info("spring task3 begin");
//    }
}

package com.op.samples.concurrent.task;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * 模拟任务1
 *
 * @author cdrcool
 */
@Slf4j
public class Task1 implements Callable<String> {

    @Override
    public String call() throws Exception {
        log.info("execute task-1 begin");

        // 模拟任务耗时
        Thread.sleep(5000);

        log.info("execute task-1 end");

        return "result 1";
    }
}

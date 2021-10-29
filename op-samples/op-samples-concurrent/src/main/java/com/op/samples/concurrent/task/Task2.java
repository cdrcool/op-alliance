package com.op.samples.concurrent.task;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * 模拟任务2
 *
 * @author cdrcool
 */
@Slf4j
public class Task2 implements Callable<String> {

    @Override
    public String call() throws Exception {
        log.info("execute task-2 begin");

        // 模拟任务耗时
        Thread.sleep(2000);

        log.info("execute task-2 end");

        return "result 2";
    }
}

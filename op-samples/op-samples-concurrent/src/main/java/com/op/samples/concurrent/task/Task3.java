package com.op.samples.concurrent.task;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * 模拟任务3
 *
 * @author chengdr01
 */
@Slf4j
public class Task3 implements Callable<String> {

    @Override
    public String call() throws Exception {
        log.info("execute task-3 begin");

        // 模拟任务耗时
        Thread.sleep(5000);

        log.info("execute task-3 end");

        return "result 3";
    }
}

package com.op.samples.asyncrequest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 示例 Service Impl
 *
 * @author chengdr01
 */
@Slf4j
@Service
public class ExampleServiceImpl implements ExampleService {

    @Override
    public void executeSync(long millis) throws InterruptedException {
        Thread.sleep(millis);
        log.info("do something: {}", millis);
    }

    @Async
    @Override
    public void executeAsync() {
        log.info("Execution thread name：{}", Thread.currentThread().getName());
    }
}

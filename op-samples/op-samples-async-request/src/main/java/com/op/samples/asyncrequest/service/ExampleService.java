package com.op.samples.asyncrequest.service;

/**
 * 示例 Service
 *
 * @author chengdr01
 */
public interface ExampleService {

    /**
     * 同步执行
     *
     * @param mills 模拟执行执行
     */
    void executeSync(long mills) throws InterruptedException;

    /**
     * 异步执行
     */
    void executeAsync();
}

package com.op.samples.concurrent;

import com.op.samples.concurrent.task.TaskExecutor;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.concurrent.*;

/**
 * 任务执行类的单元测试类
 */
public class TaskExecutorTest {
    private final TaskExecutor taskExecutor = new TaskExecutor();

    /***
     * 测试使用 {@link Thread#join()} 执行并发任务
     */
    @Test
    public void testExecuteByThreadJoin() throws InterruptedException, ExecutionException {
        taskExecutor.executeByThreadJoin();
    }

    /***
     * 测试使用 {@link ExecutorService#invokeAll(Collection)} ()} 执行并发任务
     */
    @Test
    public void executeByInvokeALl() throws InterruptedException {
        taskExecutor.executeByInvokeAll();
    }

    /***
     * 测试使用 {@link FutureTask} 执行并发任务
     */
    @Test
    public void executeByFutureTask() throws InterruptedException, ExecutionException {
        taskExecutor.executeByFutureTask();
    }

    /***
     * 测试使用 {@link CountDownLatch} 执行并发任务
     */
    @Test
    public void executeByCountDownLatch() throws ExecutionException, InterruptedException {
        taskExecutor.executeByCountDownLatch();
    }

    /***
     * 测试使用 {@link CyclicBarrier} 执行并发任务
     */
    @Test
    public void executeByCyclicBarrier() throws ExecutionException, InterruptedException, BrokenBarrierException {
        taskExecutor.executeByCyclicBarrier();
    }

    /***
     * 测试使用 {@link CompletionService} 执行并发任务
     */
    @Test
    public void executeByCompletionService() {
        taskExecutor.executeByCompletionService();
    }

    /***
     * 测试使用 {@link CompletableFuture} 执行并发任务
     */
    @Test
    public void executeByCompletableFuture() {
        taskExecutor.executeByCompletableFuture();
    }

    /***
     * 测试使用 parallel stream 执行并发任务
     */
    @Test
    public void executeByParallelStream() {
        taskExecutor.executeByParallelStream();
    }
}

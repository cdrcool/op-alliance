package com.op.samples.concurrent.task;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 任务执行类
 *
 * @author chengdr01
 */
@Slf4j
public class TaskExecutor {
    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            9,
            9,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());

    /**
     * 使用 {@link Thread#join()} 执行并发任务
     *
     * @throws InterruptedException 线程中断异常
     * @throws ExecutionException   任务结果获取异常
     */
    public void executeByThreadJoin() throws InterruptedException, ExecutionException {
        FutureTask<String> futureTask1 = new FutureTask<>(new Task1());
        FutureTask<String> futureTask2 = new FutureTask<>(new Task2());
        FutureTask<String> futureTask3 = new FutureTask<>(new Task3());

        Thread thread1 = new Thread(futureTask1);
        Thread thread2 = new Thread(futureTask2);
        Thread thread3 = new Thread(futureTask3);

        // 启动子任务
        thread1.start();
        thread2.start();
        thread3.start();

        // 主线程等待子线程执行完毕
        thread1.join();
        thread2.join();
        thread3.join();

        // 输出子任务的结果
        log.info("result of task-1: {}", futureTask1.get());
        log.info("result of task-2: {}", futureTask2.get());
        log.info("result of task-3: {}", futureTask3.get());
    }

    /**
     * 使用 {@link ExecutorService#invokeAll(Collection)} 执行并发任务
     *
     * @throws InterruptedException 线程中断异常
     */
    public void executeByInvokeAll() throws InterruptedException {
        List<Future<String>> results = threadPoolExecutor.invokeAll(Arrays.asList(new Task1(), new Task2(), new Task3()));

        // 输出子任务的结果集
        log.info("results: {}", results);
    }

    /**
     * 使用 {@link FutureTask} 执行并发任务
     *
     * @throws InterruptedException 线程中断异常
     * @throws ExecutionException   任务结果获取异常
     */
    public void executeByFutureTask() throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask1 = new FutureTask<>(new Task1());
        FutureTask<String> futureTask2 = new FutureTask<>(new Task2());
        FutureTask<String> futureTask3 = new FutureTask<>(new Task3());

        threadPoolExecutor.submit(futureTask1);
        threadPoolExecutor.submit(futureTask2);
        threadPoolExecutor.submit(futureTask3);

        // 输出子任务的结果
        log.info("result of task-1: {}", futureTask1.get());
        log.info("result of task-2: {}", futureTask2.get());
        log.info("result of task-3: {}", futureTask3.get());
    }

    /**
     * 使用 {@link CountDownLatch} 执行并发任务
     *
     * @throws InterruptedException 线程中断异常
     * @throws ExecutionException   任务结果获取异常
     */
    public void executeByCountDownLatch() throws ExecutionException, InterruptedException {
        // 创建闭锁：当 count 减为 0 时，主线程才会继续往下执行
        CountDownLatch countDownLatch = new CountDownLatch(3);

        FutureTask<String> futureTask1 = new FutureTask<String>(new Task1()) {
            @Override
            protected void done() {
                countDownLatch.countDown();
            }
        };
        FutureTask<String> futureTask2 = new FutureTask<String>(new Task2()) {
            @Override
            protected void done() {
                countDownLatch.countDown();
            }
        };
        FutureTask<String> futureTask3 = new FutureTask<String>(new Task3()) {
            @Override
            protected void done() {
                countDownLatch.countDown();
            }
        };

        threadPoolExecutor.submit(futureTask1);
        threadPoolExecutor.submit(futureTask2);
        threadPoolExecutor.submit(futureTask3);

        // 主线程等待，直到闭锁的 count 数减为 0
        countDownLatch.await();

        // 输出子任务的结果
        log.info("result of task-1: {}", futureTask1.get());
        log.info("result of task-2: {}", futureTask2.get());
        log.info("result of task-3: {}", futureTask3.get());
    }

    /**
     * 使用 {@link CyclicBarrier} 执行并发任务
     *
     * @throws InterruptedException 线程中断异常
     * @throws ExecutionException   任务结果获取异常
     */
    public void executeByCyclicBarrier() throws ExecutionException, InterruptedException, BrokenBarrierException {
        // 创建栅栏：当栅栏中指定数目的任务都执行完后，主线程才会继续往下执行
        CyclicBarrier barrier = new CyclicBarrier(4, () -> log.info("所有任务已执行结束"));

        FutureTask<String> futureTask1 = new FutureTask<String>(new Task1()) {
            @SneakyThrows
            @Override
            protected void done() {
                // 执行完成，等待屏障
                barrier.await();
            }
        };
        FutureTask<String> futureTask2 = new FutureTask<String>(new Task2()) {
            @SneakyThrows
            @Override
            protected void done() {
                // 执行完成，等待屏障
                barrier.await();
            }
        };
        FutureTask<String> futureTask3 = new FutureTask<String>(new Task3()) {
            @SneakyThrows
            @Override
            protected void done() {
                // 执行完成，等待屏障
                barrier.await();
            }
        };

        threadPoolExecutor.submit(futureTask1);
        threadPoolExecutor.submit(futureTask2);
        threadPoolExecutor.submit(futureTask3);

        // 主线程等待屏障
        barrier.await();

        // 输出子任务的结果
        log.info("result of task-1: {}", futureTask1.get());
        log.info("result of task-2: {}", futureTask2.get());
        log.info("result of task-3: {}", futureTask3.get());
    }

    /**
     * 使用 {@link CompletionService} 执行并发任务
     */
    public void executeByCompletionService() {
        CompletionService<String> completionService = new ExecutorCompletionService<>(threadPoolExecutor);

        completionService.submit(new Task1());
        completionService.submit(new Task2());
        completionService.submit(new Task3());

        // 获取任务结果
        IntStream.range(0, 3).forEach(index -> {
            try {
                // 按任务执行完毕的顺序，获取任务结果
                Future<String> future = completionService.take();
                log.info("获取任务结果：{}", future.get());
            } catch (InterruptedException | ExecutionException e) {
                log.error("获取任务结果异常", e);
            }
        });
    }

    /**
     * 使用 {@link CompletableFuture} 执行并发任务
     */
    public void executeByCompletableFuture() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                return new Task1().call();
            } catch (Exception e) {
                log.error("执行任务1异常", e);
                return null;
            }
        }, threadPoolExecutor);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                return new Task2().call();
            } catch (Exception e) {
                log.error("执行任务2异常", e);
                return null;
            }
        }, threadPoolExecutor);
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                return new Task3().call();
            } catch (Exception e) {
                log.error("执行任务3异常", e);
                return null;
            }
        }, threadPoolExecutor);

        List<String> results = Stream.of(future1, future2, future3)
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        System.out.println("所有任务已执行结束，任务结果：" + results);
    }

    /**
     * 使用 parallel stream 执行并发任务
     */
    public void executeByParallelStream() {
        List<String> results = new ArrayList<>();
        Stream.of(new Task1(), new Task2(), new Task3()).parallel().forEach(task -> {
            try {
                results.add(task.call());
            } catch (Exception e) {
                log.error("执行任务：{}异常", task.getClass().getSimpleName(), e);
            }
        });
        System.out.println("所有任务已执行结束，任务结果：" + results);
    }
}

package com.op.samples.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 异步请求 controller
 *
 * @author cdrcool
 */
@Slf4j
@RequestMapping(("asyncRequest"))
@RestController
public class AsyncRequestController {

    @GetMapping("callable")
    public Callable<String> callable() {
        log.info("Main thread name：{}", Thread.currentThread().getName());

        return () -> {
            log.info("Execution thread name：{}", Thread.currentThread().getName());
            return "Hello, World!";
        };
    }

    @GetMapping("asyncTask")
    public WebAsyncTask<String> asyncTask() {
        log.info("Main thread name：{}", Thread.currentThread().getName());

        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(3000, () -> {
            log.info("Execution thread name：{}", Thread.currentThread().getName());
            return "Hello, World!";
        });

        // 成功回调
        webAsyncTask.onCompletion(() -> System.out.println("Finish!"));

        // 超时回调
        webAsyncTask.onTimeout(() -> "Time out!");

        // 错误回调
        webAsyncTask.onError(() -> "Error!");

        return webAsyncTask;
    }

    @GetMapping("/deferred")
    public DeferredResult<String> deferredResult() {
        log.info("Main thread name：{}", Thread.currentThread().getName());


        DeferredResult<String> deferredResult = new DeferredResult<>(3000L);

        // 实际应用中，可以由消息队列、定时任务或其它事件触发
        CompletableFuture
                .supplyAsync(() -> {
                    log.info("Execution thread name：{}", Thread.currentThread().getName());
                    return "Hello, World!";
                })
                .whenCompleteAsync((result, throwable) -> {
                    // 重点：将异步结果赋值到 deferredResult 中
                    deferredResult.setResult(result);
                });

        // 成功回调
        deferredResult.onCompletion(() -> log.info("Finish!"));

        // 超时回调
        deferredResult.onTimeout( () -> {
            throw new RuntimeException("异步请求超时");
        });

        // 错误回调
        deferredResult.onError((e) -> log.error("Error!"));

        // 虽然这里有 return，但如果一直没有调用 setResult 设置值，线程就会一直 hold 在这里
        return deferredResult;
    }

    @GetMapping("/emitter")
    public ResponseBodyEmitter emitter() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        // 线程 1 输出
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                emitter.send("Hello, World!");
            } catch (IOException | InterruptedException e) {
                log.error("Error!", e);
            }
        });

        // 线程 2 输出
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                emitter.send("Hello, World again!");
            } catch (IOException | InterruptedException e) {
                log.error("Error!", e);
            }
        });

        // 线程 3 标记结束
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                emitter.complete();
            } catch (InterruptedException e) {
                log.error("Error!", e);
            }
        });

        // 一直阻塞，直到调用 emitter.complete()
        return emitter;
    }

    @GetMapping("/sseEmitter")
    public SseEmitter sseEmitter() {
        SseEmitter emitter = new SseEmitter();

        // 线程 1 输出
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                emitter.send("Hello, World!");
            } catch (IOException | InterruptedException e) {
                log.error("Error!", e);
            }
        });

        // 线程 2 输出
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                emitter.send("Hello, World again!");
            } catch (IOException | InterruptedException e) {
                log.error("Error!", e);
            }
        });

        // 线程 3 标记结束
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                emitter.complete();
            } catch (InterruptedException e) {
                log.error("Error!", e);
            }
        });

        // 一直阻塞，直到调用 emitter.complete()
        return emitter;
    }

    @GetMapping("/download")
    public StreamingResponseBody download() {
        log.info("Main thread name：{}", Thread.currentThread().getName());
        return outputStream -> {
            log.info("Execution thread name：{}", Thread.currentThread().getName());
            // write...
        };
    }
}

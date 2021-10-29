package com.op.boot.mall;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.op.boot.mall.exception.MallException;
import com.op.boot.mall.handler.MallRequestHandler;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.response.MallResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 电商请求执行类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class MallRequestExecutor {
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 执行电商请求
     *
     * @param mallRequest 电商请求
     * @param <T>         电商请求类型
     * @param <R>         请求响应类型
     * @return 请求响应
     */
    public <T extends MallRequest<?, R>, R extends MallResponse> R execute(T mallRequest) {
        log.info("准备指定电商请求，电商类型：{}，请求方法：{}， 请求参数：{}",
                mallRequest.getMallType().getName(), mallRequest.getRequestMethod(), serialize(mallRequest.getRequestObj()));

        // 1. 获取当前电商请求处理类
        MallRequestAction action = new MallRequestAction(mallRequest.getMallType(), mallRequest.getRequestMethod());
        MallRequestHandler<T, ?, R> handler = MallRequestHandlerRegistry.getHandler(action);
        if (handler == null) {
            throw new MallException("未注册的电商请求动作");
        }

        // 3. 执行电商请求并返回电商请求响应
        R mallResponse = handler.handle(mallRequest);
        mallResponse.setMallType(mallRequest.getMallType());
        mallResponse.setSuccess(true);

        log.info("执行电商请求成功，电商类型：{}，请求方法：{}， 请求参数：{}，请求响应：{}",
                mallRequest.getMallType().getName(), mallRequest.getRequestMethod(), mallRequest.getRequestObj(), serialize(mallResponse));
        return mallResponse;
    }

    /**
     * 并发执行电商请求
     *
     * @param executorService {@link ExecutorService}
     * @param mallRequests    电商请求列表
     * @param <T>             电商请求类型
     * @param <R>             请求响应类型
     * @return 请求响应列表
     */
    public <T extends MallRequest<?, R>, R extends MallResponse> List<R> executeConcurrent(ExecutorService executorService, List<T> mallRequests) {
        try {
            List<Callable<R>> callables = mallRequests.stream()
                    .map(mallRequest -> (Callable<R>) () -> execute(mallRequest)).collect(Collectors.toList());
            List<Future<R>> futures = executorService.invokeAll(callables);
            return IntStream.range(0, futures.size()).mapToObj(index -> {
                T mallRequest = mallRequests.get(index);
                Future<R> future = futures.get(index);
                try {
                    return future.get();
                } catch (InterruptedException e) {
                    String message = MessageFormat.format("线程中断异常，错误信息：{0}", e.getMessage());
                    log.error(message, e);

                    Class<R> responseClass = mallRequest.getResponseClass();
                    return new MallResponse.ErrorBuilder().errorMsg(message).build(responseClass);
                } catch (ExecutionException e) {
                    String message = MessageFormat.format("线程执行异常，错误信息：{0}", e.getMessage());
                    log.error(message, e);

                    Class<R> responseClass = mallRequest.getResponseClass();
                    return new MallResponse.ErrorBuilder().errorMsg(message).build(responseClass);
                }
            }).collect(Collectors.toList());
        } catch (InterruptedException e) {
            log.error("线程中断异常", e);
            return new ArrayList<>();
        }
    }

    /**
     * 序列化对象
     *
     * @param object 对象
     * @return 对象字符串表示
     */
    private String serialize(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.info("序列化对象：{}异常", object.getClass().getName());
            return "";
        }
    }
}

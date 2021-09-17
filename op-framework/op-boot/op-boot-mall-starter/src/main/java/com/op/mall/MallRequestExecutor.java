package com.op.mall;

import com.op.mall.business.exception.BusinessException;
import com.op.mall.handler.MallRequestHandler;
import com.op.mall.handler.MallRequestHandlerRegistry;
import com.op.mall.request.MallRequest;
import com.op.mall.request.MallRequestAction;
import com.op.mall.response.MallResponse;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 电商请求执行类
 *
 * @author chengdr01
 */
@Slf4j
public class MallRequestExecutor {
    private final ThreadPoolExecutor threadPoolExecutor;

    public MallRequestExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    /**
     * 执行电商请求
     *
     * @param mallRequest 电商请求
     * @param <T>         请求对象泛型
     * @param <R>         请求响应泛型
     * @return 请求响应
     */
    public <T extends MallRequest<R>, R extends MallResponse> R execute(T mallRequest) {
        // 1. 获取当前电商请求处理类
        MallRequestAction action = new MallRequestAction(mallRequest.getMallType(), mallRequest.getRequestMethod());
        MallRequestHandler handler = MallRequestHandlerRegistry.getHandler(action);
        if (handler == null) {
            throw new BusinessException("未注册的电商请求动作");
        }

        // 3. 执行电商请求
        R response = handler.handle(mallRequest);

        // 4. 返回电商请求响应
        return response;
    }

    /**
     * 并发执行电商请求
     *
     * @param mallRequests 电商请求列表
     * @param <T>         请求对象泛型
     * @param <R>         请求响应泛型
     * @return 请求响应列表
     */
    public <T extends MallRequest<R>, R extends MallResponse> List<R> executeConcurrent(List<T> mallRequests) {
        try {
            List<Callable<R>> callables = mallRequests.stream()
                    .map(mallRequest -> (Callable<R>) () -> execute(mallRequest)).collect(Collectors.toList());
            List<Future<R>> futures = threadPoolExecutor.invokeAll(callables);
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
}

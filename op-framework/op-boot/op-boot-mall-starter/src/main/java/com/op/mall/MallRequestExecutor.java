package com.op.mall;

import com.op.mall.business.BusinessException;
import com.op.mall.handler.MallRequestHandler;
import com.op.mall.handler.MallRequestHandlerRegistry;
import com.op.mall.request.MallRequest;
import com.op.mall.request.MallRequestAction;
import com.op.mall.response.MallResponse;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;
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
     * @param mallType           电商类型
     * @param requestSupplierMap 电商请求对象提供者 map
     * @param <T>                请求对象泛型
     * @param <R>                请求响应泛型
     * @return 请求响应
     */
    public <T extends MallRequest<R>, R extends MallResponse> R execute(
            String mallType, Map<String, Supplier<T>> requestSupplierMap) {
        // 1. 获取当前电商请求对象
        T request = requestSupplierMap.getOrDefault(mallType, () -> null).get();

        // 2. 获取当前电商请求处理类
        MallRequestAction action = new MallRequestAction(mallType, request.getRequestMethod());
        MallRequestHandler handler = MallRequestHandlerRegistry.getHandler(action);
        if (handler == null) {
            throw new BusinessException("未注册的电商请求动作");
        }

        // 3. 执行电商请求
        R response = handler.handle(request);

        // 4. 返回电商请求响应
        return response;
    }

    /**
     * 并发执行电商请求
     *
     * @param mallTypes          电商类型列表
     * @param requestSupplierMap 电商请求对象提供者 map
     * @param <T>                请求对象泛型
     * @param <R>                请求响应泛型
     * @return 请求响应列表
     */
    public <T extends MallRequest<R>, R extends MallResponse> List<R> executeConcurrent(
            List<String> mallTypes, Map<String, Supplier<T>> requestSupplierMap) {
        try {
            List<Callable<R>> callables = mallTypes.stream()
                    .map(mallType -> (Callable<R>) () -> execute(mallType, requestSupplierMap)).collect(Collectors.toList());
            List<Future<R>> futures = threadPoolExecutor.invokeAll(callables);
            return IntStream.range(0, futures.size()).mapToObj(index -> {
                String mallType = mallTypes.get(index);
                Future<R> future = futures.get(index);
                try {
                    return future.get();
                } catch (InterruptedException e) {
                    String message = MessageFormat.format("线程中断异常，错误信息：{0}", e.getMessage());
                    log.error(message, e);

                    Class<R> responseClass = requestSupplierMap.get(mallType).get().getResponseClass();
                    return new MallResponse.ErrorBuilder().errorMsg(message).build(responseClass);
                } catch (ExecutionException e) {
                    String message = MessageFormat.format("线程执行异常，错误信息：{0}", e.getMessage());
                    log.error(message, e);

                    Class<R> responseClass = requestSupplierMap.get(mallType).get().getResponseClass();
                    return new MallResponse.ErrorBuilder().errorMsg(message).build(responseClass);
                }
            }).collect(Collectors.toList());
        } catch (InterruptedException e) {
            log.error("线程中断异常", e);
            return new ArrayList<>();
        }
    }
}

package com.op.mall;

import com.op.mall.exception.MallException;
import com.op.mall.handler.MallRequestHandler;
import com.op.mall.request.MallRequest;
import com.op.mall.response.MallResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 默认的电商请求执行类
 *
 * @author chengdr01
 */
@Slf4j
public class DefaultMallRequestExecutor implements MallRequestExecutor {
    private final ThreadPoolExecutor threadPoolExecutor;
    private final List<MallRequestHandler> handlers;

    public DefaultMallRequestExecutor(ThreadPoolExecutor threadPoolExecutor, List<MallRequestHandler> handlers) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.handlers = handlers;
    }

    @Override
    public <T extends MallResponse> T execute(MallRequest<T> mallRequest) {
        Optional<MallRequestHandler> optional = handlers.stream()
                .filter(handler -> handler.supports(mallRequest.getMallType())).findFirst();
        MallRequestHandler handler = optional.orElseThrow(() -> new MallException("不支持的电商类型：" + mallRequest.getMallType()));
        T mallResponse = handler.handle(mallRequest);
        mallResponse.setMallType(mallRequest.getMallType());
        return mallResponse;
    }

    @Override
    public <T extends MallResponse> List<T> batchExecute(List<MallRequest<T>> mallRequests) {
        try {
            List<Callable<T>> callables = mallRequests.stream()
                    .map(mallRequest -> (Callable<T>) () -> execute(mallRequest)).collect(Collectors.toList());
            List<Future<T>> futures = threadPoolExecutor.invokeAll(callables);
            return IntStream.range(0, futures.size()).mapToObj(index -> {
                MallRequest<T> mallRequest = mallRequests.get(index);
                Future<T> future = futures.get(index);
                try {
                    return future.get();
                } catch (InterruptedException e) {
                    log.error("线程中断异常", e);
                    try {
                        T mallResponse = mallRequest.getResponseClass().newInstance();
                        mallResponse.setErrorMsg("线程中断异常");
                        return mallResponse;
                    } catch (InstantiationException | IllegalAccessException instantiationException) {
                        log.error("利用反射初始化对象异常", e);
                        return null;
                    }
                } catch (ExecutionException e) {
                    log.error("线程执行异常", e);
                    try {
                        T mallResponse = mallRequest.getResponseClass().newInstance();
                        mallResponse.setErrorMsg(e.getCause().getMessage());
                        return mallResponse;
                    } catch (InstantiationException | IllegalAccessException instantiationException) {
                        log.error("利用反射初始化对象异常", e);
                        return null;
                    }
                }
            }).collect(Collectors.toList());
        } catch (InterruptedException e) {
            log.error("线程中断异常", e);
            return new ArrayList<>();
        }
    }
}

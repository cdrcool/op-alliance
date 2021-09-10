package com.op.mall.experiment;

import com.op.mall.request.MallRequest;
import com.op.mall.response.MallResponse;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 电商请求执行类
 *
 * @author chengdr01
 */
public class MallRequestExecutor {
    private static final ConcurrentHashMap<String, MallRequestExecutorStrategy> STRATEGY_MAP = new ConcurrentHashMap<>();

    /**
     * 电商类型
     */
    private String mallType;

    public <T extends MallResponse> T handle(MallRequest<T> mallRequest) {
        MallRequestExecutorStrategy strategy = STRATEGY_MAP.get(mallType);
        return strategy.handle(mallRequest);
    }

    public void setMallType(String mallType) {
        this.mallType = mallType;
    }
}

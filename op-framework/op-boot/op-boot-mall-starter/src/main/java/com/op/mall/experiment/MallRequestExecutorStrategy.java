package com.op.mall.experiment;

import com.op.mall.request.MallRequest;
import com.op.mall.response.MallResponse;

/**
 * 电商请求执行策略接口
 *
 * @author chengdr01
 */
public interface MallRequestExecutorStrategy {

    /**
     * 执行电商请求
     *
     * @param mallRequest 电商请求对象
     * @param <T>         电商请求响应的类型
     * @return 电商请求响应
     */
    <T extends MallResponse> T handle(MallRequest<T> mallRequest);
}

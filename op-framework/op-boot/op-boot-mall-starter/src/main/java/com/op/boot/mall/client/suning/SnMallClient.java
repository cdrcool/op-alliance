package com.op.boot.mall.client.suning;

import com.suning.api.SuningResponse;

/**
 * 苏宁电商 Client
 *
 * @author cdrcool
 */
public interface SnMallClient {

    /**
     * 执行苏宁电商请求
     *
     * @param request 请求对象
     * @param <T>     请求响应泛型
     * @return 请求响应
     */
    <T extends SuningResponse> T execute(SnMallRequest<T> request);
}

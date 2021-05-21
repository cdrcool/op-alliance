package com.op.framework.boot.sdk.client.base;

import com.suning.api.SuningResponse;

/**
 * 苏宁 SDK Client
 *
 * @author cdrcool
 */
public interface SnSdkClient {

    /**
     * 执行请求
     *
     * @param snSdkRequest 请求对象
     * @param <T>          请求对象泛型类型
     * @return 响应对象
     */
    <T extends SuningResponse> T execute(SnSdkRequest<T> snSdkRequest);
}

package com.op.framework.boot.sdk.client.base;

import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.AbstractResponse;

/**
 * 京东 SDK Client
 *
 * @author cdrcool
 */
public interface JdSdkClient {

    /**
     * 执行请求
     *
     * @param jdRequest 请求对象
     * @param <T>       请求对象泛型类型
     * @return 响应对象
     */
    <T extends AbstractResponse> T execute(JdRequest<T> jdRequest);
}

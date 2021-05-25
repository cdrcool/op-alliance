package com.op.framework.boot.sdk.client.api.pro;

/**
 * SDK Client 接口
 *
 * @author cdrcool
 */
public interface SdkClient {

    /**
     * 执行请求
     *
     * @param sdkRequest 请求对象
     * @param <T>        响应对象泛型
     * @return 请求响应
     */
    <T extends BaseSdkResponse> T execute(SdkRequest<T> sdkRequest);
}

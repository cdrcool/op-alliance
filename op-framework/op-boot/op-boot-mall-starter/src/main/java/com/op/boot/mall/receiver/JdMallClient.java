package com.op.boot.mall.receiver;

import com.jd.open.api.sdk.response.AbstractResponse;

/**
 * 京东电商命令接收者
 *
 * @author cdrcool
 */
public interface JdMallClient {

    /**
     * 执行京东电商请求
     *
     * @param request 请求对象
     * @param <T>     请求响应泛型
     * @return 请求响应
     */
    <T extends AbstractResponse> T execute(JdMallRequest<T> request);
}

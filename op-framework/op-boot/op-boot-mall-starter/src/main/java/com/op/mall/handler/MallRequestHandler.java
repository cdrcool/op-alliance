package com.op.mall.handler;

import com.op.mall.request.MallRequest;
import com.op.mall.response.MallResponse;

/**
 * 电商请求处理类接口
 *
 * @author cdrcool
 */
public interface MallRequestHandler {

    /**
     * 处理电商请求
     *
     * @param mallRequest 电商请求对象
     * @return 电商请求响应
     */
    <T extends MallResponse> T handle(MallRequest<T> mallRequest);

    /**
     * 对象创建后要执行的动作
     */
    void postConstruct();

    /**
     * 对象销毁前要执行的动作
     */
    void preDestroy();
}

package com.op.mall.handler;

import com.op.mall.request.MallRequest;
import com.op.mall.response.MallResponse;

/**
 * 电商请求处理接口
 *
 * @author chengdr01
 */
public interface MallRequestHandler {

    /**
     * 执行电商请求
     *
     * @param request 电商请求对象
     * @param <T>         电商请求响应的类型
     * @return 电商请求响应
     */
    <T extends MallRequest<R>, R extends MallResponse> R handle(T request);
}

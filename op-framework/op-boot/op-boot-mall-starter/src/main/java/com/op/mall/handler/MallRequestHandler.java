package com.op.mall.handler;

import com.op.mall.request.MallRequest;
import com.op.mall.constans.MallType;
import com.op.mall.response.MallResponse;

/**
 * 电商请求处理接口
 *
 * @author chengdr01
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
     * 如果可以处理当前电商请求对象，就返回 true
     *
     * @param mallType 电商类型
     * @return true or false
     */
    boolean supports(MallType mallType);
}

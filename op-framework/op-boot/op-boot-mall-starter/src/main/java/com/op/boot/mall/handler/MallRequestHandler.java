package com.op.boot.mall.handler;

import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.MallResponse;
import org.springframework.beans.factory.InitializingBean;

/**
 * 电商请求处理接口
 *
 * @param <T> 电商请求类型
 * @param <P> 请求对象类型
 * @param <R> 请求响应类型
 * @author cdrcool
 */
public interface MallRequestHandler<T extends MallRequest<P, R>, P, R extends MallResponse> extends InitializingBean {

    /**
     * 执行电商请求
     *
     * @param request 电商请求对象
     * @return 电商请求响应
     */
    R handle(T request);
}

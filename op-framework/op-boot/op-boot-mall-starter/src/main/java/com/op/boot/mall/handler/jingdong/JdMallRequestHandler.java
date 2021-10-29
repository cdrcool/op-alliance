package com.op.boot.mall.handler.jingdong;

import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.handler.MallRequestHandler;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.MallResponse;

/**
 * 京东电商请求处理抽象类
 *
 * @param <T> 电商请求类型
 * @param <P> 请求对象类型
 * @param <R> 请求响应类型
 * @author cdrcool
 */
public abstract class JdMallRequestHandler<T extends MallRequest<P, R>, P, R extends MallResponse> implements MallRequestHandler<T, P, R> {
    /**
     * 京东电商 client
     */
    private final JdMallClient jdMallClient;

    public JdMallRequestHandler(JdMallClient jdMallClient) {
        this.jdMallClient = jdMallClient;
    }

    /**
     * 返回京东电商 client
     *
     * @return 京东电商 client
     */
    protected JdMallClient getJdMallClient() {
        return jdMallClient;
    }
}

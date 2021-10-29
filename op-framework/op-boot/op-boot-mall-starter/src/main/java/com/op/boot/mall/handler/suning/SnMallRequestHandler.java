package com.op.boot.mall.handler.suning;

import com.op.boot.mall.client.suning.SnMallClient;
import com.op.boot.mall.handler.MallRequestHandler;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.MallResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 苏宁电商请求处理抽象类
 *
 * @param <T> 电商请求类型
 * @param <P> 请求对象类型
 * @param <R> 请求响应类型
 * @author cdrcool
 */
@Slf4j
public abstract class SnMallRequestHandler<T extends MallRequest<P, R>, P, R extends MallResponse> implements MallRequestHandler<T, P, R> {

    private final SnMallClient snMallClient;

    public SnMallRequestHandler(SnMallClient snMallClient) {
        this.snMallClient = snMallClient;
    }

    /**
     * 返回苏宁电商 client
     *
     * @return 苏宁电商 client
     */
    protected SnMallClient getSnMallClient() {
        return snMallClient;
    }
}



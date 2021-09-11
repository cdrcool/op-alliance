package com.op.mall.request;

import com.op.mall.response.MallResponse;
import lombok.Getter;

/**
 * 电商请求对象抽象类
 *
 * @author chengdr01
 */
@Getter
public abstract class MallRequest<T extends MallResponse> {
    /**
     * 请求参数对象
     */
    private final Object requestObj;

    public MallRequest(Object requestObj) {
        this.requestObj = requestObj;
    }

    /**
     * 返回请求响应的 class
     *
     * @return 请求响应的 class
     */
    public abstract Class<T> getResponseClass();
}

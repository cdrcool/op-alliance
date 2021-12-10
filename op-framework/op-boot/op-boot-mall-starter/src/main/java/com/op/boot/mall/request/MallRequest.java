package com.op.boot.mall.request;

import com.op.boot.mall.authentication.MallAuthentication;
import com.op.boot.mall.constants.MallType;
import com.op.boot.mall.response.MallResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * 电商请求
 *
 * @author chengdr01
 */
@AllArgsConstructor
@Getter
public abstract class MallRequest<P, R extends MallResponse> implements Serializable {
    /**
     * 电商类型枚举
     */
    private final MallType mallType;

    /**
     * 电商身份认证凭据
     */
    private final MallAuthentication authentication;

    /**
     * 请求参数对象
     */
    private final P requestObj;

    /**
     * 返回电商方法
     *
     * @return 电商方法
     */
    public abstract String getRequestMethod();

    /**
     * 返回请求响应的 class
     *
     * @return 请求响应的 class
     */
    public abstract Class<R> getResponseClass();
}

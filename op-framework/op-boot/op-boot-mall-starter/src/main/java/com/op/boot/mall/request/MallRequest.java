package com.op.boot.mall.request;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.response.MallResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 电商请求对象抽象类
 *
 * @param <P> 请求对象类型
 * @param <R> 请求响应类型
 * @author cdrcool
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class MallRequest<P, R extends MallResponse> implements Serializable {
    /**
     * 电商类型
     */
    private MallType mallType;

    /**
     * 身份认证凭据
     */
    private MallAuthentication authentication;

    /**
     * 请求参数对象
     */
    private P requestObj;

    /**
     * 返回请求方法
     *
     * @return 请求方法
     */
    public abstract String getRequestMethod();

    /**
     * 返回请求响应的 class
     *
     * @return 请求响应的 class
     */
    public abstract Class<R> getResponseClass();
}

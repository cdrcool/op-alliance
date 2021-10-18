package com.op.mall.request;

import com.op.mall.client.MallAuthentication;
import com.op.mall.constans.MallType;
import com.op.mall.response.MallResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 电商请求对象抽象类
 *
 * @author chengdr01
 */
@Getter
@AllArgsConstructor
public abstract class MallRequest<T extends MallResponse> implements Serializable {
    /**
     * 电商类型
     */
    private final MallType mallType;

    /**
     * 身份认证凭据
     */
    private final MallAuthentication authentication;

    /**
     * 请求参数对象
     */
    private final Object requestObj;

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
    public abstract Class<T> getResponseClass();
}

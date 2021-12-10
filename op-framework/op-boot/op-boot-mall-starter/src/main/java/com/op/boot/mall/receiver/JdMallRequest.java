package com.op.boot.mall.receiver;

import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.boot.mall.authentication.JdMallAuthentication;
import lombok.Getter;

/**
 * 京东电商请求对象
 *
 * @author cdrcool
 */
@Getter
public class JdMallRequest<T extends AbstractResponse> {
    /**
     * 京东电商身份认证凭据
     */
    private final JdMallAuthentication authentication;

    /**
     * 京东请求对象
     */
    private final JdRequest<T> jdRequest;

    public JdMallRequest(JdMallAuthentication authentication, JdRequest<T> jdRequest) {
        this.authentication = authentication;
        this.jdRequest = jdRequest;
    }
}

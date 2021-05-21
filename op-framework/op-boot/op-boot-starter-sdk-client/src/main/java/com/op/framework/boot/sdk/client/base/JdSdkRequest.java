package com.op.framework.boot.sdk.client.base;

import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.AbstractResponse;
import lombok.Data;

/**
 * 京东 SDK 请求对象
 *
 * @author chengdr01
 */
@Data
public class JdSdkRequest<T extends AbstractResponse> {
    /**
     * 访问令牌
     */
    private String token;

    /**
     * 京东请求对象
     */
    private JdRequest<T> jdRequest;

    public JdSdkRequest(JdRequest<T> jdRequest) {
        this.jdRequest = jdRequest;
    }

    public JdSdkRequest(String token, JdRequest<T> jdRequest) {
        this.token = token;
        this.jdRequest = jdRequest;
    }
}

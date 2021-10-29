package com.op.boot.mall.client.jingdong;

import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.boot.mall.client.MallAuthentication;
import lombok.Data;

/**
 * 京东电商请求对象
 *
 * @author cdrcool
 */
@Data
public class JdMallRequest<T extends AbstractResponse> {
    /**
     * 电商身份认证凭证凭据
     */
    private MallAuthentication authentication;

    /**
     * 京东请求对象
     */
    private JdRequest<T> jdRequest;

    public JdMallRequest(MallAuthentication authentication, JdRequest<T> jdRequest) {
        this.authentication = authentication;
        this.jdRequest = jdRequest;
    }
}

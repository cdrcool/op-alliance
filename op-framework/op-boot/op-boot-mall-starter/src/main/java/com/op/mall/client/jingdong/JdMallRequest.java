package com.op.mall.client.jingdong;

import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.AbstractResponse;
import lombok.Data;

/**
 * 京东电商请求对象
 *
 * @author cdrcool
 */
@Data
public class JdMallRequest<T extends AbstractResponse> {
    /**
     * 纳税人识别号
     */
    private String taxpayerId;

    /**
     * 京东请求对象
     */
    private JdRequest<T> jdRequest;

    public JdMallRequest(String taxpayerId, JdRequest<T> jdRequest) {
        this.taxpayerId = taxpayerId;
        this.jdRequest = jdRequest;
    }
}

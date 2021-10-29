package com.op.boot.mall.client.suning;

import com.op.boot.mall.client.MallAuthentication;
import com.suning.api.SuningRequest;
import com.suning.api.SuningResponse;
import lombok.Data;

/**
 * 苏宁电商请求对象
 *
 * @author cdrcool
 */
@Data
public class SnMallRequest<T extends SuningResponse> {
    /**
     * 电商身份认证凭证凭据
     */
    private MallAuthentication authentication;

    /**
     * 苏宁请求对象
     */
    private SuningRequest<T> snRequest;

    public SnMallRequest(MallAuthentication authentication, SuningRequest<T> snRequest) {
        this.authentication = authentication;
        this.snRequest = snRequest;
    }
}

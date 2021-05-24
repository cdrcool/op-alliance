package com.op.framework.boot.sdk.client.base;

import com.suning.api.SuningRequest;
import com.suning.api.SuningResponse;
import lombok.Data;

/**
 * 苏宁 SDK 请求对象
 *
 * @author cdrcool
 */
@Data
public class SnSdkRequest<T extends SuningResponse> {
    /**
     * 访问令牌
     */
    private String token;

    /**
     * 苏宁请求对象
     */
    private SuningRequest<T> snRequest;

    public SnSdkRequest(SuningRequest<T> snRequest) {
        this.snRequest = snRequest;
    }

    public SnSdkRequest(String token, SuningRequest<T> snRequest) {
        this.token = token;
        this.snRequest = snRequest;
    }
}

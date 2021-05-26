package com.op.framework.boot.sdk.client.feign;

import com.op.framework.boot.sdk.client.base.SnSdkClient;
import com.op.framework.boot.sdk.client.base.SnSdkRequest;
import com.op.framework.boot.sdk.client.feign.config.SnClientFeignConfig;
import com.suning.api.SuningResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 苏宁 Feign Client
 *
 * @author chengdr01
 */
@FeignClient(name = "sn-sdk", url = "${sdk.third.sn.server-url}", configuration = SnClientFeignConfig.class)
public interface SnSdkFeignClient extends SnSdkClient {

    /**
     * 执行请求
     *
     * @param snSdkRequest 请求对象
     * @param <T>          请求对象泛型类型
     * @return 响应对象
     */
    @GetMapping
    @Override
    <T extends SuningResponse> T execute(SnSdkRequest<T> snSdkRequest);
}

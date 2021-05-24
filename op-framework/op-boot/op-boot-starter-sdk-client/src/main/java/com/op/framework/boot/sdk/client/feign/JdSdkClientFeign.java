package com.op.framework.boot.sdk.client.feign;

import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.framework.boot.sdk.client.base.JdSdkClient;
import com.op.framework.boot.sdk.client.base.JdSdkRequest;
import com.op.framework.boot.sdk.client.feign.config.JdApiFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 京东 Feign Client
 *
 * @author chengdr01
 */
@FeignClient(name = "jd", contextId = "jd-api", url = "${sdk.accounts.jd.auth-url}", configuration = JdApiFeignConfig.class)
public interface JdSdkClientFeign extends JdSdkClient {

    /**
     * 执行请求
     *
     * @param jdSdkRequest 请求对象
     * @param <T>          请求对象泛型类型
     * @return 响应对象
     */
    @GetMapping
    @Override
    <T extends AbstractResponse> T execute(JdSdkRequest<T> jdSdkRequest);
}

package com.op.boot.mall.receiver;

import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.boot.mall.receiver.config.JdMallFeignConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 京东电商 Client Feign
 *
 * @author cdrcool
 */
@ConditionalOnProperty(prefix = "mall.jd", value = "request-type", havingValue = "feign")
@FeignClient(name = "jd-mall", url = "${mall.jd.server-url}", configuration = JdMallFeignConfig.class)
public interface JdMallClientFeign extends JdMallClient {

    /**
     * 执行京东电商请求
     *
     * @param jdMallRequest 请求对象
     * @param <T>           请求响应泛型
     * @return 请求响应
     */
    @GetMapping
    @Override
    <T extends AbstractResponse> T execute(JdMallRequest<T> jdMallRequest);
}

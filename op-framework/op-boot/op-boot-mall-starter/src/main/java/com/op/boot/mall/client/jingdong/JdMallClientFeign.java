package com.op.boot.mall.client.jingdong;

import com.jd.open.api.sdk.response.AbstractResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 京东电商 Client Feign 方式实现类
 *
 * @author cdrcool
 */
@ConditionalOnProperty(prefix = "mall.jd", value = "request-type", havingValue = "feign")
@FeignClient(name = "jd-mall", url = "${mall.jd.server-url}", configuration = JdMallClientFeignConfig.class)
public interface JdMallClientFeign extends JdMallClient {

    /**
     * 执行请求
     *
     * @param jdSdkRequest 请求对象
     * @param <T>          请求对象泛型类型
     * @return 响应对象
     */
    @GetMapping
    @Override
    <T extends AbstractResponse> T execute(JdMallRequest<T> jdSdkRequest);
}

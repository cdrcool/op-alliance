package com.op.boot.mall.client.jingdong;

import com.op.boot.mall.properties.JdMallProperties;
import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 京东 Feign 配置基类
 *
 * @author cdrcool
 */
public abstract class BaseJdMallFeignConfig {
    private final JdMallProperties properties;

    public BaseJdMallFeignConfig(JdMallProperties properties) {
        this.properties = properties;
    }

    /**
     * 请求拦截器
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .removeHeader("o")
                .removeHeader("token");
    }

    /**
     * 请求配置（连接超时、读超时等）
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(properties.getConnectTimeout(), properties.getReadTimeout());
    }

    /**
     * 请求超时重试机制
     * 默认情况下，GET 请求无论是连接超时还是读取超时都会进行重试，非 GET 请求只有在连接超时时才会进行重试
     */
    @Bean
    public Retryer retryer() {
        return new Retryer.Default(100, SECONDS.toMillis(1), properties.getMaxAttempts());
    }

    /**
     * 日志级别
     */
    @Bean
    public Logger.Level loggerLevel() {
        return Logger.Level.FULL;
    }

    public JdMallProperties getProperties() {
        return properties;
    }
}

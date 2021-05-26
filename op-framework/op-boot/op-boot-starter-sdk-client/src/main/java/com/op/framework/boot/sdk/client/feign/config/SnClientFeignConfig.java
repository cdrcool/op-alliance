package com.op.framework.boot.sdk.client.feign.config;

import com.op.framework.boot.sdk.client.SdkProperties;
import com.op.framework.boot.sdk.client.account.exception.ThirdAccountException;
import com.op.framework.boot.sdk.client.account.service.ThirdAccountService;
import com.op.framework.boot.sdk.client.base.SnSdkRequest;
import com.op.framework.boot.sdk.client.base.ThirdSdkType;
import com.suning.api.SuningRequest;
import com.suning.api.SuningResponse;
import feign.*;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 苏宁 Client Feign 配置类
 *
 * @author cdrcool
 */
@Slf4j
public class SnClientFeignConfig {
    private final ThreadLocal<SnRequestInfo> threadLocal = ThreadLocal.withInitial(SnRequestInfo::new);

    private final SdkProperties sdkProperties;
    private final ThirdAccountService thirdAccountService;

    public SnClientFeignConfig(SdkProperties sdkProperties, ThirdAccountService thirdAccountService) {
        this.sdkProperties = sdkProperties;
        this.thirdAccountService = thirdAccountService;
    }

    @Bean
    public Encoder encoder() {
        return (object, bodyType, template) -> {
            SnSdkRequest<? extends SuningResponse> snSdkRequest = (SnSdkRequest<? extends SuningResponse>) object;
            String token = snSdkRequest.getToken();
            SuningRequest<? extends SuningResponse> snRequest = snSdkRequest.getSnRequest();

            SdkProperties.ThirdProperties thirdProperties = Optional.ofNullable(sdkProperties.getThird().get(ThirdSdkType.SN.getValue()))
                    .orElseThrow(() -> new ThirdAccountException("未找到京东账号配置"));


            if (!StringUtils.hasText(token)) {
                token = thirdAccountService.getAccessToken(thirdProperties.getAccount(), null);
            }

            // TODO

            threadLocal.set(new SnRequestInfo(snRequest.getAppMethod(), snRequest.getResponseClass()));

            log.info("请求苏宁接口：{}，请求参数：{}", snRequest.getAppMethod(), template.queries());
        };
    }

    @Bean
    public Decoder decoder() {
        return (response, type) -> {
            SnRequestInfo snRequestInfo = threadLocal.get();
            threadLocal.remove();

            String responseJson = Util.toString(response.body().asReader());
            log.info("获取苏宁接口：{} 响应，请求响应：{}", snRequestInfo.getMethod(), responseJson);

            // TODO
            return null;
        };
    }

    /**
     * 请求拦截器
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
    }

    /**
     * 请求配置（连接超时、读超时等）
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(5000, 30000);
    }

    /**
     * 请求超时重试机制
     * 默认情况下，GET请求无论是连接超时还是读取超时都会进行重试，非GET请求只有在连接超时时才会进行重试
     */
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(100, SECONDS.toMillis(1), 3);
    }

    /**
     * 日志级别
     */
    @Bean
    public Logger.Level jdFeignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class SnRequestInfo {
        /**
         * 方法名
         */
        private String method;

        /**
         * 返回类型的 Class
         */
        private Class<? extends SuningResponse> responseClass;
    }
}

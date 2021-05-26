package com.op.framework.boot.sdk.client.feign.config;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.internal.parser.Parser;
import com.jd.open.api.sdk.internal.parser.ParserFactory;
import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.framework.boot.sdk.client.SdkProperties;
import com.op.framework.boot.sdk.client.account.exception.ThirdAccountException;
import com.op.framework.boot.sdk.client.account.service.ThirdAccountService;
import com.op.framework.boot.sdk.client.base.ThirdSdkType;
import com.op.framework.boot.sdk.client.base.JdSdkRequest;
import com.op.framework.boot.sdk.client.exception.JdInvokeException;
import feign.*;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 京东 API Feign 配置类
 *
 * @author cdrcool
 */
@Slf4j
public class JdApiFeignConfig {
    private final ThreadLocal<JdRequestInfo> threadLocal = ThreadLocal.withInitial(JdRequestInfo::new);

    private final SdkProperties sdkProperties;
    private final ThirdAccountService thirdAccountService;

    public JdApiFeignConfig(SdkProperties sdkProperties, @Qualifier("jdAccountService") ThirdAccountService thirdAccountService) {
        this.sdkProperties = sdkProperties;
        this.thirdAccountService = thirdAccountService;
    }

    @Bean
    public Encoder encoder() {
        return (object, bodyType, template) -> {
            JdSdkRequest<? extends AbstractResponse> jdSdkRequest = (JdSdkRequest<? extends AbstractResponse>) object;
            String token = jdSdkRequest.getToken();
            JdRequest<? extends AbstractResponse> jdRequest = jdSdkRequest.getJdRequest();

            SdkProperties.ThirdProperties thirdProperties = Optional.ofNullable(sdkProperties.getAccounts().get(ThirdSdkType.JD.getValue()))
                    .orElseThrow(() -> new ThirdAccountException("未找到京东账号配置"));

            if (!StringUtils.hasText(token)) {
                token = thirdAccountService.getAccessToken(thirdProperties.getAccount(), null);
            }

            Map<String, String> sysParams = jdRequest.getSysParams();

            Map<String, String> params = new TreeMap<>(String::compareTo);
            params.put("v", sysParams.get("v"));
            params.put("method", jdRequest.getApiMethod());
            params.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));

            params.put("app_key", thirdProperties.getAppKey());
            params.put("access_token", token);

            String appJsonParams;
            try {
                appJsonParams = jdRequest.getAppJsonParams();
                params.put("360buy_param_json", appJsonParams);
            } catch (IOException e) {
                log.error("序列化请求参数 360buy_param_json 异常", e);
                throw new JdInvokeException("序列化请求参数 360buy_param_json 异常", e);
            }

            String otherParams;
            try {
                otherParams = jdRequest.getOtherParams();
                if (otherParams != null) {
                    params.put("other", otherParams);
                }
            } catch (IOException e) {
                log.error("序列化请求参数 other 异常", e);
                throw new JdInvokeException("序列化请求参数 other 异常", e);
            }

            String paramsStr = thirdProperties.getAppSecret() +
                    params.entrySet().stream().map(entry -> entry.getKey() + entry.getValue()).collect(Collectors.joining()) +
                    thirdProperties.getAppSecret();
            String sign = DigestUtils.md5Hex(paramsStr.getBytes(StandardCharsets.UTF_8)).toUpperCase();
            params.put("sign", sign);

            params.forEach(template::query);

            // json数据需要encode一次，不然会提示无效签名
            try {
                template.query("360buy_param_json", URLEncoder.encode(appJsonParams, "UTF-8"));
                if (otherParams != null) {
                    template.query("other", URLEncoder.encode(otherParams, "UTF-8"));
                }
            } catch (UnsupportedEncodingException e) {
                throw new JdInvokeException("编码请求参数 360buy_param_json 异常", e);
            }

            threadLocal.set(new JdRequestInfo(jdRequest.getApiMethod(), jdRequest.getResponseClass()));

            log.info("请求京东接口：{}，请求参数：{}", jdRequest.getApiMethod(), template.queries());
        };
    }

    @Bean
    public Decoder decoder() {
        return (response, type) -> {
            JdRequestInfo jdRequestInfo = threadLocal.get();
            threadLocal.remove();

            String responseJson = Util.toString(response.body().asReader());

            try {
                Parser parser = ParserFactory.getJsonParser();
                AbstractResponse abstractResponse = parser.parse(responseJson, jdRequestInfo.getResponseClass(), jdRequestInfo.getMethod());

                if (StringUtils.hasText(abstractResponse.getZhDesc())) {
                    log.error("调用京东接口【{}】失败，错误消息：{}", jdRequestInfo.getMethod(), abstractResponse.getZhDesc());
                    throw new JdInvokeException(String.format("调用京东接口【%s】失败，错误消息：%s", jdRequestInfo.getMethod(), abstractResponse.getZhDesc()));
                }

                log.info("获取京东接口：{} 响应，请求响应：{}", jdRequestInfo.getMethod(), responseJson);
                return abstractResponse;
            } catch (JdException e) {
                log.error("反序列化请求响应异常，请求响应：{}", responseJson, e);
                throw new JdInvokeException("反序列化请求响应异常", e);
            }
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
        return new Request.Options(5, SECONDS, 30, SECONDS, true);
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
    Logger.Level jdFeignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class JdRequestInfo {
        /**
         * 方法名
         */
        private String method;

        /**
         * 返回类型的 Class
         */
        private Class<? extends AbstractResponse> responseClass;
    }

}

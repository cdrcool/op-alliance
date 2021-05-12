package com.op.sdk.client.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.op.sdk.client.third.response.JdTokenResponse;
import feign.*;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 京东Feign配置类
 *
 * @author cdrcool
 */
@Slf4j
public class JdFeignConfig {
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 请求参数编码器
     */
    @Bean
    public Encoder encoder() {
        return (object, bodyType, template) -> {
            Map<String, Object> param = objectMapper.convertValue(object, new TypeReference<Map<String, Object>>() {
            });
            param.forEach((key, value) -> {
                if (value != null) {
                    if (!(value instanceof Number || value instanceof String)) {
                        try {
                            value = objectMapper.writeValueAsString(value);
                        } catch (JsonProcessingException e) {
                            log.error("序列化请求参数：{}异常", key);
                            throw new RuntimeException("序列化请求参数：" + key + "异常");
                        }
                    }
                    template.query(key, value.toString());
                }
            });
            log.info("请求京东接口：{}，请求参数：{}", template.url(), template.queries());
        };
    }

    /**
     * 请求响应解码器
     */
    @Bean
    public Decoder decoder() {
        return (response, type) -> {
            String responseJson = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            log.info("请求京东接口：{}，请求响应：{}", response.request().url(), responseJson);

            String typeName = type.getTypeName();
            if (Objects.equals(typeName, String.class.getName())) {
                return responseJson;
            }

            if (Objects.equals(typeName, JdTokenResponse.class.getName())) {
                JdTokenResponse jdTokenResponse = objectMapper.readValue(responseJson, JdTokenResponse.class);
                if (!Objects.equals(jdTokenResponse.getCode(), 0)) {
                    log.error("请求京东接口：{}失败，返回码：{}", response.request().url(), jdTokenResponse.getCode());
                    throw new RuntimeException("请求京东接口：" + response.request().url() + "失败，返回码：" + jdTokenResponse.getCode());
                }
                return jdTokenResponse;
            }

            // TODO 其它场景解析
            return responseJson;
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
    public Logger.Level jdFeignLoggerLevel() {
        return Logger.Level.FULL;
    }
}

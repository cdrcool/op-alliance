package com.op.boot.mall.client.jingdong;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.properties.JdMallProperties;
import feign.Util;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;

/**
 * 京东电商 Token Feign 配置类
 *
 * @author chendgr01
 */
@Slf4j
public class JdMallTokenFeignConfig extends BaseJdMallFeignConfig {
    /**
     * 调用京东接口成功时的返回码
     */
    private static final String SUCCESS_CODE = "0";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JdMallTokenFeignConfig(JdMallProperties properties) {
        super(properties);
    }

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

    @Bean
    public Decoder decoder() {
        return (response, type) -> {
            String responseJson = Util.toString(response.body().asReader());
            log.info("请求京东接口：{}，请求响应：{}", response.request().url(), responseJson);

            // 字符串直接返回
            String typeName = type.getTypeName();
            if (Objects.equals(typeName, String.class.getName())) {
                return responseJson;
            }

            // 京东电商 Token 响应
            if (Objects.equals(type.getTypeName(), JdTokenResponse.class.getName())) {
                JdTokenResponse tokenResponse = objectMapper.readValue(responseJson, JdTokenResponse.class);
                if (tokenResponse.getCode() != null && !tokenResponse.getCode().equals(SUCCESS_CODE)) {
                    log.error("请求京东接口：{}失败，错误码：{}", response.request().url(), tokenResponse.getCode());
                    throw new JdMallException(MessageFormat.format("请求京东接口：{0}失败",
                            response.request().url()), String.valueOf(tokenResponse.getCode()));
                }
                return tokenResponse;
            }

            // 京东电商响应
            JdBaseResponse<Object> baseResponse = objectMapper.readValue(responseJson, new TypeReference<JdBaseResponse<Object>>() {
                @Override
                public Type getType() {
                    return type;
                }
            });
            if (!Objects.equals(baseResponse.getSuccess(), true) && !Objects.equals(SUCCESS_CODE, baseResponse.getResultCode())) {
                log.error("请求京东接口：{}失败，错误码：{}，错误信息：{}",
                        response.request().url(), baseResponse.getResultCode(), baseResponse.getResultMessage());
                throw new JdMallException(MessageFormat.format("请求京东接口：{0}失败，错误信息：{1}",
                        response.request().url(), baseResponse.getResultMessage()), baseResponse.getResultCode());
            }
            return baseResponse;
        };
    }
}

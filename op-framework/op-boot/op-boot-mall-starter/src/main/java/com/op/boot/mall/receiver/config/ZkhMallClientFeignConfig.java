package com.op.boot.mall.receiver.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.op.boot.mall.exception.ZkhMallException;
import com.op.boot.mall.response.ZkhMallBaseResponse;
import com.op.boot.mall.token.response.ZkhMallTokenResponse;
import com.op.boot.mall.utils.JsonUtils;
import feign.Util;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * 震坤行 Feign 配置类
 *
 * @author chendgr01
 */
@Slf4j
public class ZkhMallClientFeignConfig {
    /**
     * 调用震坤行接口成功时的返回码
     */
    private static final String SUCCESS_CODE = "0000";

    /**
     * 请求对象编码
     *
     * @return 编码器
     */
    @Bean
    public Encoder encoder() {
        return (object, bodyType, template) -> {
            template.header("Content-type", "application/json;charset=UTF-8");

            template.body(JsonUtils.toString(object).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

            log.info("请求震坤行接口【{}】，请求参数【{}】", template.url(), new String(template.body(), StandardCharsets.UTF_8));
        };
    }

    /**
     * 请求响应解码
     *
     * @return 解码器
     */
    @Bean
    public Decoder decoder() {
        return (response, type) -> {
            String responseJson = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            log.info("请求震坤行接口【{}】，请求响应【{}】", response.request().url(), responseJson);

            // 如果返回类型是字符串，直接返回
            String typeName = type.getTypeName();
            if (Objects.equals(typeName, String.class.getName())) {
                return responseJson;
            }

            // 如果返回类型是 ZkhMallTokenResponse
            if (Objects.equals(type.getTypeName(), ZkhMallTokenResponse.class.getName())) {
                ZkhMallTokenResponse tokenResponse = JsonUtils.parse(responseJson, ZkhMallTokenResponse.class);
                assert tokenResponse != null;
                if (tokenResponse.getCode() != null && !tokenResponse.getCode().equals(SUCCESS_CODE)) {
                    log.error("请求震坤行接口【{}】失败，错误码【{}】", response.request().url(), tokenResponse.getCode());
                    throw new ZkhMallException(MessageFormat.format("请求震坤行接口【{0}】失败",
                            response.request().url()), String.valueOf(tokenResponse.getCode()));
                }
                return tokenResponse;
            }

            // 最后，断言返回类型是 ZkhMallBaseResponse
            ZkhMallBaseResponse<Object> baseResponse = JsonUtils.parse(responseJson,
                    new TypeReference<ZkhMallBaseResponse<Object>>() {
                        @Override
                        public Type getType() {
                            return type;
                        }
                    });
            assert baseResponse != null;
            if (!Objects.equals(baseResponse.getSuccess(), true) && !Objects.equals(SUCCESS_CODE, baseResponse.getResultCode())) {
                log.error("调用震坤行接口【{}】失败，错误码【{}】，错误消息【{}】",
                        response.request().url(), baseResponse.getResultCode(), baseResponse.getResultMessage());
                throw new ZkhMallException(MessageFormat.format("请求震坤行接口【{0}】失败，错误信息【{1}】",
                        response.request().url(), baseResponse.getResultMessage()), baseResponse.getResultCode());
            }
            return baseResponse;
        };
    }
}

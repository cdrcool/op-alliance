package com.op.boot.mall.receiver.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.internal.parser.Parser;
import com.jd.open.api.sdk.internal.parser.ParserFactory;
import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.boot.mall.authentication.JdMallAuthentication;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.receiver.JdMallRequest;
import com.op.boot.mall.response.JdMallBaseResponse;
import com.op.boot.mall.token.response.JdMallTokenResponse;
import com.op.boot.mall.utils.JsonUtils;
import feign.Util;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 京东电商 Feign 配置类
 *
 * @author chendgr01
 */
@Slf4j
public class JdMallFeignConfig {
    /**
     * 请求执行成功的响应码
     */
    private static final String SUCCESS_CODE = "0";

    /**
     * 京东请求信息线程本地存储
     */
    private final ThreadLocal<JdRequestInfo> threadLocal = ThreadLocal.withInitial(JdRequestInfo::new);

    /**
     * 请求对象编码
     *
     * @return 编码器
     */
    @Bean
    public Encoder encoder() {
        return (object, bodyType, template) -> {
            JdMallRequest<? extends AbstractResponse> jdMallRequest = (JdMallRequest<? extends AbstractResponse>) object;
            JdMallAuthentication authentication = jdMallRequest.getAuthentication();
            JdRequest<? extends AbstractResponse> jdRequest = jdMallRequest.getJdRequest();

            Map<String, String> sysParams = jdRequest.getSysParams();

            Map<String, String> params = new TreeMap<>(String::compareTo);
            params.put("v", sysParams.get("v"));
            params.put("method", jdRequest.getApiMethod());
            params.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));

            params.put("app_key", authentication.getAppKey());
            params.put("access_token", authentication.getAccessToken());

            String appJsonParams;
            try {
                appJsonParams = jdRequest.getAppJsonParams();
                params.put("360buy_param_json", appJsonParams);
            } catch (IOException e) {
                log.error("序列化请求参数 360buy_param_json 异常", e);
                throw new JdMallException("序列化请求参数 360buy_param_json 异常", e);
            }

            String otherParams;
            try {
                otherParams = jdRequest.getOtherParams();
                if (otherParams != null) {
                    params.put("other", otherParams);
                }
            } catch (IOException e) {
                log.error("序列化请求参数 other 异常", e);
                throw new JdMallException("序列化请求参数 other 异常", e);
            }

            String paramsStr = authentication.getAppSecret() +
                    params.entrySet().stream().map(entry -> entry.getKey() + entry.getValue()).collect(Collectors.joining()) +
                    authentication.getAppSecret();
            String sign = DigestUtils.md5Hex(paramsStr.getBytes(StandardCharsets.UTF_8)).toUpperCase();
            params.put("sign", sign);

            params.entrySet().stream()
                    .filter(entry -> !"360buy_param_json".equals(entry.getKey()) && !"other".equals(entry.getKey()))
                    .forEach(entry -> template.query(entry.getKey(), entry.getValue()));

            // json 数据需要 encode 一次，不然会提示无效签名
            try {
                template.query("360buy_param_json", URLEncoder.encode(appJsonParams, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new JdMallException("编码请求参数 360buy_param_json 异常", e);
            }
            if (otherParams != null) {
                try {
                    template.query("other", URLEncoder.encode(otherParams, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new JdMallException("编码请求参数 other 异常", e);
                }
            }

            threadLocal.set(new JdRequestInfo(jdRequest.getApiMethod(), jdRequest.getResponseClass()));

            log.info("请求京东接口【{}】，请求参数【{}】", jdRequest.getApiMethod(), template.queries());
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
            JdRequestInfo jdRequestInfo = threadLocal.get();
            threadLocal.remove();

            String responseJson = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            log.info("请求京东接口【{}】，请求响应【{}】", jdRequestInfo.getMethod(), responseJson);

            // 如果返回类型是字符串，直接返回
            String typeName = type.getTypeName();
            if (Objects.equals(typeName, String.class.getName())) {
                return responseJson;
            }

            // 如果返回类型是 JdMallTokenResponse
            if (Objects.equals(type.getTypeName(), JdMallTokenResponse.class.getName())) {
                JdMallTokenResponse tokenResponse = JsonUtils.parse(responseJson, JdMallTokenResponse.class);
                assert tokenResponse != null;
                if (tokenResponse.getCode() != null && !tokenResponse.getCode().equals(SUCCESS_CODE)) {
                    log.error("请求京东接口【{}】失败，错误码【{}】", response.request().url(), tokenResponse.getCode());
                    throw new JdMallException(MessageFormat.format("请求京东接口【{0}】失败",
                            response.request().url()), String.valueOf(tokenResponse.getCode()));
                }
                return tokenResponse;
            }

            // 如果返回类型是 JdMallBaseResponse
            if (Objects.equals(type.getTypeName(), JdMallBaseResponse.class.getName())) {
                JdMallBaseResponse<Object> baseResponse = JsonUtils.parse(responseJson,
                        new TypeReference<JdMallBaseResponse<Object>>() {
                            @Override
                            public Type getType() {
                                return type;
                            }
                        });
                assert baseResponse != null;
                if (!Objects.equals(baseResponse.getSuccess(), true) && !Objects.equals(SUCCESS_CODE, baseResponse.getResultCode())) {
                    log.error("请求京东接口【{}】失败，错误码【{}】，错误信息【{}】",
                            response.request().url(), baseResponse.getResultCode(), baseResponse.getResultMessage());
                    throw new JdMallException(MessageFormat.format("请求京东接口【{0}】失败，错误信息【{1}】",
                            response.request().url(), baseResponse.getResultMessage()), baseResponse.getResultCode());
                }
            }

            // 最后，断言返回类型是 AbstractResponse
            Parser parser = ParserFactory.getJsonParser();
            AbstractResponse jdResponse;
            try {
                jdResponse = parser.parse(responseJson, jdRequestInfo.getResponseClass(), jdRequestInfo.getMethod());
            } catch (JdException e) {
                log.error("反序列化京东请求响应异常，请求响应【{}】", responseJson, e);
                throw new JdMallException("反序列化京东请求响应异常", e);
            }

            if (!SUCCESS_CODE.equals(jdResponse.getCode())) {
                log.error("调用京东接口【{}】失败，错误码【{}】，错误消息【{}】",
                        jdRequestInfo.getMethod(), jdResponse.getCode(), jdResponse.getZhDesc());
                throw new JdMallException(jdResponse.getZhDesc(), jdResponse.getCode());
            }

            return jdResponse;
        };
    }

    /**
     * 京东请求信息
     */
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

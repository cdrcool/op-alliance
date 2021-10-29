package com.op.boot.mall.client.jingdong;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.internal.parser.Parser;
import com.jd.open.api.sdk.internal.parser.ParserFactory;
import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.exception.MallException;
import com.op.boot.mall.properties.JdMallProperties;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 京东电商 Client Feign 配置类
 *
 * @author chendgr01
 */
@Slf4j
public class JdMallClientFeignConfig extends BaseJdMallFeignConfig {
    /**
     * 请求执行成功的响应码
     */
    private static final String SUCCESS_CODE = "0";

    private final ThreadLocal<JdRequestInfo> threadLocal = ThreadLocal.withInitial(JdRequestInfo::new);

    public JdMallClientFeignConfig(JdMallProperties properties) {
        super(properties);
    }

    @Bean
    public Encoder jdClientEncoder() {
        return (object, bodyType, template) -> {
            JdMallRequest<? extends AbstractResponse> jdSdkRequest = (JdMallRequest<? extends AbstractResponse>) object;
            JdMallAuthentication authentication = (JdMallAuthentication) jdSdkRequest.getAuthentication();
            JdRequest<? extends AbstractResponse> jdRequest = jdSdkRequest.getJdRequest();

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
                throw new MallException("序列化请求参数 360buy_param_json 异常", e);
            }

            String otherParams;
            try {
                otherParams = jdRequest.getOtherParams();
                if (otherParams != null) {
                    params.put("other", otherParams);
                }
            } catch (IOException e) {
                log.error("序列化请求参数 other 异常", e);
                throw new MallException("序列化请求参数 other 异常", e);
            }

            String paramsStr = authentication.getAppSecret() +
                    params.entrySet().stream().map(entry -> entry.getKey() + entry.getValue()).collect(Collectors.joining()) +
                    authentication.getAppSecret();
            String sign = DigestUtils.md5Hex(paramsStr.getBytes(StandardCharsets.UTF_8)).toUpperCase();
            params.put("sign", sign);

            params.forEach(template::query);

            // json 数据需要 encode 一次，不然会提示无效签名
            try {
                template.query("360buy_param_json", URLEncoder.encode(appJsonParams, "UTF-8"));
                if (otherParams != null) {
                    template.query("other", URLEncoder.encode(otherParams, "UTF-8"));
                }
            } catch (UnsupportedEncodingException e) {
                throw new MallException("编码请求参数 360buy_param_json 异常", e);
            }

            threadLocal.set(new JdRequestInfo(jdRequest.getApiMethod(), jdRequest.getResponseClass()));

            log.info("请求京东接口：{}，请求参数：{}", jdRequest.getApiMethod(), template.queries());
        };
    }

    @Bean
    public Decoder jdClientDecoder() {
        return (response, type) -> {
            JdRequestInfo jdRequestInfo = threadLocal.get();
            threadLocal.remove();

            String responseJson = Util.toString(response.body().asReader());
            log.info("请求京东接口：{}，请求响应：{}", jdRequestInfo.getMethod(), responseJson);

            try {
                Parser parser = ParserFactory.getJsonParser();
                AbstractResponse jdResponse = parser.parse(responseJson, jdRequestInfo.getResponseClass(), jdRequestInfo.getMethod());

                if (!SUCCESS_CODE.equals(jdResponse.getCode())) {
                    log.error("调用京东接口【{}】失败，错误码：【{}】，错误消息：【{}】",
                            jdRequestInfo.getMethod(), jdResponse.getCode(), jdResponse.getZhDesc());
                    throw new JdMallException(jdResponse.getZhDesc(), jdResponse.getCode());
                }

                return jdResponse;
            } catch (JdException e) {
                log.error("反序列化请求响应异常，请求响应：{}", responseJson, e);
                throw new MallException("反序列化请求响应异常", e);
            }
        };
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

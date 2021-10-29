package com.op.boot.mall.client.suning;

import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.exception.SnMallException;
import com.op.boot.mall.properties.SnMallProperties;
import com.suning.api.DefaultSuningClient;
import com.suning.api.SuningRequest;
import com.suning.api.SuningResponse;
import com.suning.api.exception.SuningApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 苏宁 SDK Client 适配类
 *
 * @author cdrcool
 */
@Slf4j
@ConditionalOnProperty(prefix = "sdk", value = "third.sn.request-type", havingValue = "sdk", matchIfMissing = true)
@Primary
@Component
public class SnMallClientAdapter implements SnMallClient {
    /**
     * 请求执行成功的响应码
     */
    private static final String SUCCESS_CODE = "0";

    private final SnMallProperties properties;

    public SnMallClientAdapter(SnMallProperties properties) {
        this.properties = properties;
    }

    @Override
    public <T extends SuningResponse> T execute(SnMallRequest<T> request) {
        SuningRequest<T> snRequest = request.getSnRequest();

        try {
            DefaultSuningClient snClient = createSnClient(request.getAuthentication());

            T response = snClient.excute(snRequest);

            if (!SUCCESS_CODE.equals(response.getCode())) {
                log.error("调用苏宁接口【{}】失败，错误码：【{}】，错误消息：【{}】，请求参数：【{}】，系统参数：【{}】",
                        snRequest.getApiMethdeName(), response.getCode(), response.getBody(), serializeRequest(snRequest), snRequest.getSysParams());
                throw new SnMallException(response.getBody(), response.getCode());
            }

            log.info("调用苏宁接口【{}】成功，请求参数：【{}】，请求响应：【{}】",
                    snRequest.getApiMethdeName(), serializeRequest(snRequest), deserializeResponse(response));
            return response;
        } catch (Exception e) {
            log.error("调用苏宁接口【{}】失败，请求参数：【{}】，系统参数：【{}】",
                    snRequest.getApiMethdeName(), serializeRequest(snRequest), snRequest.getSysParams(), e);
            throw new SnMallException(e.getMessage(), "-1", e);
        }
    }

    /**
     * 创建 {@link DefaultSuningClient}
     *
     * @param authentication 电商身份认证凭证凭据
     * @return DefaultSuningClient
     */
    private DefaultSuningClient createSnClient(MallAuthentication authentication) {
        SnMallAuthentication snMallAuthentication = (SnMallAuthentication) authentication;
        return new DefaultSuningClient(properties.getServerUrl(),
                snMallAuthentication.getAppKey(),
                snMallAuthentication.getAppSecret(),
                null,
                "json",
                properties.getConnectTimeout(),
                properties.getReadTimeout());
    }

    /**
     * 序列化请求对象
     *
     * @param snRequest 请求对象
     * @param <T>       请求响应泛型
     * @return 请求对象字符串表示
     */
    private <T extends SuningResponse> String serializeRequest(SuningRequest<T> snRequest) {
        String requestJson = null;
        try {
            requestJson = snRequest.getResParams();
        } catch (SuningApiException e) {
            log.error("序列化苏宁请求参数异常", e);
        }
        return requestJson;
    }

    /**
     * 反序列化请求响应
     *
     * @param response 请求响应
     * @param <T>      请求响应泛型
     * @return 请求响应对象
     */
    private <T extends SuningResponse> String deserializeResponse(T response) {
        String responseJson = "";
        try {
            responseJson = com.jd.open.api.sdk.internal.util.JsonUtil.toJson(response);
        } catch (IOException e) {
            log.error("反序列化苏宁请求响应异常", e);
        }
        return responseJson;
    }
}

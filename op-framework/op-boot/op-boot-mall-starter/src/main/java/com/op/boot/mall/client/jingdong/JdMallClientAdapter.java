package com.op.boot.mall.client.jingdong;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.internal.util.JsonUtil;
import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.boot.mall.client.MallAuthentication;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.properties.JdMallProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 京东电商 Client 适配器类
 *
 * @author cdrcool
 */
@Slf4j
@ConditionalOnProperty(prefix = "mall.jd", value = "request-type", havingValue = "sdk", matchIfMissing = true)
@Primary
@Component
public class JdMallClientAdapter implements JdMallClient {
    /**
     * 请求执行成功的响应码
     */
    private static final String SUCCESS_CODE = "0";

    private final JdMallProperties properties;

    public JdMallClientAdapter(JdMallProperties properties) {
        this.properties = properties;
    }

    @Override
    public <T extends AbstractResponse> T execute(JdMallRequest<T> request) {
        JdRequest<T> jdRequest = request.getJdRequest();

        try {
            JdClient jdClient = createJdClient(request.getAuthentication());

            T response = jdClient.execute(jdRequest);

            if (!SUCCESS_CODE.equals(response.getCode())) {
                log.error("调用京东接口【{}】失败，错误码：【{}】，错误消息：【{}】，请求参数：【{}】，系统参数：【{}】",
                        jdRequest.getApiMethod(), response.getCode(), response.getZhDesc(), serializeRequest(jdRequest), jdRequest.getSysParams());
                throw new JdMallException(response.getZhDesc(), response.getCode());
            }

            log.info("调用京东接口【{}】成功，请求参数：【{}】，请求响应：【{}】",
                    jdRequest.getApiMethod(), serializeRequest(jdRequest), deserializeResponse(response));
            return response;
        } catch (Exception e) {
            log.error("调用京东接口【{}】失败，请求参数：【{}】，系统参数：【{}】",
                    jdRequest.getApiMethod(), serializeRequest(jdRequest), jdRequest.getSysParams(), e);
            throw new JdMallException(e.getMessage(), "-1", e);
        }
    }

    /**
     * 创建 {@link JdClient}
     *
     * @param authentication 电商身份认证凭证凭据
     * @return JdClient
     */
    private JdClient createJdClient(MallAuthentication authentication) {
        JdMallAuthentication jdMallAuthentication = (JdMallAuthentication) authentication;
        return new DefaultJdClient(properties.getServerUrl(),
                jdMallAuthentication.getAccessToken(),
                jdMallAuthentication.getAppKey(),
                jdMallAuthentication.getAppSecret(),
                properties.getConnectTimeout(),
                properties.getReadTimeout());
    }

    /**
     * 序列化请求对象
     *
     * @param jdRequest 请求对象
     * @param <T>       请求响应泛型
     * @return 请求对象字符串表示
     */
    private <T extends AbstractResponse> String serializeRequest(JdRequest<T> jdRequest) {
        String requestJson = "";
        try {
            requestJson = jdRequest.getAppJsonParams();
        } catch (IOException e) {
            log.error("序列化京东请求参数异常", e);
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
    private <T extends AbstractResponse> String deserializeResponse(T response) {
        String responseJson = "";
        try {
            responseJson = JsonUtil.toJson(response);
        } catch (IOException e) {
            log.error("反序列化京东请求响应异常", e);
        }
        return responseJson;
    }
}

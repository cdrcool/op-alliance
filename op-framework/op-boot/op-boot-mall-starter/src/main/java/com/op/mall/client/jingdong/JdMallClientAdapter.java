package com.op.mall.client.jingdong;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.internal.util.JsonUtil;
import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.mall.client.MallAuthenticationManager;
import com.op.mall.constans.MallType;
import com.op.mall.exception.JdMallException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 京东电商 Client 适配器类
 *
 * @author cdrcool
 */
@Slf4j
public class JdMallClientAdapter implements JdMallClient {
    /**
     * 请求执行成功的响应码
     */
    private static final String SUCCESS_CODE = "0";

    private final JdMallProperties properties;
    private final MallAuthenticationManager authenticationManager;

    public JdMallClientAdapter(JdMallProperties properties, MallAuthenticationManager authenticationManager) {
        this.properties = properties;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public <T extends AbstractResponse> T execute(JdMallRequest<T> request) {
        JdRequest<T> jdRequest = request.getJdRequest();

        try {
            JdClient jdClient = createJdClient(request.getTaxpayerId());

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
     * @param taxpayerId 纳税人识别号
     * @return JdClient
     */
    private JdClient createJdClient(String taxpayerId) {
        JdMallMallAuthentication authentication = (JdMallMallAuthentication) authenticationManager.getAuthentication(MallType.JINGDONG, taxpayerId);
        return new DefaultJdClient(properties.getServerUrl(), authentication.getAccessToken(), authentication.getAppKey(), authentication.getAppSecret());
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

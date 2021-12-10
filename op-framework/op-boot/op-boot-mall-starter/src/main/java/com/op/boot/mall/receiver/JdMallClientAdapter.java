package com.op.boot.mall.receiver;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.boot.mall.authentication.JdMallAuthentication;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.properties.JdMallProperties;
import com.op.boot.mall.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.Map;

/**
 * 京东电商命令接收者适配器类
 *
 * @author chengdr01
 */
@Slf4j
@ConditionalOnProperty(prefix = "mall.jd", value = "request-type", havingValue = "sdk", matchIfMissing = true)
@Component
public class JdMallClientAdapter implements JdMallClient {
    /**
     * 京东电商请求执行成功时的响应码
     */
    private static final String SUCCESS_CODE = "0";

    /**
     * JdClient 本地缓存
     */
    private static final Map<JdMallAuthentication, JdClient> JD_CLIENT_CACHE = new ConcurrentReferenceHashMap<>();

    private final JdMallProperties properties;

    public JdMallClientAdapter(JdMallProperties properties) {
        this.properties = properties;
    }

    @Override
    public <T extends AbstractResponse> T execute(JdMallRequest<T> request) {
        JdRequest<T> jdRequest = request.getJdRequest();

        try {
            JdClient jdClient = JD_CLIENT_CACHE.computeIfAbsent(request.getAuthentication(), this::createJdClient);

            T response = jdClient.execute(jdRequest);

            if (!SUCCESS_CODE.equals(response.getCode())) {
                log.error("调用京东接口【{}】失败，错误码【{}】，错误消息【{}】，请求参数【{}】，系统参数【{}】",
                        jdRequest.getApiMethod(), response.getCode(), response.getZhDesc(), JsonUtils.toString(jdRequest),
                        jdRequest.getSysParams());
                throw new JdMallException(response.getZhDesc(), response.getCode());
            }

            log.info("调用京东接口【{}】成功，请求参数【{}】，请求响应【{}】",
                    jdRequest.getApiMethod(), JsonUtils.toString(jdRequest), JsonUtils.toString(response));

            return response;
        } catch (Exception e) {
            log.error("调用京东接口【{}】失败，请求参数【{}】，系统参数【{}】",
                    jdRequest.getApiMethod(), JsonUtils.toString(jdRequest), jdRequest.getSysParams(), e);
            throw new JdMallException(e.getMessage(), "-1", e);
        }
    }

    /**
     * 创建 {@link JdClient}
     *
     * @param authentication 京东电商身份认证凭据
     * @return JdClient
     */
    private JdClient createJdClient(JdMallAuthentication authentication) {
        return new DefaultJdClient(properties.getServerUrl(),
                authentication.getAccessToken(),
                authentication.getAppKey(),
                authentication.getAppSecret(),
                properties.getConnectTimeout(),
                properties.getReadTimeout());
    }
}

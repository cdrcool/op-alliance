package com.op.framework.boot.sdk.client.base;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.internal.util.JsonUtil;
import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.framework.boot.sdk.client.SdkProperties;
import com.op.framework.boot.sdk.client.account.exception.ThirdAccountException;
import com.op.framework.boot.sdk.client.account.service.ThirdAccountService;
import com.op.framework.boot.sdk.client.exception.JdInvokeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Optional;

/**
 * 京东 SDK Client 适配类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdSdkClientAdapter implements JdSdkClient {
    private final SdkProperties sdkProperties;
    private final ThirdAccountService thirdAccountService;

    public JdSdkClientAdapter(SdkProperties sdkProperties, ThirdAccountService thirdAccountService) {
        this.sdkProperties = sdkProperties;
        this.thirdAccountService = thirdAccountService;
    }

    @Override
    public <T extends AbstractResponse> T execute(JdSdkRequest<T> jdSdkRequest) {
        String token = jdSdkRequest.getToken();
        JdRequest<T> jdRequest = jdSdkRequest.getJdRequest();

        try {
            T response = getJdClient(token).execute(jdRequest);
            log.info("调用京东接口【{}】成功，请求参数：【{}】，请求响应：【{}】",
                    jdRequest.getApiMethod(), serializeRequest(jdRequest), serializeResponse(response));
            return response;
        } catch (Exception e) {
            log.error("调用京东接口【{}】失败，请求参数：【{}】，系统参数：【{}】",
                    jdRequest.getApiMethod(), serializeRequest(jdRequest), jdRequest.getSysParams(), e);
            throw new JdInvokeException(String.format("调用京东接口【%s】失败", jdRequest.getApiMethod()), e);
        }
    }

    private JdClient getJdClient(String token) {
        SdkProperties.Account account = Optional.ofNullable(sdkProperties.getAccounts().get(AccountType.JD.getValue()))
                .orElseThrow(() -> new ThirdAccountException("未找到京东账号配置"));

        // 未传递token就取默认账号的token
        if (!StringUtils.hasText(token)) {
            token = thirdAccountService.getAccessToken(account.getAccount(), null);
        }

        return new DefaultJdClient(account.getServerUrl(), token, account.getAppKey(), account.getAppSecret());
    }

    private <T extends AbstractResponse> String serializeRequest(JdRequest<T> jdRequest) {
        String requestJson = null;
        try {
            requestJson = jdRequest.getAppJsonParams();
        } catch (IOException e) {
            log.error("序列化京东请求参数异常", e);
        }
        return requestJson;
    }

    private <T extends AbstractResponse> String serializeResponse(T response) {
        String responseJson = null;
        try {
            responseJson = JsonUtil.toJson(response);
        } catch (IOException e) {
            log.error("反序列化京东请求响应异常", e);
        }
        return responseJson;
    }
}

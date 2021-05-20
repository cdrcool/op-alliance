package com.op.framework.boot.sdk.client.base;

import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.internal.util.JsonUtil;
import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.framework.boot.sdk.client.exception.JdInvokeException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 京东 SDK Client 适配类
 *
 * @author cdrcool
 */
@Slf4j
public class JdSdkClientAdapter implements JdSdkClient {
    private final JdClient jdClient;

    public JdSdkClientAdapter(JdClient jdClient) {
        this.jdClient = jdClient;
    }

    @Override
    public <T extends AbstractResponse> T execute(JdRequest<T> request) {
        try {
            T response = jdClient.execute(request);
            log.info("调用京东接口【{}】成功，请求参数：【{}】，请求响应：【{}】",
                    request.getApiMethod(), serializeRequest(request), serializeResponse(response));
            return response;
        } catch (Exception e) {
            log.error("调用京东接口【{}】失败，请求参数：【{}】，系统参数：【{}】",
                    request.getApiMethod(), serializeRequest(request), request.getSysParams(), e);
            throw new JdInvokeException(String.format("调用京东接口【%s】失败", request.getApiMethod()), e);
        }
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

package com.op.framework.boot.sdk.client.base;

import com.op.framework.boot.sdk.client.exception.JdInvokeException;
import com.suning.api.DefaultSuningClient;
import com.suning.api.SuningRequest;
import com.suning.api.SuningResponse;
import com.suning.api.exception.SuningApiException;
import com.suning.api.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 苏宁 SDK Client 适配类
 *
 * @author cdrcool
 */
@Slf4j
public class SnSdkClientAdapter implements SnSdkClient {
    private final DefaultSuningClient snClient;

    public SnSdkClientAdapter(DefaultSuningClient snClient) {
        this.snClient = snClient;
    }

    @Override
    public <T extends SuningResponse> T execute(SuningRequest<T> request) {
        try {
            T response = snClient.excute(request);
            log.info("调用苏宁接口【{}】成功，请求参数：【{}】，请求响应：【{}】",
                    request.getAppMethod(), serializeRequest(request), serializeResponse(response));
            return response;
        } catch (Exception e) {
            log.error("调用苏宁接口【{}】失败，请求参数：【{}】，系统参数：【{}】",
                    request.getAppMethod(), serializeRequest(request), request.getSysParams(), e);
            throw new JdInvokeException(String.format("调用苏宁接口【%s】失败", request.getAppMethod()), e);
        }
    }

    private <T extends SuningResponse> String serializeRequest(SuningRequest<T> request) {
        String requestJson = null;
        try {
            requestJson = request.getResParams();
        } catch (SuningApiException e) {
            log.error("序列化苏宁请求参数异常", e);
        }
        return requestJson;
    }

    private <T extends SuningResponse> String serializeResponse(T response) {
        String responseJson = null;
        try {
            responseJson = JsonUtil.mapToJson(response);
        } catch (SuningApiException e) {
            log.error("反序列化苏宁请求响应异常", e);
        }
        return responseJson;
    }
}

package com.op.framework.boot.sdk.client.base;

import com.op.framework.boot.sdk.client.SdkProperties;
import com.op.framework.boot.sdk.client.account.exception.ThirdAccountException;
import com.op.framework.boot.sdk.client.account.service.ThirdAccountService;
import com.op.framework.boot.sdk.client.exception.SnInvokeException;
import com.suning.api.DefaultSuningClient;
import com.suning.api.SuningRequest;
import com.suning.api.SuningResponse;
import com.suning.api.exception.SuningApiException;
import com.suning.api.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * 苏宁 SDK Client 适配类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class SnSdkClientAdapter implements SnSdkClient {
    private final SdkProperties sdkProperties;
    private final ThirdAccountService thirdAccountService;

    public SnSdkClientAdapter(SdkProperties sdkProperties, @Qualifier("snAccountServiceImpl") ThirdAccountService thirdAccountService) {
        this.sdkProperties = sdkProperties;
        this.thirdAccountService = thirdAccountService;
    }

    /**
     * 增加重试机制
     * 默认发生任何异常时重试，最大重试3次
     * 设置重试间隔为1s
     */
    @Retryable(backoff = @Backoff(delay = 1000, multiplier = 0, maxDelay = 5000L))
    @Override
    public <T extends SuningResponse> T execute(SnSdkRequest<T> suSdkRequest) {
        String token = suSdkRequest.getToken();
        SuningRequest<T> snRequest = suSdkRequest.getSnRequest();

        try {
            T response = getSnClient(token).excute(snRequest);
            log.info("调用苏宁接口【{}】成功，请求参数：【{}】，请求响应：【{}】",
                    snRequest.getAppMethod(), serializeRequest(snRequest), serializeResponse(response));
            return response;
        } catch (Exception e) {
            log.error("调用苏宁接口【{}】失败，请求参数：【{}】，系统参数：【{}】",
                    snRequest.getAppMethod(), serializeRequest(snRequest), snRequest.getSysParams(), e);
            throw new SnInvokeException(String.format("调用苏宁接口【%s】失败", snRequest.getAppMethod()), e);
        }
    }

    private DefaultSuningClient getSnClient(String token) {
        SdkProperties.ThirdProperties thirdProperties = Optional.ofNullable(sdkProperties.getThird().get(ThirdSdkType.SN.getValue()))
                .orElseThrow(() -> new ThirdAccountException("未找到苏宁账号配置"));

        // 未传递token就取默认账号的token
        if (!StringUtils.hasText(token)) {
            token = thirdAccountService.getAccessToken(thirdProperties.getAccount(), null);
        }
        return new DefaultSuningClient(thirdProperties.getServerUrl(), token, thirdProperties.getAppKey(), thirdProperties.getAppSecret());
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

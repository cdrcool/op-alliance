package com.op.framework.boot.sdk.client.api.pro;

import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.framework.boot.sdk.client.api.pro.jd.JdRequestFunction;
import com.op.framework.boot.sdk.client.api.pro.jd.JdResponseFunction;
import com.op.framework.boot.sdk.client.api.pro.sn.*;
import com.op.framework.boot.sdk.client.base.JdSdkClient;
import com.op.framework.boot.sdk.client.base.JdSdkRequest;
import com.op.framework.boot.sdk.client.base.SnSdkClient;
import com.op.framework.boot.sdk.client.base.SnSdkRequest;
import com.op.framework.web.common.api.response.exception.BusinessException;
import com.suning.api.SuningRequest;
import com.suning.api.SuningResponse;

/**
 * SDK Client 默认实现类
 *
 * @author cdrcool
 */
public class DefaultSdkClient implements SdkClient {
    private final JdSdkClient jdSdkClient;
    private final SnSdkClient snSdkClient;
    private final SdkRequestFunctionFactory sdkRequestFunctionFactory;
    private final SdkResponseFunctionFactory sdkResponseFunctionFactory;

    public DefaultSdkClient(JdSdkClient jdSdkClient, SnSdkClient snSdkClient,
                            SdkRequestFunctionFactory sdkRequestFunctionFactory,
                            SdkResponseFunctionFactory sdkResponseFunctionFactory) {
        this.jdSdkClient = jdSdkClient;
        this.snSdkClient = snSdkClient;
        this.sdkRequestFunctionFactory = sdkRequestFunctionFactory;
        this.sdkResponseFunctionFactory = sdkResponseFunctionFactory;
    }

    @Override
    public <T extends BaseSdkResponse> T execute(SdkRequest<T> sdkRequest) {
        String actionName = sdkRequest.getActionName();
        switch (sdkRequest.getSdkType()) {
            case JD:
                // 1. 获取京东请求对象 function
                JdRequestFunction<T, ? extends AbstractResponse> jdRequestFunction = sdkRequestFunctionFactory.getJdRequestFunction(actionName);
                // 2. 根据京东请求对象 function 获取京东请求对象
                JdRequest<? extends AbstractResponse> jdRequest = jdRequestFunction.apply(sdkRequest);
                // 3. 调用京东接口
                AbstractResponse jdResponse = jdSdkClient.execute(new JdSdkRequest<>(sdkRequest.getToken(), jdRequest));
                // 4. 获取京东响应对象 function
                JdResponseFunction<AbstractResponse, T> jdResponseFunction = sdkResponseFunctionFactory.getJdResponseFunction(actionName);
                // 5. 根据京东响应对象 function 转换响应对象
                return jdResponseFunction.apply(jdResponse);
            case SN:
                // 1. 获取苏宁请求对象
                SnRequestFunction<T, ? extends SuningResponse> snRequestFunction = sdkRequestFunctionFactory.getSnRequestFunction(actionName);
                // 2. 根据 function 获取苏宁请求对象
                SuningRequest<? extends SuningResponse> snRequest = snRequestFunction.apply(sdkRequest);
                // 2. 调用苏宁接口
                SuningResponse snResponse = snSdkClient.execute(new SnSdkRequest<>(sdkRequest.getToken(), snRequest));
                // 4. 获取苏宁响应对象 function
                SnResponseFunction<SuningResponse, T> snResponseFunction = sdkResponseFunctionFactory.getSnResponseFunction(actionName);
                // 5. 根据苏宁响应对象 function 转换响应对象
                return snResponseFunction.apply(snResponse);
            default:
        }
        throw new BusinessException("不支持的SDK请求，请求参数：【" + sdkRequest + "】");
    }
}

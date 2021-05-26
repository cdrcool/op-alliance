package com.op.framework.boot.sdk.client.api.pro.function;

import com.op.framework.boot.sdk.client.api.pro.response.BaseSdkResponse;
import com.op.framework.boot.sdk.client.api.pro.SdkRequest;
import com.suning.api.SuningRequest;
import com.suning.api.SuningResponse;

import java.util.function.Function;

/**
 * 苏宁 SDK 请求对象 Function
 *
 * @author cdrcool
 */
public interface SnRequestFunction<T extends BaseSdkResponse, R extends SuningResponse> extends Function<SdkRequest<T>, SuningRequest<R>> {
}

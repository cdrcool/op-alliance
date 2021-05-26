package com.op.framework.boot.sdk.client.api.pro.function;

import com.op.framework.boot.sdk.client.api.pro.response.BaseSdkResponse;
import com.suning.api.SuningResponse;

import java.util.function.Function;

/**
 * 苏宁 SDK 请求对象 Function
 *
 * @author cdrcool
 */
public interface SnResponseFunction<T extends SuningResponse, R extends BaseSdkResponse> extends Function<T, R> {
}

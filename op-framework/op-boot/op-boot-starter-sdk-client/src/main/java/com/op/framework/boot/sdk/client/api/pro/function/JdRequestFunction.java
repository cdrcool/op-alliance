package com.op.framework.boot.sdk.client.api.pro.function;

import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.framework.boot.sdk.client.api.pro.response.BaseSdkResponse;
import com.op.framework.boot.sdk.client.api.pro.SdkRequest;

import java.util.function.Function;

/**
 * 京东 SDK 请求对象 Function
 *
 * @author cdrcool
 */
public interface JdRequestFunction<T extends BaseSdkResponse, R extends AbstractResponse> extends Function<SdkRequest<T>, JdRequest<R>> {
}

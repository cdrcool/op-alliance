package com.op.framework.boot.sdk.client.api.pro.jd;

import com.jd.open.api.sdk.response.AbstractResponse;
import com.op.framework.boot.sdk.client.api.pro.BaseSdkResponse;

import java.util.function.Function;

/**
 * 京东 SDK 响应对象 Function
 *
 * @author cdrcool
 */
public interface JdResponseFunction<T extends AbstractResponse, R extends BaseSdkResponse> extends Function<T, R> {
}

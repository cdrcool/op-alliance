package com.op.framework.boot.sdk.client.response;

import java.lang.annotation.*;

/**
 * 标识类或方法不进行统一包装
 *
 * @author cdrcool
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoApiResponse {
}

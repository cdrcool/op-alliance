package com.op.framework.web.common.api.response;

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

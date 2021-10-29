package com.op.boot.mall.properties;

import lombok.Getter;

/**
 * 请求类型枚举
 *
 * @author cdrcool
 */
@Getter
public enum RequestType {
    /**
     * sdk 方式调用
     */
    SDK("sdk"),

    /**
     * feign 方式调用
     */
    FEIGN("feign"),
    ;

    private final String value;

    RequestType(String value) {
        this.value = value;
    }
}

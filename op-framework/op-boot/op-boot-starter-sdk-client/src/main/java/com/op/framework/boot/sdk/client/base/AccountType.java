package com.op.framework.boot.sdk.client.base;

import lombok.Getter;

/**
 * 账号类型
 *
 * @author cdrcool
 */
@Getter
public enum AccountType {
    /**
     * 京东
     */
    JD("jd"),

    /**
     * 苏宁
     */
    SN("sn");

    /**
     * 值
     */
    private final String value;


    AccountType(String value) {
        this.value = value;
    }
}

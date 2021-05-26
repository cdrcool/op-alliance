package com.op.framework.boot.sdk.client.base;

import com.op.framework.web.common.api.response.exception.BusinessException;
import lombok.Getter;

import java.util.Arrays;

/**
 * 第三方 SDK 类型
 *
 * @author cdrcool
 */
@Getter
public enum ThirdSdkType {
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


    ThirdSdkType(String value) {
        this.value = value;
    }

    public static ThirdSdkType getByValue(String value) {
        return Arrays.stream(values()).filter(item -> item.getValue().equals(value)).findAny()
                .orElseThrow(() -> new BusinessException("不支持的第三方 SDK 类型：" + value));
    }
}

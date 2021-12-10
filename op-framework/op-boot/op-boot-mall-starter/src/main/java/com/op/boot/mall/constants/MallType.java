package com.op.boot.mall.constants;

import com.op.boot.mall.exception.MallException;
import lombok.Getter;

import java.util.Arrays;

/**
 * 电商类型枚举
 *
 * @author chengdr01
 */
@Getter
public enum MallType {
    /**
     * 京东
     */
    JD("jd", "京东"),

    /**
     * 京东金采
     */
    JD_BILL("jd_bill", "京东金采"),

    /**
     * 苏宁
     */
    SN("sn", "苏宁"),

    /**
     * 震坤行
     */
    ZKH("zkh", "震坤行"),
    ;

    /**
     * 值
     */
    private final String value;

    /**
     * 描述
     */
    private final String desc;

    MallType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举值
     *
     * @param value 值
     * @return 枚举值
     */
    public static MallType getByValue(String value) {
        return Arrays.stream(values()).filter(item -> item.value.equals(value)).findAny()
                .orElseThrow(() -> new MallException("不支持的电商类型【" + value + "】"));
    }
}

package com.op.mall.constans;

import com.op.mall.exception.MallException;
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
     * 京东电商
     */
    JINGDONG("jingdong", "京东"),

    /**
     * 苏宁电商
     */
    SUNING("suning", "苏宁"),

    ;

    /**
     * 值
     */
    private final String value;

    /**
     * 名称
     */
    private final String name;

    MallType(String value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 根据值获取枚举值
     *
     * @param value 值
     * @return 枚举值
     */
    public static MallType getMallType(String value) {
        return Arrays.stream(values()).filter(item -> item.value.equals(value)).findAny()
                .orElseThrow(() -> new MallException("不支持的电商类型枚举值：" + value));
    }
}

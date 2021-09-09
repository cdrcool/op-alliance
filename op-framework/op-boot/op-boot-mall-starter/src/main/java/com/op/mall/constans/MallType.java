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
    JINGDONG(1, "京东", "jd"),

    /**
     * 苏宁电商
     */
    SUNING(2, "苏宁", "sn"),

    ;

    /**
     * 值
     */
    private final Integer value;

    /**
     * 名称
     */
    private final String name;

    /**
     * 编码
     */
    private final String code;

    MallType(Integer value, String name, String code) {
        this.value = value;
        this.name = name;
        this.code = code;
    }

    /**
     * 根据值获取枚举值
     *
     * @param value 值
     * @return 枚举值
     */
    public static MallType getByValue(Integer value) {
        return Arrays.stream(values()).filter(item -> item.value.equals(value)).findAny()
                .orElseThrow(() -> new MallException("不支持的电商类型枚举值：" + value));
    }
}

package com.op.boot.mall.constans;

import com.op.boot.mall.exception.MallException;
import lombok.Getter;

import java.util.Arrays;

/**
 * 电商类型枚举
 *
 * @author cdrcool
 */
@Getter
public enum MallType {
    /**
     * 京东电商
     */
    JINGDONG("jd", "京东"),

    /**
     * 苏宁电商
     */
    SUNING("sn", "苏宁"),

    /**
     * 京东电商金采
     */
    JINGDONG_BILL("jd_bill", "京东金采"),

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

    /**
     * 根据来源类型获取枚举值（为兼容之前的枚举值）
     *
     * @param sourceType 来源类型
     * @return 枚举值
     */
    public static MallType getBySourceType(String sourceType) {
        if ("4".equals(sourceType)) {
            return MallType.JINGDONG;
        }
        if ("5".equals(sourceType)) {
            return MallType.SUNING;
        }
        throw new MallException("不支持的来源类型：" + sourceType);
    }

    /**
     * 根据来源类型获取枚举值（为兼容之前的枚举值）
     *
     * @param orderType 订单类型
     * @return 枚举值
     */
    public static MallType getByOrderType(String orderType) {
        if ("2".equals(orderType)) {
            return MallType.JINGDONG;
        }
        if ("3".equals(orderType)) {
            return MallType.SUNING;
        }
        throw new MallException("不支持的订单类型：" + orderType);
    }
}

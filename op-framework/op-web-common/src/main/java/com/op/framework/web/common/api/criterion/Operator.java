package com.op.framework.web.common.api.criterion;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

/**
 * 操作符枚举
 *
 * @author cdrcool
 */
@ApiModel(description = "操作符枚举")
@Getter
public enum Operator {
    /**
     * 等于
     */
    EQ("="),

    /**
     * 不等于
     */
    NE("!="),

    /**
     * 大于
     */
    GT(">"),

    /**
     * 小于
     */
    LT("<"),

    /**
     * 模糊查询
     */
    LIKE("like"),

    ;

    private final String value;

    Operator(String value) {
        this.value = value;
    }
}

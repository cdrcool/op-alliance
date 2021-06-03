package com.op.framework.web.common.api.criterion;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

/**
 * 逻辑操作符枚举
 *
 * @author cdrcool
 */
@ApiModel(description = "逻辑操作符枚举")
@Getter
public enum LogicOperator {
    /**
     * 逻辑与
     */
    AND("and"),

    /**
     * 逻辑或
     */
    OR("or"),

    ;

    private final String value;

    LogicOperator(String value) {
        this.value = value;
    }
}

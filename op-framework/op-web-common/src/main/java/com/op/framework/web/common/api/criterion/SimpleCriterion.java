package com.op.framework.web.common.api.criterion;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 简单查询条件
 *
 * @author cdrcool
 */
@ApiModel(description = "简单查询条件")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class SimpleCriterion extends AbstractCriterion {
    @ApiModelProperty("属性名")
    private String name;

    @ApiModelProperty("属性值")
    private Object value;

    @ApiModelProperty("操作符")
    private Operator op;
}

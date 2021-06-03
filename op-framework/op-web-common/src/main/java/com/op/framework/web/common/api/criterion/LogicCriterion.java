package com.op.framework.web.common.api.criterion;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 复合查询条件
 *
 * @author cdrcool
 */
@ApiModel(description = "复合查询条件")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class LogicCriterion extends AbstractCriterion {
    /**
     * 左查询条件
     */
    private Criterion left;

    /**
     * 右查询条件
     */
    private Criterion right;

    /**
     * 逻辑操作符
     */
    private LogicOperator op;
}

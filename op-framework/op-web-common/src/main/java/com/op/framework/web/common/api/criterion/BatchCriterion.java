package com.op.framework.web.common.api.criterion;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 批查询条件
 *
 * @author cdrcool
 */
@ApiModel(description = "批查询条件")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class BatchCriterion extends AbstractCriterion {
    @ApiModelProperty("简单查询条件列表")
    private List<SimpleCriterion> list;
}

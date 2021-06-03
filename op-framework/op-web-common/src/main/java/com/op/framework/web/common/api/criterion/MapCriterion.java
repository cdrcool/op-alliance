package com.op.framework.web.common.api.criterion;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Key-Value 查询条件
 *
 * @author cdrcool
 */
@ApiModel(description = "Key-Value 查询条件")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class MapCriterion extends AbstractCriterion {
    @ApiModelProperty("Key-Value 键值对")
    private Map<String, Object> map;
}

package com.op.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 下拉框选项 VO
 *
 * @author cdrcool
 */
@ApiModel(description = "下拉框选项 VO")
@Data
public class SelectOptionVO {
    @ApiModelProperty("标签")
    private String label;

    @ApiModelProperty("值")
    private Object value;
}

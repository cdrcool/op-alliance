package com.op.samples.business.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Example保存DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "Example保存DTO")
@Data
public class ExampleSaveDTO {
    @NotEmpty
    @ApiModelProperty(value = "主键", required = true)
    private String id;
    @NotEmpty
    @ApiModelProperty(value = "编号", required = true)
    private String sn;
    @NotEmpty
    @ApiModelProperty(value = "名称", required = true)
    private String name;
}

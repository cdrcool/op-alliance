package com.op.samples.concurrent.business.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Example查询DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "Example查询DTO")
@Data
public class ExampleQueryDTO {
    @ApiModelProperty("编号")
    private String sn;
    @ApiModelProperty("名称")
    private String name;
}

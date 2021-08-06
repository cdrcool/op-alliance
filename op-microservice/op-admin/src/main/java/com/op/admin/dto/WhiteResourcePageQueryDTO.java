package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 白名单资源分页查询 VO
 *
 * @author cdrcool
 */
@ApiModel(description = "白名单资源分页查询 VO")
@Data
public class WhiteResourcePageQueryDTO {

    @ApiModelProperty("关键字（根据资源名称、资源路径或资源描述模糊查询）")
    private String keyword;

    @ApiModelProperty("资源路径")
    private String resourcePath;

    @ApiModelProperty("资源描述")
    private String resourceDesc;

    @ApiModelProperty("启用状态（0-禁用；1-启用）")
    private Integer status;
}
package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 白名单资源保存 VO
 *
 * @author cdrcool
 */
@ApiModel(description = "白名单资源保存 VO")
@Data
public class WhiteResourceSaveDTO {
    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("资源名称")
    @NotEmpty
    private String resourceName;

    @ApiModelProperty("资源路径")
    @NotEmpty
    private String resourcePath;

    @ApiModelProperty("资源描述")
    private String resourceDesc;

    @ApiModelProperty(value = "启用状态（0-禁用；1-启用）", allowableValues = "0,1")
    @NotNull
    private Integer status;

    @ApiModelProperty("资源编号")
    private Integer resourceNo;
}
package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源保存 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "资源保存 DTO")
@Data
public class ResourceSaveDTO {
    @ApiModelProperty("资源id")
    private Integer id;

    @ApiModelProperty(value = "资源分类id", required = true)
    private Integer categoryId;

    @ApiModelProperty(value = "资源名称", required = true)
    private String resourceName;

    @ApiModelProperty(value = "资源路径", required = true)
    private String resourcePath;

    @ApiModelProperty("资源描述")
    private String resourceDesc;

    @ApiModelProperty("资源编号")
    private Integer resourceNo;
}

package com.op.admin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 资源
 *
 * @author cdrcool
 */
@ApiModel("资源")
@EqualsAndHashCode(callSuper = true)
@Data
public class Resource extends BaseEntity {
    @ApiModelProperty(value = "资源分类ID")
    @NotNull
    private Long categoryId;

    @ApiModelProperty(value = "资源名称")
    @NotEmpty
    private String resourceName;

    @ApiModelProperty(value = "资源URL")
    @NotEmpty
    private String resourceUrl;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "编号")
    private Integer indexNo;
}
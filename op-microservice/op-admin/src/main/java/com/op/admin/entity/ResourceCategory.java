package com.op.admin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 资源分类
 *
 * @author cdrcool
 */
@ApiModel("资源分类")
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceCategory extends BaseEntity {
    @ApiModelProperty(value = "上级分类id")
    private Long pid;

    @ApiModelProperty(value = "分类名称")
    @NotEmpty
    private String categoryName;

    @ApiModelProperty(value = "编号")
    private Integer indexNo;
}
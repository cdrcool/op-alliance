package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源分类分页查询 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "资源分类分页查询 DTO")
@Data
public class ResourceCategoryPageQueryDTO {
    @ApiModelProperty("关键字（根据分类名称或服务名称查询）")
    private String keyword;
}

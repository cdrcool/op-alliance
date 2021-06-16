package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源分类列表查询 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "资源分类列表查询 DTO")
@Data
public class ResourceCategoryListQueryDTO {
    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("搜索文本（根据分类名称查询）")
    private String searchText;
}

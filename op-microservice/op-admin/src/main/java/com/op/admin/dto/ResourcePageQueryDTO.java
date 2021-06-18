package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源分页查询 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "资源分页查询 DTO")
@Data
public class ResourcePageQueryDTO {
    @ApiModelProperty("搜索文本（根据资源名称或资源路径查询）")
    private String searchText;
}

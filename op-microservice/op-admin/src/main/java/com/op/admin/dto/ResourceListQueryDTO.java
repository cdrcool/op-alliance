package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源列表查询 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "资源列表查询 DTO")
@Data
public class ResourceListQueryDTO {
    @ApiModelProperty("资源名称")
    private String resourceName;

    @ApiModelProperty("搜索文本（根据资源名称或资源路径查询）")
    private String searchText;
}

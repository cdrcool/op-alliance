package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源动作分页查询 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "资源动作分页查询 DTO")
@Data
public class ResourceActionPageQueryDTO {
    @ApiModelProperty("搜索文本（根据动作名称或动作路径查询）")
    private String searchText;
}

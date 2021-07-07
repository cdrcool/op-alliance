package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 菜单列表查询 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "菜单列表查询 DTO")
@Data
public class MenuListQueryDTO {
    @ApiModelProperty("关键字（根据菜单名称、菜单编码或菜单路由查询）")
    private String keyword;
}

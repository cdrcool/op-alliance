package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 菜单树列表查询 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "菜单树列表查询 DTO")
@Data
public class MenuTreeListQueryDTO {
    @ApiModelProperty("关键字（根据菜单名称、菜单路径或权限标识查询）")
    private String keyword;

    @ApiModelProperty("菜单id（不返回该菜单及其子菜单列表）")
    private Integer id;
}

package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 菜单分配 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "菜单分配 DTO")
@Data
public class MenuAssignDTO {
    @ApiModelProperty(value = "组织/用户/角色/用户组id", required = true)
    private Integer id;

    @ApiModelProperty(value = "菜单ids", required = true)
    private List<Integer> menuIds;
}

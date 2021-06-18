package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色分配 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "角色分配 DTO")
@Data
public class RoleAssignDTO {
    @ApiModelProperty(value = "组织/用户/用户组id", required = true)
    private Integer id;

    @ApiModelProperty(value = "角色ids", required = true)
    private List<Integer> roleIds;
}

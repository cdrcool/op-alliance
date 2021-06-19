package com.op.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色分配 VO
 *
 * @author cdrcool
 */
@ApiModel(description = "角色分配 VO")
@Data
public class RoleAssignVO {
    @ApiModelProperty("角色id")
    private Integer id;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色编码")
    private String roleCode;
}

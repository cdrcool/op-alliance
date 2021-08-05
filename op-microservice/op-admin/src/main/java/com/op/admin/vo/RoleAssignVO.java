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

    @ApiModelProperty("角色描述")
    private String roleDesc;

    @ApiModelProperty("启用状态（0-禁用；1-启用）")
    private Integer status;

    @ApiModelProperty("是否选中")
    private Boolean checked;

    @ApiModelProperty("是否可以取消选中")
    private Boolean enableUncheck;
}

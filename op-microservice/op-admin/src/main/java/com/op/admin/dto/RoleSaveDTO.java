package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色保存 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "角色保存 DTO")
@Data
public class RoleSaveDTO {
    @ApiModelProperty("角色id")
    private Integer id;

    @ApiModelProperty(value = "角色名称", required = true)
    private String roleName;

    @ApiModelProperty(value = "角色编码", required = true)
    private String roleCode;

    @ApiModelProperty(value = "角色描述", required = true)
    private String roleDesc;

    @ApiModelProperty(value = "启用状态（0-禁用；1-启用）", allowableValues = "0, 1", required = true)
    private Integer status;

    @ApiModelProperty("角色编号")
    private Integer roleNo;
}

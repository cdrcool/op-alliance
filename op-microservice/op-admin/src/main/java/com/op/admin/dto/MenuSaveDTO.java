package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 菜单保存 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "菜单保存 DTO")
@Data
public class MenuSaveDTO {
    @ApiModelProperty("菜单id")
    private Integer id;

    @ApiModelProperty("上级菜单id")
    private Integer pid;

    @ApiModelProperty(value = "菜单名称", required = true)
    @NotEmpty
    private String menuName;

    @ApiModelProperty(value = "菜单图标", required = true)
    @NotEmpty
    private String menuIcon;

    @ApiModelProperty(value = "菜单路径", required = true)
    @NotEmpty
    private String menuPath;

    @ApiModelProperty(value = "是否显示（0-隐藏；1-显示）", allowableValues = "0,1", required = true)
    @NotNull
    private Boolean isShow;

    @ApiModelProperty("权限标识")
    private String permission;

    @ApiModelProperty("菜单编号")
    private Integer menuNo;
}

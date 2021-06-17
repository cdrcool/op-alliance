package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    private String menuName;

    @ApiModelProperty(value = "菜单编码", required = true)
    private String menuCode;

    @ApiModelProperty(value = "菜单图标", required = true)
    private String menuIcon;

    @ApiModelProperty(value = "菜单路由", required = true)
    private String menuRouting;

    @ApiModelProperty("菜单编号")
    private Integer menuNo;

    @ApiModelProperty(value = "是否目录", required = true)
    private Boolean isDirectory;

    @ApiModelProperty(value = "是否隐藏", required = true)
    private Boolean hidden;
}

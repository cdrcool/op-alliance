package com.op.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 菜单分配 VO
 *
 * @author cdrcool
 */
@ApiModel(description = "菜单分配 VO")
@Data
public class MenuAssignVO {
    @ApiModelProperty("菜单id")
    private Integer id;

    @ApiModelProperty("父菜单id")
    private Integer pid;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("菜单编号")
    private Integer menuNo;

    @ApiModelProperty("下级菜单列表")
    private List<MenuAssignVO> children;

    @ApiModelProperty("是否选中")
    private Boolean checked;

    @ApiModelProperty("是否可以取消选中")
    private Boolean enableUncheck;
}

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

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("菜单编码")
    private String menuCode;

    @ApiModelProperty("下级菜单列表")
    private List<MenuAssignVO> children;
}

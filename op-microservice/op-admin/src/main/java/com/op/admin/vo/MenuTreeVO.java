package com.op.admin.vo;

import com.op.framework.web.common.persistence.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 菜单树 VO
 *
 * @author cdrcool
 */
@ApiModel(description = "菜单 VO")
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuTreeVO extends BaseVo {
    @ApiModelProperty("菜单id")
    private Integer id;

    @ApiModelProperty("上级菜单id")
    private Integer pid;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("菜单图标")
    private String menuIcon;

    @ApiModelProperty("菜单路径")
    private String menuPath;

    @ApiModelProperty("是否显示")
    private Boolean isShow;

    @ApiModelProperty("权限标识")
    private String permission;

    @ApiModelProperty("菜单编号")
    private Integer menuNo;

    @ApiModelProperty("子菜单列表")
    private List<MenuTreeVO> children;
}

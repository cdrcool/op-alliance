package com.op.admin.vo;

import com.op.framework.web.common.persistence.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 角色树 VO
 *
 * @author cdrcool
 */
@ApiModel(description = "角色树 VO")
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleTreeVO extends BaseVo {
    @ApiModelProperty("角色id")
    private Integer id;

    @ApiModelProperty("父角色id")
    private Integer pid;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色编码")
    private String roleCode;

    @ApiModelProperty("角色描述")
    private String roleDesc;

    @ApiModelProperty("启用状态（0-禁用；1-启用）")
    private Integer status;

    @ApiModelProperty("角色编号")
    private Integer roleNo;

    @ApiModelProperty("分配了该角色的用户数量")
    private Integer userCount;

    @ApiModelProperty("子角色列表")
    private List<RoleTreeVO> children;
}

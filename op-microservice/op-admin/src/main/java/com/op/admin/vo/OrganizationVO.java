package com.op.admin.vo;

import com.op.framework.web.common.persistence.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 组织 VO
 *
 * @author cdrcool
 */
@ApiModel(description = "组织 VO")
@EqualsAndHashCode(callSuper = true)
@Data
public class OrganizationVO extends BaseVo {
    @ApiModelProperty("组织id")
    private Integer id;

    @ApiModelProperty("上级组织id")
    private Integer pid;

    @ApiModelProperty("组织名称")
    private String orgName;

    @ApiModelProperty("组织编码")
    private String orgCode;

    @ApiModelProperty("组织编码链（从根组织到当前组织的编码链，用于快速查询）")
    private String orgCodeLink;

    @ApiModelProperty("组织类型（1-集团；2-公司；3-分公司；4-项目部；5-部门）")
    private Integer orgType;

    @ApiModelProperty("上级组织名称")
    private String parentName;

    @ApiModelProperty("父节点ids")
    private List<Integer> parentIds;
}

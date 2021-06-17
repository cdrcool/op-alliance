package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织保存 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "组织保存 DTO")
@Data
public class OrganizationSaveDTO {
    @ApiModelProperty("组织id")
    private Integer id;

    @ApiModelProperty("上级组织id")
    private Integer pid;

    @ApiModelProperty(value = "组织名称", required = true)
    private String orgName;

    @ApiModelProperty(value = "组织编码", required = true)
    private String orgCode;

    @ApiModelProperty(value = "组织类型（1-集团；2-公司；3-部门）", allowableValues = "1, 2, 3", required = true)
    private Integer orgType;
}

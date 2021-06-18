package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private Integer pid;

    @ApiModelProperty(value = "组织名称", required = true)
    @NotEmpty
    private String orgName;

    @ApiModelProperty(value = "组织编码", required = true)
    @NotEmpty
    private String orgCode;

    @ApiModelProperty(value = "组织类型（1-集团；2-公司；3-分公司；4-项目部；5-部门）", allowableValues = "1,2,3,4,5", required = true)
    @NotNull
    private Integer orgType;
}

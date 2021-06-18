package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织列表查询 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "组织列表查询 DTO")
@Data
public class OrganizationListQueryDTO {
    @ApiModelProperty(value = "组织名称", required = true)
    private String orgName;

    @ApiModelProperty(value = "组织编码", required = true)
    private String orgCode;

    @ApiModelProperty("搜索文本（根据组织名称或组织编码查询）")
    private String searchText;
}

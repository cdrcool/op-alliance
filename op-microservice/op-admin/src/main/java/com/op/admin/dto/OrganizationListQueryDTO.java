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
    @ApiModelProperty("上级组织id")
    private Integer pid;

    @ApiModelProperty("关键字（根据组织名称或组织编码查询）")
    private String keyword;
}

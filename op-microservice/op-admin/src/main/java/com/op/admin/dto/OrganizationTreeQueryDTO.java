package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 组织树查询 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "组织树查询 DTO")
@Data
public class OrganizationTreeQueryDTO {
    @ApiModelProperty("上级组织id")
    private Integer pid;

    @ApiModelProperty("关键字（根据组织名称查询）")
    private String keyword;

    @ApiModelProperty("要过滤的组织id（不返回该组织及其子组织列表）")
    private Integer filteredId;

    @ApiModelProperty("要追加的组织ids（追加返回该组织及其上级组织列表）")
    private List<Integer> appendedIds;
}

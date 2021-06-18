package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
}

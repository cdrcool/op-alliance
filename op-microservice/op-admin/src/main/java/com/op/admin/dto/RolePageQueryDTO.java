package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色分页查询 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "角色分页查询 DTO")
@Data
public class RolePageQueryDTO {
    @ApiModelProperty(value = "启用状态（0-禁用；1-启用）", allowableValues = "0, 1")
    private List<Integer> status;

    @ApiModelProperty("关键字（根据角色名称或角色编码查询）")
    private String keyword;
}

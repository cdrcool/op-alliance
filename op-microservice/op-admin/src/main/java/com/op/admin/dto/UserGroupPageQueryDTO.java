package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户组分页查询 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "用户组分页查询 DTO")
@Data
public class UserGroupPageQueryDTO {
    @ApiModelProperty("用户组名称")
    private String groupName;

    @ApiModelProperty("关键字（根据用户组名称查询）")
    private String keyword;
}

package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户组列表查询 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "用户组列表查询 DTO")
@Data
public class UserGroupQueryDTO {
    @ApiModelProperty("用户组名称")
    private String groupName;

    @ApiModelProperty("搜索文本（根据用户组名称查询）")
    private String searchText;
}

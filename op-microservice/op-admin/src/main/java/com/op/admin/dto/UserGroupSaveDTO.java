package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户组保存 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "用户组保存 DTO")
@Data
public class UserGroupSaveDTO {
    @ApiModelProperty("用户组id")
    private Integer id;

    @ApiModelProperty(value = "用户组名称", required = true)
    private String groupName;

    @ApiModelProperty("用户组描述")
    private String groupDesc;

    @ApiModelProperty("用户编号")
    private Integer groupNo;

    @ApiModelProperty("用户 ids")
    private List<Integer> userIds;
}

package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户列表查询 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "用户列表查询 DTO")
@Data
public class UserListQueryDTO {
    @ApiModelProperty("组织id")
    private Integer orgId;

    @ApiModelProperty(value = "性别（1-男；2-女）", allowableValues = "1, 2")
    private Integer gender;

    @ApiModelProperty(value = "帐号状态列表（0-禁用；1-启用；2-过期；3-锁定；4-密码过期）", allowableValues = "0, 1, 2, 3, 4")
    private List<Integer> status;

    @ApiModelProperty("搜索文本（根据用户名、昵称、手机号或邮箱模糊查询）")
    private String searchText;
}

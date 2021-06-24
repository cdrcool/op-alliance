package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "用户 DTO")
@Data
public class UserDTO {
    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("帐号状态（0-禁用；1-启用；2-过期；3-锁定；4-密码过期）")
    private Integer status;

    @ApiModelProperty("权限列表")
    private List<String> permissions;
}

package com.op.admin.dto;

import com.op.framework.web.common.api.validation.annotation.PasswordMatch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 用户修改密码 DTO
 *
 * @author cdrcool
 */
@PasswordMatch(password = "password", confirmPassword = "confirmPassword")
@ApiModel(description = "用户修改密码 DTO")
@Data
public class UserChangePasswordDTO {
    @ApiModelProperty(value = "用户id", required = true)
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "旧密码", required = true)
    @NotEmpty
    private String oldPassword;

    @ApiModelProperty(value = "新密码", required = true)
    @NotEmpty
    private String password;

    @ApiModelProperty(value = "新密码确认", required = true)
    @NotEmpty
    private String confirmPassword;
}

package com.op.authorization.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Oauth2 Token 请求对象
 *
 * @author cdrcool
 */
@ApiModel("Oauth2 Token 请求对象")
@Data
public class OauthTokenRequest {
    @ApiModelProperty("客户端标识")
    @NotEmpty
    private String clientId;

    @ApiModelProperty("用户名")
    @NotEmpty
    private String username;

    @ApiModelProperty("密码")
    @NotEmpty
    private String password;
}

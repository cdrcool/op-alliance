package com.op.authorization.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Oauth2 Token 请求响应对象
 *
 * @author cdrcool
 */
@ApiModel("Oauth2 Token 请求响应对象")
@Builder
@Data
public class OauthTokenResponse {
    @ApiModelProperty("访问令牌")
    private String accessToken;

    @ApiModelProperty("令牌类型")
    private String tokenType;

    @ApiModelProperty("刷新令牌")
    private String refreshToken;

    @ApiModelProperty("有效时间（秒）")
    private Integer expiresIn;
}

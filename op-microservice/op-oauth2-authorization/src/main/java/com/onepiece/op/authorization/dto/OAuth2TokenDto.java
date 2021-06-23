package com.onepiece.op.authorization.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Oauth2 Token Dto
 */
@ApiModel("Oauth2 Token Dto")
@Builder
@Data
public class OAuth2TokenDto {
    @ApiModelProperty("访问令牌")
    private String accessToken;

    @ApiModelProperty("令牌类型")
    private String tokenType;

    @ApiModelProperty("刷新令牌")
    private String refreshToken;

    @ApiModelProperty("有效时间（秒）")
    private int expiresIn;
}

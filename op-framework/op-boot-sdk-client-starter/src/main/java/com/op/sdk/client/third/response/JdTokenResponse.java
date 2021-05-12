package com.op.sdk.client.third.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 京东token响应
 *
 * @author cdrcool
 */
@ApiModel(description = "京东token响应")
@Data
public class JdTokenResponse {
    @ApiModelProperty("业务id")
    private String uid;

    @ApiModelProperty("访问令牌, 用于业务接口调用. 有效期24小时")
    @JsonProperty("access_token")
    private String accessToken;

    @ApiModelProperty("当access_token过期时, 用于刷新access_token")
    @JsonProperty("refresh_token")
    private String refreshToken;

    @ApiModelProperty("当前时间, 时间戳格式: 1551663377887 ")
    private Long time;

    @ApiModelProperty("access_token的有效期, 单位: 秒, 有效期24小时")
    @JsonProperty("expires_in")
    private Integer expiresIn;

    @ApiModelProperty("refresh_token的过期时间, 毫秒级别, 时间戳")
    @JsonProperty("refresh_token_expires")
    private Long refreshTokenExpires;

    @ApiModelProperty("返回码")
    private Integer code;
}

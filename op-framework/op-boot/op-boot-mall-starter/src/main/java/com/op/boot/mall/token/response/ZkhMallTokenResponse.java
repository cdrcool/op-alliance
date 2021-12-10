package com.op.boot.mall.token.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 震坤行电商 Token 响应
 *
 * @author chengdr01
 */
@Data
public class ZkhMallTokenResponse {
    /**
     * 访问令牌
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 刷新令牌
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * 当前时间
     */
    private Long time;

    /**
     * 访问令牌过期时间，单位：秒
     */
    @JsonProperty("expires_in")
    private Integer expiresIn;

    /**
     * 刷新令牌过期时间，单位：秒
     */
    @JsonProperty("refresh_token_expires")
    private Long refreshTokenExpires;

    /**
     * 返回码
     */
    private String code;
}

package com.op.framework.boot.sdk.client.response;

import lombok.Data;

/**
 * 京东 Token 响应
 *
 * @author chengdr01
 */
@Data
public class JdTokenResponse {
    /**
     * 业务id
     */
    private String uid;

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 当前时间
     */
    private Long time;

    /**
     * 访问令牌过期时间，单位：秒
     */
    private Integer expiresIn;

    /**
     * 刷新令牌过期时间，单位：秒
     */
    private Long refreshTokenExpires;

    /**
     * 返回码
     */
    private Integer code;
}

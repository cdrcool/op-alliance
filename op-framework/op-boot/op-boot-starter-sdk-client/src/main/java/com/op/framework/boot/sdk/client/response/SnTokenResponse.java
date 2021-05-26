package com.op.framework.boot.sdk.client.response;

import lombok.Data;

/**
 * 苏宁 Token 响应
 *
 * @author chengdr01
 */
@Data
public class SnTokenResponse {
    /**
     * 令牌类型
     */
    private String tokenType;

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
    private Long reExpiresIn;

    /**
     * 用户名
     * 便于将访问令牌与用户名进行绑定
     */
    private String suningUserName;

    /**
     * 用户会员编码
     */
    private String custnum;

    /**
     * 用户类型
     */
    private String module;

    /**
     * 商家编码
     */
    private String vendorCode;
}

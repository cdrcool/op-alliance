package com.op.boot.mall.account;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 电商账号
 *
 * @author chengdr01
 */
@Data
public class Account {
    /**
     * 主键
     */
    private Long id;

    /**
     * 账号名
     */
    private String accountName;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号类型（jd-京东；sn-苏宁）
     */
    private String accountType;

    /**
     * 账号 APP Key
     */
    private String clientId;

    /**
     * 账号 APP Secret
     */
    private String clientSecret;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 是否启用（1-启用；0-停用）
     */
    private Integer isEnabled;

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 访问令牌过期时间
     */
    private Long accessTokenExpiresAt;

    /**
     * 刷新令牌过期时间
     */
    private Long refreshTokenExpiresAt;

    /**
     * 访问令牌更新时间
     */
    private LocalDateTime accessTokenUpdateTime;

    /**
     * 刷新令牌更新时间
     */
    private LocalDateTime refreshTokenUpdateTime;
}

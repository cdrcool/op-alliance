package com.op.sdk.client.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 第三方帐号
 *
 * @author cdrcool
 */
@Data
@TableName("sdk_third_account")
public class ThirdAccount {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 账号类型
     *
     * @see AccountType
     */
    private Integer accountType;

    /**
     * 帐号名
     */
    private String account;

    /**
     * 帐号密码
     */
    private String password;

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

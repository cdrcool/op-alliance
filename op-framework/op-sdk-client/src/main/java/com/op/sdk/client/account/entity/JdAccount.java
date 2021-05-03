package com.op.sdk.client.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 京东帐号
 *
 * @author cdrcool
 */
@Data
@TableName("sdk_jd_account")
public class JdAccount {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

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
}

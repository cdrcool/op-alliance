package com.op.admin.entity;

import com.op.framework.web.common.persistence.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseEntity {
    /**
     * 主键
     */
    private Long id;

    /**
     * 组织id
     */
    private Long orgId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别（1-男；2-女）
     */
    private Integer gender;

    /**
     * 出生年月
     */
    private Date birthday;

    /**
     * 帐号状态（0-禁用；1-启用；2-过期；3-锁定；4-密码过期）
     */
    private Integer status;

    /**
     * 用户编号
     */
    private Integer userNo;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;
}
package com.op.admin.entity;

import com.op.framework.web.common.persistence.entity.BaseEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mybatis Generator
 * @date 2021/08/06 05:58
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseEntity {
    /**
     * 主键
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

    /**
     * 组织id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer orgId;

    /**
     * 用户名
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String username;

    /**
     * 密码
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String password;

    /**
     * 昵称
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String nickname;

    /**
     * 头像
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String avatar;

    /**
     * 个性签名
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String signature;

    /**
     * 手机号
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String phone;

    /**
     * 邮箱
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String email;

    /**
     * 性别（1-男；2-女）
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer gender;

    /**
     * 出生日期
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private LocalDate birthday;

    /**
     * 帐号状态（0-禁用；1-启用；2-过期；3-锁定；4-密码过期）
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer status;

    /**
     * 用户编号
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer userNo;

    /**
     * 最后登录时间
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private LocalDateTime lastLoginTime;
}
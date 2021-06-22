package com.op.admin.entity;

import com.op.framework.web.common.persistence.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

/**
 * @author Mybatis Generator
 * @date 2021/06/18 11:20
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Role extends BaseEntity {
    /**
     * 主键
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

    /**
     * 角色名称
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String roleName;

    /**
     * 角色编码
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String roleCode;

    /**
     * 角色描述
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String roleDesc;

    /**
     * 启用状态（0-禁用；1-启用）
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer status;

    /**
     * 角色编号
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer roleNo;

    /**
     * 分配了该角色的用户数量
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer userCount;
}
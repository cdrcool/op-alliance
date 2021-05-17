package com.op.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Role extends BaseEntity {
    /**
     * 主键
     */
    private Long id;

    /**
     * 父角色id
     */
    private Long pid;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 启用状态（0-禁用；1-启用）
     */
    private Integer status;

    /**
     * 角色编号
     */
    private Integer roleNo;

    /**
     * 分配了该角色的用户数量
     */
    private Integer userCount;
}
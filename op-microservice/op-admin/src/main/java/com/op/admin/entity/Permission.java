package com.op.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Permission extends BaseEntity {
    /**
     * 主键
     */
    private Long id;

    /**
     * 父权限id
     */
    private Long pid;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限类型（0-目录；1-菜单；2-按钮（接口绑定权限））
     */
    private Integer permissionType;

    /**
     * 前端图标
     */
    private String frontIcon;

    /**
     * 前端资源路径
     */
    private String frontUri;

    /**
     * 启用状态（0-禁用；1-启用）
     */
    private Integer status;

    /**
     * 权限编号
     */
    private Integer permissionNo;
}

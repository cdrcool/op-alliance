package com.op.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Menu extends BaseEntity {
    /**
     * 主键
     */
    private Long id;

    /**
     * 上级菜单id
     */
    private Long pid;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 菜单路由
     */
    private String menuRouting;

    /**
     * 菜单层级
     */
    private Integer menuLevel;

    /**
     * 菜单编号
     */
    private Integer menuNo;

    /**
     * 是否隐藏
     */
    private Boolean hidden;
}
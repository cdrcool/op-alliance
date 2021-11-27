package com.op.samples.tenant.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chengdr01
 */
@NoArgsConstructor
@Data
public class Menu {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 上级菜单id
     */
    private Integer pid;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 菜单路径
     */
    private String menuPath;

    /**
     * 是否显示
     */
    private Boolean isShow;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 菜单编号
     */
    private Integer menuNo;
}

package com.op.admin.entity;

import com.op.framework.web.common.persistence.entity.BaseEntity;
import javax.annotation.Generated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mybatis Generator
 * @date 2021/07/01 12:06
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Menu extends BaseEntity {
    /**
     * 主键
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

    /**
     * 上级菜单id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer pid;

    /**
     * 上级菜单ids
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String parentIds;

    /**
     * 菜单名称
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String menuName;

    /**
     * 菜单编码
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String menuCode;

    /**
     * 菜单图标
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String menuIcon;

    /**
     * 菜单路由
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String menuRoute;

    /**
     * 菜单层级
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer menuLevel;

    /**
     * 菜单编号
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer menuNo;

    /**
     * 是否目录
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Boolean isDirectory;

    /**
     * 是否隐藏
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Boolean isHidden;

    /**
     * 权限标识
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String permission;
}
package com.op.admin.entity;

import com.op.framework.web.common.persistence.entity.BaseEntity;
import javax.annotation.Generated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mybatis Generator
 * @date 2021/08/06 11:49
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
     * 菜单名称
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String menuName;

    /**
     * 菜单图标
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String menuIcon;

    /**
     * 菜单路径
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String menuPath;

    /**
     * 是否显示
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Boolean isShow;

    /**
     * 权限标识
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String permission;

    /**
     * 菜单编号
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer menuNo;
}
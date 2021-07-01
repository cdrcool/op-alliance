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
public class RoleMenuRelation extends BaseEntity {
    /**
     * 角色id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer roleId;

    /**
     * 菜单id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer menuId;
}
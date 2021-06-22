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
public class UserMenuRelation extends BaseEntity {
    /**
     * 用户id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer userId;

    /**
     * 菜单id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer menuId;
}
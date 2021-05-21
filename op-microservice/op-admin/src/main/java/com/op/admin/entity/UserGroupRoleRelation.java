package com.op.admin.entity;

import com.op.framework.web.common.persistence.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

/**
 * @author Mybatis Generator
 * @date 2021/05/19 11:58
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class UserGroupRoleRelation extends BaseEntity {
    /**
     * 用户组id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer groupId;

    /**
     * 角色id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer roleId;
}
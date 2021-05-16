package com.op.admin.entity.relation;

import lombok.Data;

/**
 * 用户-角色关联
 *
 * @author cdrcool
 */
@Data
public class UserRoleRelation {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;
}

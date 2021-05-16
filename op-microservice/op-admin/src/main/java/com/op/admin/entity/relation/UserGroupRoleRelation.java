package com.op.admin.entity.relation;

import lombok.Data;

/**
 * 用户组-角色关联
 *
 * @author cdrcool
 */
@Data
public class UserGroupRoleRelation {
    /**
     * 用户组id
     */
    private Long groupId;

    /**
     * 角色id
     */
    private Long roleId;
}

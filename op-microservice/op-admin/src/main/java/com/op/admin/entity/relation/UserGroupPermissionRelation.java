package com.op.admin.entity.relation;

import lombok.Data;

/**
 * 用户组-权限关联
 *
 * @author cdrcool
 */
@Data
public class UserGroupPermissionRelation {
    /**
     * 用户组id
     */
    private Long groupId;

    /**
     * 权限id
     */
    private Long permissionId;
}

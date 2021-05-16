package com.op.admin.entity.relation;

import lombok.Data;

/**
 * 用户-权限关联
 *
 * @author cdrcool
 */
@Data
public class UserPermissionRelation {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 权限id
     */
    private Long permissionId;
}

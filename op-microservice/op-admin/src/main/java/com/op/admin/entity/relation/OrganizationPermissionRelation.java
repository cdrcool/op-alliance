package com.op.admin.entity.relation;

import lombok.Data;

/**
 * 组织-角色关联
 *
 * @author cdrcool
 */
@Data
public class OrganizationPermissionRelation {
    /**
     * 组织id
     */
    private Long orgId;

    /**
     * 权限id
     */
    private Long permissionId;
}

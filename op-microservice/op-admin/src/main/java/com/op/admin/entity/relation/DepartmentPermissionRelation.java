package com.op.admin.entity.relation;

import lombok.Data;

/**
 * 部门-权限关联
 *
 * @author cdrcool
 */
@Data
public class DepartmentPermissionRelation {
    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 权限id
     */
    private Long permissionId;
}

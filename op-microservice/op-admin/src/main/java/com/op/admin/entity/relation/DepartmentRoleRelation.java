package com.op.admin.entity.relation;

import lombok.Data;

/**
 * 部门-角色关联
 *
 * @author cdrcool
 */
@Data
public class DepartmentRoleRelation {
    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 角色id
     */
    private Long roleId;
}

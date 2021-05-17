package com.op.admin.entity.relation;

import lombok.Data;

/**
 * 组织-角色关联
 *
 * @author cdrcool
 */
@Data
public class OrganizationRoleRelation {
    /**
     * 组织id
     */
    private Long orgId;

    /**
     * 角色id
     */
    private Long roleId;
}

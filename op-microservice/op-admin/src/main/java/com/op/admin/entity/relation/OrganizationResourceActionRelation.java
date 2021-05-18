package com.op.admin.entity.relation;

import lombok.Data;

/**
 * 组织-资源动作关联
 *
 * @author cdrcool
 */
@Data
public class OrganizationResourceActionRelation {
    /**
     * 组织id
     */
    private Long orgId;

    /**
     * 资源动作id
     */
    private Long actionId;
}

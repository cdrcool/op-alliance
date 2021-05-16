package com.op.admin.entity.relation;

import lombok.Data;

/**
 * 公司-角色关联
 *
 * @author cdrcool
 */
@Data
public class CompanyPermissionRelation {
    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 权限id
     */
    private Long permissionId;
}

package com.op.admin.entity.relation;

import lombok.Data;

/**
 * 用户组-资源动作关联
 *
 * @author cdrcool
 */
@Data
public class UserGroupResourceActionRelation {
    /**
     * 用户组id
     */
    private Long groupId;

    /**
     * 资源动作id
     */
    private Long actionId;
}

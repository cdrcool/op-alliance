package com.op.admin.entity.relation;

import lombok.Data;

/**
 * 用户-资源动作关联
 *
 * @author cdrcool
 */
@Data
public class UserResourceActionRelation {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 资源动作id
     */
    private Long actionId;
}

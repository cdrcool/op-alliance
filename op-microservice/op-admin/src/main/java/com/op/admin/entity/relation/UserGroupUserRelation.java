package com.op.admin.entity.relation;

import lombok.Data;

/**
 * 用户组-用户关联
 *
 * @author cdrcool
 */
@Data
public class UserGroupUserRelation {
    /**
     * 用户组id
     */
    private Long groupId;

    /**
     * 用户id
     */
    private Long userId;
}

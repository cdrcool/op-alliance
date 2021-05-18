package com.op.admin.entity;

import com.op.framework.web.common.persistence.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户组
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserGroup extends BaseEntity {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户组名称
     */
    private String groupName;

    /**
     * 用户组描述
     */
    private String groupDesc;

    /**
     * 用户组编号
     */
    private Integer groupNo;
}

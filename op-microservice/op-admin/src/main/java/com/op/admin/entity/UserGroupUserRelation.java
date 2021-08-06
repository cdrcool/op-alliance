package com.op.admin.entity;

import com.op.framework.web.common.persistence.entity.BaseEntity;
import javax.annotation.Generated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mybatis Generator
 * @date 2021/08/06 05:58
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class UserGroupUserRelation extends BaseEntity {
    /**
     * 用户组id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer groupId;

    /**
     * 用户id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer userId;
}
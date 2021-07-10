package com.op.admin.entity;

import com.op.framework.web.common.persistence.entity.BaseEntity;
import javax.annotation.Generated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mybatis Generator
 * @date 2021/07/10 11:11
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleResourceActionRelation extends BaseEntity {
    /**
     * 角色id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer roleId;

    /**
     * 资源动作id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer actionId;
}
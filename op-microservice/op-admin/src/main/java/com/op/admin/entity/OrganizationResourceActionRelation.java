package com.op.admin.entity;

import com.op.framework.web.common.persistence.BaseEntity;
import javax.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mybatis Generator
 * @date 2021/05/19 09:47
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class OrganizationResourceActionRelation extends BaseEntity {
    /**
     * 组织id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer orgId;

    /**
     * 资源动作id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer actionId;
}
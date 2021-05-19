package com.op.admin.entity;

import com.op.framework.web.common.persistence.entity.BaseEntity;
import javax.annotation.Generated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mybatis Generator
 * @date 2021/05/19 11:58
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class UserResourceActionRelation extends BaseEntity {
    /**
     * 用户id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer userId;

    /**
     * 资源动作id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer actionId;
}
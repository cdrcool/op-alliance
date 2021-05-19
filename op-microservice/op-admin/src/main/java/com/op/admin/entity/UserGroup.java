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
public class UserGroup extends BaseEntity {
    /**
     * 主键
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

    /**
     * 用户组名称
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String groupName;

    /**
     * 用户组描述
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String groupDesc;

    /**
     * 用户编号
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer groupNo;
}
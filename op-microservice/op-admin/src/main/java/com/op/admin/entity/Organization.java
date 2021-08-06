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
public class Organization extends BaseEntity {
    /**
     * 主键
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

    /**
     * 上级组织id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer pid;

    /**
     * 组织名称
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String orgName;

    /**
     * 组织编码
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String orgCode;

    /**
     * 组织编码链（从根组织到当前组织的编码链，用于快速查询）
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String orgCodeLink;

    /**
     * 组织类型（1-集团；2-公司；3-分公司；4-项目部；5-部门）
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer orgType;
}
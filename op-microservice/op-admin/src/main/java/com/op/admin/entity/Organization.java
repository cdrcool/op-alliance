package com.op.admin.entity;

import com.op.framework.web.common.persistence.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 组织
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Organization extends BaseEntity {
    /**
     * 主键
     */
    private Long id;

    /**
     * 上级组织id
     */
    private Long pid;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 组织编码
     */
    private String orgCode;

    /**
     * 组织编码链（从根组织到当前组织的编码链，用于快速查询）
     */
    private String orgCodeLink;

    /**
     * 组织类型（1-集团；2-公司；3-部门）
     */
    private Integer orgType;
}

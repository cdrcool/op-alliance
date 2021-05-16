package com.op.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公司
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Company extends BaseEntity {
    /**
     * 主键
     */
    private Long id;

    /**
     * 上级公司id
     */
    private Long pid;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司编码
     */
    private String companyCode;

    /**
     * 公司编码链（从根公司到当前公司的编码链，用于快速查询）
     */
    private String companyCodeLink;

    /**
     * 公司类型
     */
    private Integer companyType;
}

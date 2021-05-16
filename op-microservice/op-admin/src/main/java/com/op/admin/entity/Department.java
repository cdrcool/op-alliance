package com.op.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Department extends BaseEntity {
    /**
     * 主键
     */
    private Long id;

    /**
     * 上级部门id
     */
    private Long pid;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 部门编码链（从根部门到当前部门的编码链，用于快速查询）
     */
    private String deptCodeLink;

    /**
     * 部门类型
     */
    private Integer deptType;
}

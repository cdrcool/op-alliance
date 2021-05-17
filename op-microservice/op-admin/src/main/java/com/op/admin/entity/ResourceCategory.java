package com.op.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资源分类
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceCategory extends BaseEntity {
    /**
     * 主键
     */
    private Long id;

    /**
     * 上级分类id
     */
    private Long pid;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类编号
     */
    private Integer categoryNo;
}
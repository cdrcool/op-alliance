package com.op.admin.entity;

import com.op.framework.web.common.persistence.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资源
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Resource extends BaseEntity {
    /**
     * 主键
     */
    private Long id;

    /**
     * 所属资源分类id
     */
    private Long categoryId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源路径
     */
    private String resourcePath;

    /**
     * 资源描述
     */
    private String resourceDesc;

    /**
     * 资源编号
     */
    private Integer resourceNo;
}
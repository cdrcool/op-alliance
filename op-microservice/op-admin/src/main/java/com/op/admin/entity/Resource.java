package com.op.admin.entity;

import com.op.framework.web.common.persistence.entity.BaseEntity;
import javax.annotation.Generated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mybatis Generator
 * @date 2021/07/12 01:38
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Resource extends BaseEntity {
    /**
     * 主键
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

    /**
     * 资源分类id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer categoryId;

    /**
     * 资源名称
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String resourceName;

    /**
     * 资源路径
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String resourcePath;

    /**
     * 资源描述
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String resourceDesc;

    /**
     * 资源编号
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer resourceNo;
}
package com.op.admin.entity;

import com.op.framework.web.common.persistence.entity.BaseEntity;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mybatis Generator
 * @date 2021/06/24 03:12
 */
@Builder
@AllArgsConstructor
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

    /**
     * 版本号
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer version;

    /**
     * 是否逻辑删除
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Boolean deleted;

    /**
     * 创建人id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer creatorId;

    /**
     * 创建时间
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private LocalDateTime createTime;

    /**
     * 最后修改人id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer lastModifierId;

    /**
     * 最后修改时间
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private LocalDateTime lastModifyTime;

    /**
     * 租户id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String tenantId;
}
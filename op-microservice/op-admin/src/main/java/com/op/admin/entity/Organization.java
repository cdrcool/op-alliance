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
 * @date 2021/06/17 05:16
 */
@Builder
@AllArgsConstructor
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
     * 组织类型（1-集团；2-公司；3-部门）
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer orgType;

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
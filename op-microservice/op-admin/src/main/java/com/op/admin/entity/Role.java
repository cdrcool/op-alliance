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
public class Role extends BaseEntity {
    /**
     * 主键
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

    /**
     * 角色名称
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String roleName;

    /**
     * 角色编码
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String roleCode;

    /**
     * 角色描述
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String roleDesc;

    /**
     * 启用状态（0-禁用；1-启用）
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer status;

    /**
     * 角色编号
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer roleNo;

    /**
     * 分配了该角色的用户数量
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer userCount;

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
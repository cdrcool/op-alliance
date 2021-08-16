package com.op.admin.entity;

import com.op.framework.web.common.persistence.entity.BaseEntity;
import javax.annotation.Generated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mybatis Generator
 * @date 2021/08/16 02:00
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class WhiteResource extends BaseEntity {
    /**
     * 主键
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

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
     * 是否移除身份认证信息
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Boolean removeAuthorization;

    /**
     * 启用状态（0-禁用；1-启用）
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer status;

    /**
     * 资源编号
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer resourceNo;
}
package com.op.admin.entity;

import com.op.framework.web.common.persistence.entity.BaseEntity;
import javax.annotation.Generated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mybatis Generator
 * @date 2021/08/12 05:32
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class OauthClientDetails extends BaseEntity {
    /**
     * 主键
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

    /**
     * 客户端标识
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String clientId;

    /**
     * 客户端密钥
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String clientSecret;

    /**
     * 授权许可类型
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String authorizedGrantTypes;

    /**
     * 授权范围
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String scope;

    /**
     * 重定向地址
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String webServerRedirectUri;

    /**
     * 权限（客户端/隐式模式需要配置）
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String authorities;

    /**
     * 资源ids
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String resourceIds;

    /**
     * 访问令牌有效期
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效期
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer refreshTokenValidity;

    /**
     * 是否自动授权（只适用于授权码模式，可选值：true|false|read|write）
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String autoapprove;

    /**
     * 其它信息
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String additionalInformation;
}
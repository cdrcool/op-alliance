package com.op.admin.entity;

import com.op.framework.web.common.persistence.BaseEntity;
import javax.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mybatis Generator
 * @date 2021/05/19 09:47
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceAction extends BaseEntity {
    /**
     * 主键
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

    /**
     * 资源id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer resourceId;

    /**
     * 动作名称
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String actionName;

    /**
     * 动作路径
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String actionPath;

    /**
     * 动作描述
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String actionDesc;

    /**
     * 动作编号
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer actionNo;
}
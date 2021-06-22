package com.op.admin.entity;

import com.op.framework.web.common.persistence.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

/**
 * @author Mybatis Generator
 * @date 2021/06/18 11:20
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceCategory extends BaseEntity {
    /**
     * 主键
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

    /**
     * 分类名称
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String categoryName;

    /**
     * 服务名称
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String serverName;

    /**
     * 分类编号
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer categoryNo;
}
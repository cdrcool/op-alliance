package com.op.admin.entity;

import com.op.framework.web.common.persistence.entity.BaseEntity;
import javax.annotation.Generated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mybatis Generator
 * @date 2021/05/19 11:58
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
     * 上级分类id
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer pid;

    /**
     * 分类名称
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String categoryName;

    /**
     * 分类编号
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer categoryNo;
}
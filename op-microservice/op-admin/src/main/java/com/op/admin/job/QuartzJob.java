package com.op.admin.job;

import com.op.framework.web.common.persistence.entity.BaseEntity;
import javax.annotation.Generated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mybatis Generator
 * @date 2021/08/16 03:51
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class QuartzJob extends BaseEntity {
    /**
     * 主键
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

    /**
     * 任务标识
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String jobId;

    /**
     * 任务名称
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String jobName;

    /**
     * 任务执行类
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String jobClass;

    /**
     * cron表达式
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String cronExps;

    /**
     * 启用状态（0-暂停；1-正常运行）
     */
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer status;
}

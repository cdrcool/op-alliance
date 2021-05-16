package com.op.admin.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 实体基类
 *
 * @author cdrcool
 */
@Data
public class BaseEntity implements Serializable {
    /**
     * 版本号
     */
    private Integer version;

    /**
     * 是否已删除
     */
    private Boolean deleted;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 创建时间
     */
    private LocalDate createTime;

    /**
     * 最后修改人id
     */
    private Long lastModifierId;

    /**
     * 最后修改时间
     */
    private LocalDate lastModifyTime;

    /**
     * 租户id
     */
    private Long tenantId;
}

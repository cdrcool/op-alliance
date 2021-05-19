package com.op.framework.web.common.persistence;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体基类
 *
 * @author cdrcool
 */
@Data
public abstract class BaseEntity implements Serializable {
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
    private Integer creatorId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后修改人id
     */
    private Integer lastModifierId;

    /**
     * 最后修改时间
     */
    private LocalDateTime lastModifyTime;

    /**
     * 租户id
     */
    private String tenantId;
}

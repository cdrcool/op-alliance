package com.op.framework.web.common.persistence.entity;

import com.op.framework.web.common.persistence.audit.CreatedBy;
import com.op.framework.web.common.persistence.audit.CreatedDate;
import com.op.framework.web.common.persistence.audit.LastModifiedBy;
import com.op.framework.web.common.persistence.audit.LastModifiedDate;
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
    private Integer version = 0;

    /**
     * 是否已删除
     */
    private Boolean deleted = false;

    /**
     * 创建人id
     */
    @CreatedBy
    private Integer creatorId;

    /**
     * 创建时间
     */
    @CreatedDate
    private LocalDateTime createTime;

    /**
     * 最后修改人id
     */
    @LastModifiedBy
    private Integer lastModifierId;

    /**
     * 最后修改时间
     */
    @LastModifiedDate
    private LocalDateTime lastModifyTime;

    /**
     * 租户id
     */
    private String tenantId;
}

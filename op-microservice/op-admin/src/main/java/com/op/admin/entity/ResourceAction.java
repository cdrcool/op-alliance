package com.op.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资源动作
 *
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceAction extends BaseEntity {
    /**
     * 主键
     */
    private Long id;

    /**
     * 所属资源id
     */
    private Long resourceId;

    /**
     * 动作名称
     */
    private String actionName;

    /**
     * 动作路径
     */
    private String actionPath;

    /**
     * 动作描述
     */
    private String actionDesc;

    /**
     * 动作编号
     */
    private Integer actionNo;
}
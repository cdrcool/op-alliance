package com.op.framework.web.common.persistence.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * vo 基类
 *
 * @author cdrcool
 */
@ApiModel(description = "VO 基类")
@Data
public abstract class BaseVo implements Serializable {
    @ApiModelProperty("版本号")
    private Integer version;

    @ApiModelProperty("是否已删除")
    private Boolean deleted;

    @ApiModelProperty("创建人id")
    private Integer creatorId;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("最后修改人id")
    private Integer lastModifierId;

    @ApiModelProperty("最后修改时间")
    private LocalDateTime lastModifyTime;

    @ApiModelProperty("租户id")
    private String tenantId;
}

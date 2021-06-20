package com.op.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源动作分配 VO
 *
 * @author cdrcool
 */
@ApiModel(description = "资源动作分配 VO")
@Data
public class ResourceActionAssignVO {
    @ApiModelProperty("资源动作id")
    private Integer id;

    @ApiModelProperty("资源id")
    private Integer resourceId;

    @ApiModelProperty("动作名称")
    private String actionName;
}

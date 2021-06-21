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

    @ApiModelProperty("是否选中")
    private Boolean checked;

    @ApiModelProperty("是否可以取消选中")
    private Boolean enableUncheck;
}

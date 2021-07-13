package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源动作保存 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "资源动作保存 DTO")
@Data
public class ResourceActionSaveDTO {
    @ApiModelProperty("资源动作id")
    private Integer id;

    @ApiModelProperty(value = "资源id", required = true)
    private Integer resourceId;

    @ApiModelProperty(value = "动作名称", required = true)
    private String actionName;

    @ApiModelProperty(value = "动作路径", required = true)
    private String actionPath;

    @ApiModelProperty("动作描述")
    private String actionDesc;

    @ApiModelProperty("权限标识")
    private String permission;

    @ApiModelProperty("动作编号")
    private Integer actionNo;
}

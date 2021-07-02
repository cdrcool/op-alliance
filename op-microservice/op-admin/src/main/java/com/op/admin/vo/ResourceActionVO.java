package com.op.admin.vo;

import com.op.framework.web.common.persistence.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资源动作 VO
 *
 * @author cdrcool
 */
@ApiModel(description = "资源动作 VO")
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceActionVO extends BaseVo {
    @ApiModelProperty("资源动作id")
    private Integer id;

    @ApiModelProperty("资源id")
    private Integer resourceId;

    @ApiModelProperty("动作名称")
    private String actionName;

    @ApiModelProperty("动作路径")
    private String actionPath;

    @ApiModelProperty("动作描述")
    private String actionDesc;

    @ApiModelProperty("动作编号")
    private Integer actionNo;

    @ApiModelProperty("权限名")
    private String permission;
}

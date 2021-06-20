package com.op.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 资源分配 VO
 *
 * @author cdrcool
 */
@ApiModel(description = "资源分配 VO")
@Data
public class ResourceAssignVO {
    @ApiModelProperty("资源id")
    private Integer id;

    @ApiModelProperty("资源分类id")
    private Integer categoryId;

    @ApiModelProperty("资源名称")
    private String resourceName;

    @ApiModelProperty("资源动作列表")
    private List<ResourceActionAssignVO> actions;
}

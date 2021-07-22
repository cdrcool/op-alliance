package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 资源分配 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "资源分配 DTO")
@Data
public class ResourceAssignDTO {
    @ApiModelProperty(value = "组织/用户/角色/用户组id", required = true)
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "资源动作ids", required = true)
    private List<Integer> resourceActionIds;
}

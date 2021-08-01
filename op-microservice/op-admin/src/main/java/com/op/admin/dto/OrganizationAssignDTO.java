package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 组织授权 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "组织授权 DTO")
@Data
public class OrganizationAssignDTO {
    @ApiModelProperty(value = "用户id", required = true)
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "组织ids", required = true)
    private List<Integer> organizationIds;
}

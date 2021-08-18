package com.op.admin.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 菜单显示/隐藏 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "菜单显示/隐藏 DTO")
@Data
public class MenuChangeVisibilityDTO {
    @ApiModelProperty(value = "菜单 ids")
    @NotEmpty
    private List<Integer> ids;

    @ApiModelProperty(value = "是否显示（0-隐藏；1-显示）", allowableValues = "0,1")
    @NotNull
    private Boolean show;
}

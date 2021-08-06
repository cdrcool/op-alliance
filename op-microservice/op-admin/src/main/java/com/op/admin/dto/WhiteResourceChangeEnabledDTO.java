package com.op.admin.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 白名单资源启用/禁用 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "白名单资源启用/禁用 DTO")
@Data
public class WhiteResourceChangeEnabledDTO {
    @ApiModelProperty(value = "白名单资源 ids")
    @NotEmpty
    private List<Integer> ids;

    @ApiModelProperty(value = "是否启用（0-禁用；1-启用）", allowableValues = "0, 1")
    @NotNull
    private Boolean enable;
}

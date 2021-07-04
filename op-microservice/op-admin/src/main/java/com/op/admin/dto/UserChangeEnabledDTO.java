package com.op.admin.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户启用/禁用 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "用户启用/禁用 DTO")
@Data
public class UserChangeEnabledDTO {
    @ApiModelProperty(value = "用户 ids")
    @NotEmpty
    private List<Integer> ids;

    @ApiModelProperty(value = "是否（0-禁用；1-启用）", allowableValues = "0, 1")
    @NotNull
    private Boolean enable;
}

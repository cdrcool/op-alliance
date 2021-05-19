package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 用户创建 dto
 *
 * @author chengdr01
 */
@ApiModel(description = "用户创建 dto")
@Data
public class UserCreateDto {
    @ApiModelProperty(value = "组织id", required = true)
    @NotNull
    private Integer orgId;

    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty
    private String username;

    @ApiModelProperty("用户编号")
    private Integer userNo;
}

package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * oauth2-client 分页查询 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "oauth2-client 分页查询 DTO")
@Data
public class OauthClientDetailsPageQueryDTO {
    @ApiModelProperty("关键字（根据客户端标识查询）")
    private String keyword;
}

package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * oauth2-client保存 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "oauth2-client保存 DTO")
@Data
public class OauthClientDetailsSaveDTO {
    @ApiModelProperty("客户端id")
    private Integer id;

    @ApiModelProperty(value = "客户端标识", required = true)
    @NotEmpty
    private String clientId;

    @ApiModelProperty(value = "客户端密钥", required = true)
    @NotEmpty
    private String clientSecret;

    @ApiModelProperty(value = "授权许可类型", required = true)
    @NotEmpty
    private String authorizedGrantTypes;

    @ApiModelProperty("授权范围")
    private String scope;

    @ApiModelProperty("重定向地址")
    private String webServerRedirectUri;

    @ApiModelProperty("权限")
    private String authorities;

    @ApiModelProperty("资源ids")
    private String resourceIds;

    @ApiModelProperty(value = "访问令牌有效期")
    private Integer accessTokenValidity;

    @ApiModelProperty("刷新令牌有效期")
    private Integer refreshTokenValidity;

    @ApiModelProperty(value = "是否自动授权")
    private String autoapprove;

    @ApiModelProperty("其它信息")
    private String additionalInformation;
}

package com.op.admin.vo;

import com.op.framework.web.common.persistence.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * oauth2-client VO
 *
 * @author cdrcool
 */
@ApiModel(description = "oauth2-client VO")
@EqualsAndHashCode(callSuper = true)
@Data
public class OauthClientDetailsVO extends BaseVo {
    @ApiModelProperty("客户端id")
    private Integer id;

    @ApiModelProperty("客户端标识")
    private String clientId;

    @ApiModelProperty("客户端密钥")
    private String clientSecret;

    @ApiModelProperty("授权许可类型")
    private String authorizedGrantTypes;

    @ApiModelProperty("授权范围")
    private String scope;

    @ApiModelProperty("重定向地址")
    private String webServerRedirectUri;

    @ApiModelProperty("权限")
    private String authorities;

    @ApiModelProperty("资源ids")
    private String resourceIds;

    @ApiModelProperty("访问令牌有效期")
    private Integer accessTokenValidity;

    @ApiModelProperty("刷新令牌有效期")
    private Integer refreshTokenValidity;

    @ApiModelProperty("是否自动授权")
    private String autoapprove;

    @ApiModelProperty("其它信息")
    private String additionalInformation;
}

package com.op.admin.dto;

import com.op.framework.web.common.api.validation.annotation.FieldsMatch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * oauth2-client 修改秘钥 DTO
 *
 * @author cdrcool
 */
@FieldsMatch(first = "secret", second = "confirmSecret", message = "两次客户端秘钥不一致")
@ApiModel(description = "oauth2-client 修改秘钥 DTO")
@Data
public class OauthClientChangeSecretDTO {
    @ApiModelProperty(value = "客户端id", required = true)
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "客户端秘钥", required = true)
    @NotEmpty
    private String secret;

    @ApiModelProperty(value = "客户端秘钥确认", required = true)
    @NotEmpty
    private String confirmSecret;
}

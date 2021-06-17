package com.op.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 用户更新 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "用户更新 DTO")
@Data
public class UserUpdateDTO {
    @ApiModelProperty(value = "用户id", required = true)
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "组织id", required = true)
    @NotNull
    private Integer orgId;

    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty
    private String username;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("个性签名")
    private String signature;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    @Email
    private String email;

    @ApiModelProperty("性别（1-男；2-女）")
    private Integer gender;

    @ApiModelProperty("出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate birthday;

    @ApiModelProperty("用户编号")
    private Integer userNo;
}

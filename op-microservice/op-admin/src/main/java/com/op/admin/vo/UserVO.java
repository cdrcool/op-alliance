package com.op.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.op.framework.web.common.persistence.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户 VO
 *
 * @author cdrcool
 */
@ApiModel(description = "用户 VO")
@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends BaseVo {
    @ApiModelProperty("用户id")
    private Integer id;

    @ApiModelProperty("组织id")
    private Integer orgId;

    @ApiModelProperty("用户名")
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

    @ApiModelProperty("帐号状态（0-禁用；1-启用；2-过期；3-锁定；4-密码过期）")
    private Integer status;

    @ApiModelProperty("用户编号")
    private Integer userNo;

    @ApiModelProperty("最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty("组织ids")
    private List<Integer> organizationIds;
}

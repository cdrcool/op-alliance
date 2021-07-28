package com.op.admin.vo;

import com.op.framework.web.common.persistence.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户组 VO
 *
 * @author cdrcool
 */
@ApiModel(description = "用户组 VO")
@EqualsAndHashCode(callSuper = true)
@Data
public class UserGroupVO extends BaseVo {
    @ApiModelProperty("用户组id")
    private Integer id;

    @ApiModelProperty("用户组名称")
    private String groupName;

    @ApiModelProperty("用户组描述")
    private String groupDesc;

    @ApiModelProperty("用户编号")
    private Integer groupNo;

    @ApiModelProperty("用户列表")
    private List<UserVO> users;
}

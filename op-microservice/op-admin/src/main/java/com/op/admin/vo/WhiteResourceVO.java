package com.op.admin.vo;

import com.op.framework.web.common.persistence.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 白名单资源 VO
 *
 * @author cdrcool
 */
@ApiModel(description = "白名单资源 VO")
@EqualsAndHashCode(callSuper = true)
@Data
public class WhiteResourceVO extends BaseEntity {
    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("资源名称")
    private String resourceName;

    @ApiModelProperty("资源路径")
    private String resourcePath;

    @ApiModelProperty("资源描述")
    private String resourceDesc;

    @ApiModelProperty("启用状态（0-禁用；1-启用）")
    private Integer status;

    @ApiModelProperty("资源编号")
    private Integer resourceNo;
}
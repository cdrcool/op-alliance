package com.op.admin.vo;

import com.op.framework.web.common.persistence.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资源 VO
 *
 * @author cdrcool
 */
@ApiModel(description = "资源 VO")
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceVO extends BaseVo {
    @ApiModelProperty("资源id")
    private Integer id;

    @ApiModelProperty("资源分类id")
    private Integer categoryId;

    @ApiModelProperty("资源名称")
    private String resourceName;

    @ApiModelProperty("资源路径")
    private String resourcePath;

    @ApiModelProperty("资源描述")
    private String resourceDesc;

    @ApiModelProperty("资源编号")
    private Integer resourceNo;
}

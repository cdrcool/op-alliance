package com.op.admin.vo;

import com.op.framework.web.common.persistence.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资源分类 VO
 *
 * @author cdrcool
 */
@ApiModel(description = "资源分类 VO")
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceCategoryVO extends BaseVo {
    @ApiModelProperty("资源分类id")
    private Integer id;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("服务名称")
    private String serverName;

    @ApiModelProperty("分类编号")
    private Integer categoryNo;
}

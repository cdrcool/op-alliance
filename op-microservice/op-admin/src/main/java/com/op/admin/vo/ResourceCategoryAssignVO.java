package com.op.admin.vo;

import com.op.framework.web.common.persistence.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 资源分类分配 VO
 *
 * @author cdrcool
 */
@ApiModel(description = "资源分类分配 VO")
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceCategoryAssignVO extends BaseVo {
    @ApiModelProperty("资源分类id")
    private Integer id;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("资源列表")
    private List<ResourceAssignVO> resources;
}

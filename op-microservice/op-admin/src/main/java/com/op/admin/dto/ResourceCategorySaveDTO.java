package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源分类保存 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "资源分类保存 DTO")
@Data
public class ResourceCategorySaveDTO {
    @ApiModelProperty("资源分类id")
    private Integer id;

    @ApiModelProperty(value = "分类名称", required = true)
    private String categoryName;

    @ApiModelProperty(value = "服务名称", required = true)
    private String serverName;

    @ApiModelProperty("分类编号")
    private Integer categoryNo;
}

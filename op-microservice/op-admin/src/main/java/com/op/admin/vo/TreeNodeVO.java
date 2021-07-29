package com.op.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 树节点 VO
 *
 * @author cdrcool
 */
@ApiModel(description = "树节点 VO")
@Data
public class TreeNodeVO {
    @ApiModelProperty("名称")
    private String title;

    @ApiModelProperty("值")
    private Object value;

    @ApiModelProperty("子节点列表")
    private List<TreeNodeVO> children;
}

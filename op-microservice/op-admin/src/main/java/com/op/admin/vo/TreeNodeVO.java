package com.op.admin.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @ApiModelProperty("关键字")
    private String key;

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("父节点主键")
    @JsonProperty("pId")
    private Integer pid;

    @ApiModelProperty("名称")
    private String title;

    @ApiModelProperty("值")
    private Object value;

    @ApiModelProperty("是否叶子节点")
    private Boolean isLeaf;

    @ApiModelProperty("是否展开")
    private Boolean isExpand;

    @ApiModelProperty("子节点列表")
    private List<TreeNodeVO> children;
}
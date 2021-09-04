package com.op.samples.concurrent.business.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Example VO
 *
 * @author cdrcool
 */
@ApiModel(description = "Example VO")
@Data
public class ExampleVO {
    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("编号")
    private String sn;
    @ApiModelProperty("名称")
    private String name;
}

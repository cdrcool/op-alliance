package com.op.admin.job;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Quartz Job 分页查询 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "Quartz Job 分页查询 DTO")
@Data
public class QuartzJobPageQueryDTO {
    @ApiModelProperty(value = "启用状态（0-禁用；1-启用）", allowableValues = "0, 1")
    private Integer status;

    @ApiModelProperty("关键字（根据任务名称查询）")
    private String keyword;
}

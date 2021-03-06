package com.op.admin.job;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * QuartZ Job 保存 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "QuartZ Job 保存 DTO")
@Data
public class QuartzJobSaveDTO {
    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("任务标识")
    @NotEmpty
    private String jobId;

    @ApiModelProperty("任务名称")
    @NotEmpty
    private String jobName;

    @ApiModelProperty("任务执行类")
    @NotEmpty
    private String jobClass;

    @ApiModelProperty("cron表达式")
    @NotEmpty
    private String cronExps;

    @ApiModelProperty(value = "运行状态（0-已暂停；1-运行中）", allowableValues = "0,1", required = true)
    @NotNull
    private Integer status;
}

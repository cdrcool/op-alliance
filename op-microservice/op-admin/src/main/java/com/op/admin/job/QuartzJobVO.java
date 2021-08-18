package com.op.admin.job;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * QuartZ Job VO
 *
 * @author cdrcool
 */
@ApiModel(description = "QuartZ Job VO")
@Data
public class QuartzJobVO {
    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("任务标识")
    private String jobId;

    @ApiModelProperty("任务名称")
    private String jobName;

    @ApiModelProperty("任务执行类")
    private String jobClass;

    @ApiModelProperty("cron表达式")
    private String cronExps;

    @ApiModelProperty("运行状态（0-已暂停；1-运行中）")
    private Integer status;
}

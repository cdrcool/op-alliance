package com.op.admin.job;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Quartz Job mapping
 *
 * @author cdrcool
 */
@Mapper(componentModel = "spring")
public interface QuartzJobMapping {

    /**
     * Quartz Job保存 dto ->  Quartz Job
     *
     * @param saveDTO Quartz Job保存 dto
     * @return Quartz Job
     */
    QuartzJob toQuartzJob(QuartzJobSaveDTO saveDTO);

    /**
     * 根据 Quartz Job保存 dto 更新 Quartz Job
     *
     * @param saveDTO   Quartz Job保存 dto
     * @param userGroup Quartz Job
     */
    void update(QuartzJobSaveDTO saveDTO, @MappingTarget QuartzJob userGroup);

    /**
     * Quartz Job ->  Quartz Job vo
     *
     * @param userGroup Quartz Job
     * @return Quartz Job vo
     */
    QuartzJobVO toQuartzJobVO(QuartzJob userGroup);

    /**
     * Quartz Job列表 ->  Quartz Job vo 列表
     *
     * @param userGroups Quartz Job列表
     * @return Quartz Job vo 列表
     */
    List<QuartzJobVO> toQuartzJobVOList(List<QuartzJob> userGroups);
}

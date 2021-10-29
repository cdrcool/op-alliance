package com.op.admin.job;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Quartz Job Service
 *
 * @author cdrcool
 */
public interface QuartzJobService {

    /**
     * 保存 Quartz Job
     * <p>
     * 在更新定时任务之后会立即执行一次
     *
     * @param saveDTO Quartz Job保存 dto
     */
    void save(QuartzJobSaveDTO saveDTO);

    /**
     * 删除 Quartz Job
     *
     * @param jobId job 标识
     */
    void deleteByJobId(String jobId);

    /**
     * 批量删除 Quartz Job
     *
     * @param jobIds job 标识列表
     */
    void deleteByJobIds(List<String> jobIds);

    /**
     * 查找 Quartz Job
     *
     * @param id Quartz Job id
     * @return Quartz Job vo
     */
    QuartzJobVO findById(Integer id);

    /**
     * 分页查询 Quartz Job
     *
     * @param pageable 分页对象
     * @param queryDTO 查询对象
     * @return Quartz Job vo 分页列表
     */
    Page<QuartzJobVO> queryPage(Pageable pageable, QuartzJobPageQueryDTO queryDTO);

    /**
     * 暂停 Quartz Job
     *
     * @param jobId job 标识
     */
    void pause(String jobId);

    /**
     * 恢复 Quartz Job
     * <p>
     * 在恢复定时任务之后会立即执行一次
     *
     * @param jobId job 标识
     */
    void resume(String jobId);

    /**
     * 触发 Quartz Job
     *
     * @param jobId job 标识
     */
    void trigger(String jobId);
}

package com.op.admin.job;

import com.github.pagehelper.PageHelper;
import com.op.framework.web.common.api.response.ResultCode;
import com.op.framework.web.common.api.response.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.SimpleSortSpecification;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.quartz.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

/**
 * Quartz Job Service Impl
 *
 * @author chengdr01
 */
@Slf4j
@Service
public class QuartzJobServiceImpl implements QuartzJobService {
    private final QuartzJobMapper quartzJobMapper;
    private final QuartzJobMapping quartzJobMapping;
    private final Scheduler scheduler;

    public QuartzJobServiceImpl(QuartzJobMapper quartzJobMapper, QuartzJobMapping quartzJobMapping, Scheduler scheduler) {
        this.quartzJobMapper = quartzJobMapper;
        this.quartzJobMapping = quartzJobMapping;
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void init() {
        SelectStatementProvider selectStatementProvider = select(QuartzJobMapper.selectList)
                .from(QuartzJobDynamicSqlSupport.qrtzJob)
                .where(QuartzJobDynamicSqlSupport.status, isEqualTo(1))
                .build().render(RenderingStrategies.MYBATIS3);
        List<QuartzJob> jobList = quartzJobMapper.selectMany(selectStatementProvider);
        jobList.forEach(job -> {
            JobKey jobKey = JobKey.jobKey(job.getJobId());
            try {
                scheduler.triggerJob(jobKey);
            } catch (SchedulerException e) {
                log.error("执行定时任务【{}】异常", job.getJobId(), e);
            }
        });
    }

    @Override
    public void save(QuartzJobSaveDTO saveDTO) {
        Class<? extends Job> jobClass;
        try {
            jobClass = (Class<? extends Job>) Class.forName(saveDTO.getJobClass());
        } catch (ClassNotFoundException e) {
            log.error("获取任务执行类【{}】异常", saveDTO.getJobClass(), e);
            throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "获取任务执行类【" + saveDTO.getJobClass() + "】异常", e);
        }

        // 构建定时任务信息
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(saveDTO.getJobId()).build();
        // 设置定时任务执行方式
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(saveDTO.getCronExps());
        // 构建触发器 trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(saveDTO.getJobId()).withSchedule(scheduleBuilder).build();

        if (saveDTO.getId() == null) {
            try {
                // 执行任务调度
                scheduler.scheduleJob(jobDetail, trigger);
            } catch (SchedulerException e) {
                log.error("执行定时任务【{}】调度异常", saveDTO.getJobId(), e);
                throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "执行定时任务【" + saveDTO.getJobId() + "】调度异常", e);
            }

            QuartzJob quartzJob = quartzJobMapping.toQuartzJob(saveDTO);
            quartzJobMapper.insert(quartzJob);
        } else {
            TriggerKey triggerKey = TriggerKey.triggerKey(saveDTO.getJobId());
            try {
                trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                // 重新执行任务调度
                scheduler.rescheduleJob(triggerKey, trigger);
            } catch (SchedulerException e) {
                log.error("重新执行定时任务【{}】调度异常", saveDTO.getJobId(), e);
                throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "重新执行定时任务【" + saveDTO.getJobId() + "】调度异常", e);
            }

            Integer id = saveDTO.getId();
            QuartzJob quartzJob = quartzJobMapper.selectByPrimaryKey(id)
                    .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的定时任务"));
            quartzJobMapping.update(saveDTO, quartzJob);
            quartzJobMapper.updateByPrimaryKey(quartzJob);
        }
    }

    @Override
    public void deleteById(String jobId) {
        JobKey jobKey = JobKey.jobKey(jobId);
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            log.error("删除定时任务【{}】异常", jobId, e);
            throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "删除定时任务【" + jobId + "】异常", e);
        }

        DeleteStatementProvider deleteStatementProvider = deleteFrom(QuartzJobDynamicSqlSupport.qrtzJob)
                .where(QuartzJobDynamicSqlSupport.jobId, isEqualTo(jobId))
                .build().render(RenderingStrategies.MYBATIS3);
        quartzJobMapper.delete(deleteStatementProvider);
    }

    @Override
    public void deleteByIds(List<String> jobIds) {
        jobIds.forEach(this::deleteById);
    }

    @Override
    public QuartzJobVO findById(Integer id) {
        QuartzJob quartzJob = quartzJobMapper.selectByPrimaryKey(id)
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的定时任务"));
        return quartzJobMapping.toQuartzJobVO(quartzJob);
    }

    @Override
    public Page<QuartzJobVO> queryPage(Pageable pageable, QuartzJobPageQueryDTO queryDTO) {
        SortSpecification[] specifications = pageable.getSort().stream()
                .map(order -> {
                    SortSpecification specification = SimpleSortSpecification.of(order.getProperty());
                    if (order.isDescending()) {
                        return specification.descending();
                    }
                    return specification;
                }).toArray(SortSpecification[]::new);

        SelectStatementProvider selectStatementProvider = select(QuartzJobMapper.selectList)
                .from(QuartzJobDynamicSqlSupport.qrtzJob)
                .where(QuartzJobDynamicSqlSupport.status, isEqualToWhenPresent(queryDTO.getStatus()))
                .and(QuartzJobDynamicSqlSupport.jobId, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%"),
                        or(QuartzJobDynamicSqlSupport.jobName, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%")))
                .orderBy(specifications)
                .build().render(RenderingStrategies.MYBATIS3);

        com.github.pagehelper.Page<QuartzJob> result = PageHelper
                .startPage(pageable.getPageNumber() + 1, pageable.getPageSize())
                .doSelectPage(() -> quartzJobMapper.selectMany(selectStatementProvider));

        return new PageImpl<>(quartzJobMapping.toQuartzJobVOList(result.getResult()), pageable, result.getTotal());
    }

    @Override
    public void pause(String jobId) {
        JobKey jobKey = JobKey.jobKey(jobId);
        try {
            scheduler.pauseJob(jobKey);

            UpdateStatementProvider updateStatement = SqlBuilder.update(QuartzJobDynamicSqlSupport.qrtzJob)
                    .set(QuartzJobDynamicSqlSupport.status).equalTo(0)
                    .where(QuartzJobDynamicSqlSupport.jobId, isEqualTo(jobId))
                    .build()
                    .render(RenderingStrategies.MYBATIS3);
            quartzJobMapper.update(updateStatement);
        } catch (SchedulerException e) {
            log.error("暂停定时任务【{}】异常", jobId, e);
            throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "暂停定时任务【" + jobId + "】异常", e);
        }
    }

    @Override
    public void resume(String jobId) {
        JobKey jobKey = JobKey.jobKey(jobId);
        try {
            scheduler.pauseJob(jobKey);

            UpdateStatementProvider updateStatement = SqlBuilder.update(QuartzJobDynamicSqlSupport.qrtzJob)
                    .set(QuartzJobDynamicSqlSupport.status).equalTo(1)
                    .where(QuartzJobDynamicSqlSupport.jobId, isEqualTo(jobId))
                    .build()
                    .render(RenderingStrategies.MYBATIS3);
            quartzJobMapper.update(updateStatement);
        } catch (SchedulerException e) {
            log.error("恢复定时任务【{}】异常", jobId, e);
            throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "恢复定时任务【" + jobId + "】异常", e);
        }
    }

    @Override
    public void trigger(String jobId) {
        JobKey jobKey = JobKey.jobKey(jobId);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            log.error("触发定时任务【{}】异常", jobId, e);
        }
    }
}

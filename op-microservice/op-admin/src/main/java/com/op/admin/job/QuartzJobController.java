package com.op.admin.job;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 定时任务 Controller
 *
 * @author chengdr01
 */
@RequestMapping("quartzJob")
@RestController
public class QuartzJobController {
    private final QuartzJobService quartzJobService;

    public QuartzJobController(QuartzJobService quartzJobService) {
        this.quartzJobService = quartzJobService;
    }

    @ApiOperation("保存定时任务")
    @PostMapping("save")
    public void save(@Valid @RequestBody QuartzJobSaveDTO saveDTO) {
        quartzJobService.save(saveDTO);
    }

    @ApiOperation("删除定时任务")
    @PostMapping("delete")
    public void delete(@RequestParam String jobId) {
        quartzJobService.deleteById(jobId);
    }

    @ApiOperation("批量删除定时任务")
    @PostMapping("batchDelete")
    public void batchDelete(@RequestBody List<String> jobIds) {
        jobIds.forEach(quartzJobService::deleteById);
    }

    @ApiOperation("查看定时任务详情")
    @GetMapping("get")
    public QuartzJobVO get(@RequestParam Integer id) {
        return quartzJobService.findById(id);
    }

    @ApiOperation("分页查询定时任务")
    @PostMapping("queryPage")
    public Page<QuartzJobVO> queryPage(@PageableDefault(sort = "job_name", direction = Sort.Direction.ASC) Pageable pageable,
                                       @Valid @RequestBody QuartzJobPageQueryDTO queryDTO) {
        return quartzJobService.queryPage(pageable, queryDTO);
    }

    @ApiOperation("暂停定时任务")
    @PostMapping("pause")
    public void pause(@Valid @RequestParam String jobId) {
        quartzJobService.pause(jobId);
    }

    @ApiOperation("恢复定时任务")
    @PostMapping("resume")
    public void resume(@Valid @RequestParam String jobId) {
        quartzJobService.resume(jobId);
    }

    @ApiOperation("触发定时任务")
    @PostMapping("trigger")
    public void trigger(@Valid @RequestParam String jobId) {
        quartzJobService.resume(jobId);
    }
}

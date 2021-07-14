package com.op.admin.controller;

import com.op.admin.dto.ResourceActionPageQueryDTO;
import com.op.admin.dto.ResourceActionSaveDTO;
import com.op.admin.service.ResourceActionService;
import com.op.admin.vo.ResourceActionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 资源动作 Controller
 *
 * @author cdrcool
 */
@Api(tags = "资源动作管理")
@RequestMapping("resourceAction")
@RestController
public class ResourceActionController {
    private final ResourceActionService resourceActionService;

    public ResourceActionController(ResourceActionService resourceActionService) {
        this.resourceActionService = resourceActionService;
    }

    @ApiOperation("保存资源动作")
    @PostMapping("save")
    public void save(@Valid @RequestBody ResourceActionSaveDTO saveDTO) {
        resourceActionService.save(saveDTO);
    }

    @ApiOperation("删除资源动作")
    @PostMapping("delete")
    public void delete(@RequestParam Integer id) {
        resourceActionService.deleteById(id);
    }

    @ApiOperation("批量删除资源动作")
    @PostMapping("batchDelete")
    public void batchDelete(@RequestBody List<Integer> ids) {
        resourceActionService.deleteByIds(ids);
    }

    @ApiOperation("查看资源动作详情")
    @GetMapping("get")
    public ResourceActionVO get(@RequestParam Integer id) {
        return resourceActionService.findById(id);
    }

    @ApiOperation("分页查询资源动作")
    @PostMapping("page")
    public Page<ResourceActionVO> queryPage(@PageableDefault(sort = "action_no", direction = Sort.Direction.ASC) Pageable pageable,
                                            @Valid @RequestBody ResourceActionPageQueryDTO queryDTO) {
        return resourceActionService.queryPage(pageable, queryDTO);
    }

    @ApiOperation("获取指定资源下的资源动作列表")
    @GetMapping("findByResourceId")
    public List<ResourceActionVO> findByResourceId(@RequestParam Integer resourceId) {
        return resourceActionService.findByResourceId(resourceId);
    }
}

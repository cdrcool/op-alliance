package com.op.admin.controller;

import com.op.admin.dto.ResourcePageQueryDTO;
import com.op.admin.dto.ResourceSaveDTO;
import com.op.admin.service.ResourceService;
import com.op.admin.vo.ResourceVO;
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
 * 资源 Controller
 *
 * @author cdrcool
 */
@Api(tags = "资源管理")
@RequestMapping("resource")
@RestController
public class ResourceController {
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @ApiOperation("保存资源")
    @PostMapping("save")
    public void save(@Valid @RequestBody ResourceSaveDTO saveDTO) {
        resourceService.save(saveDTO);
    }

    @ApiOperation("删除资源")
    @PostMapping("delete")
    public void delete(@RequestParam Integer id) {
        resourceService.deleteById(id);
    }

    @ApiOperation("批量删除资源")
    @PostMapping("batchDelete")
    public void batchDelete(@RequestBody List<Integer> ids) {
        resourceService.deleteByIds(ids);
    }

    @ApiOperation("查看资源详情")
    @GetMapping("get")
    public ResourceVO get(@RequestParam Integer id) {
        return resourceService.findById(id);
    }

    @ApiOperation("分页查询资源")
    @PostMapping("queryPage")
    public Page<ResourceVO> queryPage(@PageableDefault(sort = "resource_no", direction = Sort.Direction.ASC) Pageable pageable,
                                      @Valid @RequestBody ResourcePageQueryDTO queryDTO) {
        return resourceService.queryPage(pageable, queryDTO);
    }
}

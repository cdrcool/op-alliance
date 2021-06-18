package com.op.admin.controller;

import com.op.admin.dto.ResourceListQueryDTO;
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

/**
 * 资源 Controller
 *
 * @author cdrcool
 */
@Api(tags = "资源管理")
@RequestMapping("role")
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

    @ApiOperation("查看资源详情")
    @GetMapping("get")
    public ResourceVO get(@RequestParam Integer id) {
        return resourceService.findById(id);
    }

    @ApiOperation("分页查询资源")
    @PostMapping("page")
    public Page<ResourceVO> queryPage(@PageableDefault(sort = "resourceNo", direction = Sort.Direction.ASC) Pageable pageable,
                                      @Valid @RequestBody ResourceListQueryDTO queryDTO) {
        return resourceService.queryPage(pageable, queryDTO);
    }
}

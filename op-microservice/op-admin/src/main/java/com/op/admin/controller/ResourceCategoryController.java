package com.op.admin.controller;

import com.op.admin.dto.ResourceCategoryPageQueryDTO;
import com.op.admin.dto.ResourceCategorySaveDTO;
import com.op.admin.service.ResourceCategoryService;
import com.op.admin.vo.ResourceCategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 资源分类 Controller
 *
 * @author cdrcool
 */
@Api(tags = "资源分类管理")
@RequestMapping("resourceCategory")
@RestController
public class ResourceCategoryController {
    private final ResourceCategoryService resourceCategoryService;

    public ResourceCategoryController(ResourceCategoryService resourceCategoryService) {
        this.resourceCategoryService = resourceCategoryService;
    }

    @ApiOperation("保存资源分类")
    @PostMapping("save")
    public void save(@Valid @RequestBody ResourceCategorySaveDTO saveDTO) {
        resourceCategoryService.save(saveDTO);
    }

    @ApiOperation("删除资源分类")
    @PostMapping("delete")
    public void delete(@RequestParam Integer id) {
        resourceCategoryService.deleteById(id);
    }

    @ApiOperation("查看资源分类详情")
    @GetMapping("get")
    public ResourceCategoryVO get(@RequestParam Integer id) {
        return resourceCategoryService.findById(id);
    }

    @ApiOperation("分页查询资源分类")
    @PostMapping("page")
    public Page<ResourceCategoryVO> queryPage(@PageableDefault(sort = "categoryNo", direction = Sort.Direction.ASC) Pageable pageable,
                                              @Valid @RequestBody ResourceCategoryPageQueryDTO queryDTO) {
        return resourceCategoryService.queryPage(pageable, queryDTO);
    }
}
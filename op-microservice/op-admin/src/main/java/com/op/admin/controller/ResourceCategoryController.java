package com.op.admin.controller;

import com.op.admin.dto.ResourceCategoryPageQueryDTO;
import com.op.admin.dto.ResourceCategorySaveDTO;
import com.op.admin.service.ResourceCategoryService;
import com.op.admin.vo.ResourceCategoryVO;
import com.op.admin.vo.SelectOptionVO;
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
 * 资源分类 Controller
 *
 * @author cdrcool
 */
@Api(tags = "资源分类")
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

    @ApiOperation("批量删除资源分类")
    @PostMapping("batchDelete")
    public void batchDelete(@RequestBody List<Integer> ids) {
        resourceCategoryService.deleteByIds(ids);
    }

    @ApiOperation("查看资源分类详情")
    @GetMapping("get")
    public ResourceCategoryVO get(@RequestParam Integer id) {
        return resourceCategoryService.findById(id);
    }

    @ApiOperation("分页查询资源分类")
    @PostMapping("queryPage")
    public Page<ResourceCategoryVO> queryPage(@PageableDefault(sort = "category_no", direction = Sort.Direction.ASC) Pageable pageable,
                                              @Valid @RequestBody ResourceCategoryPageQueryDTO queryDTO) {
        return resourceCategoryService.queryPage(pageable, queryDTO);
    }

    @ApiOperation("查询资源分类下拉框选项列表")
    @PostMapping("querySelectOptions")
    public List<SelectOptionVO> querySelectOptions(@Valid @RequestBody ResourceCategoryPageQueryDTO queryDTO) {
        return resourceCategoryService.querySelectOptions(queryDTO);
    }
}

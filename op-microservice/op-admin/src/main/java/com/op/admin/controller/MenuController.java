package com.op.admin.controller;

import com.op.admin.dto.MenuListQueryDTO;
import com.op.admin.dto.MenuSaveDTO;
import com.op.admin.service.MenuService;
import com.op.admin.vo.MenuTreeVO;
import com.op.admin.vo.MenuVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 菜单 Controller
 *
 * @author cdrcool
 */
@Api(tags = "菜单管理")
@RequestMapping("menu")
@RestController
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @ApiOperation("保存菜单")
    @PostMapping("save")
    public void save(@Valid @RequestBody MenuSaveDTO saveDTO) {
        menuService.save(saveDTO);
    }

    @ApiOperation("删除菜单")
    @PostMapping("delete")
    public void delete(@RequestParam Integer id) {
        menuService.deleteById(id);
    }

    @ApiOperation("查看菜单详情")
    @GetMapping("get")
    public MenuVO get(@RequestParam Integer id) {
        return menuService.findById(id);
    }

    @ApiOperation("查询菜单树列表")
    @PostMapping("queryTreeList")
    public List<MenuTreeVO> queryTreeList() {
        return menuService.queryTreeList();
    }

    @ApiOperation("查询菜单列表")
    @PostMapping("queryList")
    public List<MenuVO> queryList(@Valid @RequestBody MenuListQueryDTO queryDTO) {
        return menuService.queryList(queryDTO);
    }

    @ApiOperation("显示/隐藏菜单")
    @PostMapping("changeVisibility")
    public void changeVisibility(@RequestParam Integer id, @RequestParam boolean show) {
        menuService.changeVisibility(id, show);
    }
}

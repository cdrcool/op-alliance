package com.op.admin.controller;

import com.op.admin.dto.MenuAssignDTO;
import com.op.admin.dto.ResourceAssignDTO;
import com.op.admin.dto.RolePageQueryDTO;
import com.op.admin.dto.RoleSaveDTO;
import com.op.admin.service.RoleService;
import com.op.admin.vo.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 角色 Controller
 *
 * @author cdrcool
 */
@Api(tags = "角色管理")
@RequestMapping("role")
@RestController
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation("保存角色")
    @PostMapping("save")
    public void save(@Valid @RequestBody RoleSaveDTO saveDTO) {
        roleService.save(saveDTO);
    }

    @ApiOperation("删除角色")
    @PostMapping("delete")
    public void delete(@RequestParam Integer id) {
        roleService.deleteById(id);
    }

    @ApiOperation("查看角色详情")
    @GetMapping("get")
    public RoleVO get(@RequestParam Integer id) {
        return roleService.findById(id);
    }

    @ApiOperation("分页查询角色")
    @PostMapping("page")
    public Page<RoleVO> queryPage(@PageableDefault(sort = "roleNo", direction = Sort.Direction.ASC) Pageable pageable,
                                       @Valid @RequestBody RolePageQueryDTO queryDTO) {
        return roleService.queryPage(pageable, queryDTO);
    }

    @ApiOperation("启用/禁用角色")
    @PostMapping("changeEnabled")
    public void changeEnabled(@RequestParam Integer id, @RequestParam boolean enable) {
        roleService.changeEnabled(id, enable);
    }

    @ApiOperation("分配资源")
    @PostMapping("assignResources")
    public void assignResources(@Valid @RequestBody ResourceAssignDTO resourceAssignDTO){
        roleService.assignResources(resourceAssignDTO.getId(), resourceAssignDTO.getResourceIds());
    }

    @ApiOperation("分配菜单")
    @PostMapping("assignMenus")
    public void assignMenus(@Valid @RequestBody MenuAssignDTO menuAssignDTO){
        roleService.assignMenus(menuAssignDTO.getId(), menuAssignDTO.getMenuIds());
    }
}

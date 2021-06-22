package com.op.admin.controller;

import com.op.admin.dto.*;
import com.op.admin.service.OrganizationService;
import com.op.admin.vo.OrganizationTreeVO;
import com.op.admin.vo.OrganizationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 组织 Controller
 *
 * @author cdrcool
 */
@Api(tags = "组织管理")
@RequestMapping("organization")
@RestController
public class OrganizationController {
    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @ApiOperation("保存组织")
    @PostMapping("save")
    public void save(@Valid @RequestBody OrganizationSaveDTO saveDTO) {
        organizationService.save(saveDTO);
    }

    @ApiOperation("删除组织")
    @PostMapping("delete")
    public void delete(@RequestParam Integer id) {
        organizationService.deleteById(id);
    }

    @ApiOperation("查看组织详情")
    @GetMapping("get")
    public OrganizationVO get(@RequestParam Integer id) {
        return organizationService.findById(id);
    }

    @ApiOperation("查询组织树")
    @PostMapping("queryTree")
    public OrganizationTreeVO queryTree(@Valid @RequestBody OrganizationTreeQueryDTO queryDTO) {
        return organizationService.queryTree(queryDTO);
    }

    @ApiOperation("查询组织列表")
    @PostMapping("queryList")
    public List<OrganizationVO> queryList(@Valid @RequestBody OrganizationListQueryDTO queryDTO) {
        return organizationService.queryList(queryDTO);
    }

    @ApiOperation("分配角色")
    @PostMapping("assignRoles")
    public void assignRoles(@Valid @RequestBody RoleAssignDTO roleAssignDTO) {
        organizationService.assignRoles(roleAssignDTO.getId(), roleAssignDTO.getRoleIds());
    }

    @ApiOperation("分配资源")
    @PostMapping("assignResources")
    public void assignResources(@Valid @RequestBody ResourceAssignDTO resourceAssignDTO) {
        organizationService.assignRoles(resourceAssignDTO.getId(), resourceAssignDTO.getResourceIds());
    }

    @ApiOperation("分配菜单")
    @PostMapping("assignMenus")
    public void assignMenus(@Valid @RequestBody MenuAssignDTO menuAssignDTO) {
        organizationService.assignMenus(menuAssignDTO.getId(), menuAssignDTO.getMenuIds());
    }
}

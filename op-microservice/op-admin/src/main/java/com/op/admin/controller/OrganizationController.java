package com.op.admin.controller;

import com.op.admin.dto.*;
import com.op.admin.vo.*;
import com.op.admin.service.OrganizationService;
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

    @ApiOperation("批量删除组织")
    @PostMapping("batchDelete")
    public void batchDelete(@RequestBody List<Integer> ids) {
        organizationService.deleteByIds(ids);
    }

    @ApiOperation("查看组织详情")
    @GetMapping("get")
    public OrganizationVO get(@RequestParam Integer id) {
        return organizationService.findById(id);
    }

    @ApiOperation("查询组织树列表")
    @PostMapping("queryTreeList")
    public List<OrganizationTreeVO> queryTreeList(@Valid @RequestBody OrganizationTreeQueryDTO queryDTO) {
        return organizationService.queryTreeList(queryDTO);
    }

    @ApiOperation("分配角色")
    @PostMapping("assignRoles")
    public void assignRoles(@Valid @RequestBody RoleAssignDTO roleAssignDTO) {
        organizationService.assignRoles(roleAssignDTO.getId(), roleAssignDTO.getRoleIds());
    }

    @ApiOperation("分配资源动作")
    @PostMapping("assignResourceActions")
    public void assignResourceActions(@Valid @RequestBody ResourceAssignDTO resourceAssignDTO) {
        organizationService.assignResourceActions(resourceAssignDTO.getId(), resourceAssignDTO.getResourceActionIds());
    }

    @ApiOperation("获取组织角色分配情况")
    @GetMapping("loadAssignedRoles")
    public List<RoleAssignVO> loadRoles(@RequestParam Integer id) {
        return organizationService.loadAssignedRoles(id);
    }

    @ApiOperation("获取组织资源分配情况")
    @GetMapping("loadAssignedResources")
    public List<ResourceCategoryAssignVO> loadAssignedResources(@RequestParam Integer id) {
        return organizationService.loadAssignedResources(id);
    }

    @ApiOperation("查询组织异步树（平展的）")
    @PostMapping("queryForAsyncTreeFlat")
    public List<TreeNodeVO> queryForAsyncTreeFlat(@Valid @RequestBody OrganizationTreeQueryDTO queryDTO) {
        return organizationService.queryForAsyncTreeFlat(queryDTO);
    }

    @ApiOperation("查询组织异步树")
    @PostMapping("queryForAsyncTree")
    public List<TreeNodeVO> queryForAsyncTree(@Valid @RequestBody OrganizationTreeQueryDTO queryDTO) {
        return organizationService.queryForAsyncTree(queryDTO);
    }
}

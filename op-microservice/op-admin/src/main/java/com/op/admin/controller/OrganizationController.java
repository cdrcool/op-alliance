package com.op.admin.controller;

import com.op.admin.dto.*;
import com.op.admin.dto.*;
import com.op.admin.vo.*;
import com.op.admin.service.OrganizationService;
import com.op.admin.vo.*;
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

    @ApiOperation("分配资源动作")
    @PostMapping("assignResourceActions")
    public void assignResourceActions(@Valid @RequestBody ResourceAssignDTO resourceAssignDTO) {
        organizationService.assignResourceActions(resourceAssignDTO.getId(), resourceAssignDTO.getResourceIds());
    }

    @ApiOperation("查找所有角色，以及组织分配情况")
    @GetMapping("loadRoles")
    public List<RoleAssignVO> loadRoles(@RequestParam Integer id) {
        return organizationService.loadRoles(id);
    }

    @ApiOperation("查找所有资源，以及组织分配情况")
    @GetMapping("loadResources")
    public List<ResourceCategoryAssignVO> loadResources(@RequestParam Integer id) {
        return organizationService.loadResources(id);
    }
}

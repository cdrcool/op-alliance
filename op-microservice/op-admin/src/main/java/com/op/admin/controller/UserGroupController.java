package com.op.admin.controller;

import com.op.admin.dto.ResourceAssignDTO;
import com.op.admin.dto.RoleAssignDTO;
import com.op.admin.dto.UserGroupPageQueryDTO;
import com.op.admin.dto.UserGroupSaveDTO;
import com.op.admin.service.UserGroupService;
import com.op.admin.vo.ResourceCategoryAssignVO;
import com.op.admin.vo.RoleAssignVO;
import com.op.admin.vo.UserGroupVO;
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
 * 用户组 Controller
 *
 * @author cdrcool
 */
@Api(tags = "用户组")
@RequestMapping("userGroup")
@RestController
public class UserGroupController {
    private final UserGroupService userGroupService;

    public UserGroupController(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }

    @ApiOperation("保存用户组")
    @PostMapping("save")
    public void save(@Valid @RequestBody UserGroupSaveDTO saveDTO) {
        userGroupService.save(saveDTO);
    }

    @ApiOperation("删除用户组")
    @PostMapping("delete")
    public void delete(@RequestParam Integer id) {
        userGroupService.deleteById(id);
    }

    @ApiOperation("批量删除用户组")
    @PostMapping("batchDelete")
    public void batchDelete(@RequestBody List<Integer> ids) {
        userGroupService.deleteByIds(ids);
    }

    @ApiOperation("查看用户组详情")
    @GetMapping("get")
    public UserGroupVO get(@RequestParam Integer id) {
        return userGroupService.findById(id);
    }

    @ApiOperation("分页查询用户组")
    @PostMapping("queryPage")
    public Page<UserGroupVO> queryPage(@PageableDefault(sort = "group_no", direction = Sort.Direction.ASC) Pageable pageable,
                                       @Valid @RequestBody UserGroupPageQueryDTO queryDTO) {
        return userGroupService.queryPage(pageable, queryDTO);
    }

    @ApiOperation("分配角色")
    @PostMapping("assignRoles")
    public void assignRoles(@Valid @RequestBody RoleAssignDTO roleAssignDTO) {
        userGroupService.assignRoles(roleAssignDTO.getId(), roleAssignDTO.getRoleIds());
    }

    @ApiOperation("分配资源动作")
    @PostMapping("assignResourceActions")
    public void assignResourceActions(@Valid @RequestBody ResourceAssignDTO resourceAssignDTO) {
        userGroupService.assignResourceActions(resourceAssignDTO.getId(), resourceAssignDTO.getResourceActionIds());
    }

    @ApiOperation("获取用户组角色分配情况")
    @GetMapping("loadAssignedRoles")
    public List<RoleAssignVO> loadAssignedRoles(@RequestParam Integer id) {
        return userGroupService.loadAssignedRoles(id);
    }

    @ApiOperation("获取用户组资源分配情况")
    @GetMapping("loadAssignedResources")
    public List<ResourceCategoryAssignVO> loadAssignedResources(@RequestParam Integer id) {
        return userGroupService.loadAssignedResources(id);
    }
}

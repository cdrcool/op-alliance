package com.op.admin.controller;

import com.op.admin.dto.*;
import com.op.admin.service.UserService;
import com.op.admin.vo.MenuAssignVO;
import com.op.admin.vo.ResourceCategoryAssignVO;
import com.op.admin.vo.RoleAssignVO;
import com.op.admin.vo.UserVO;
import com.op.framework.web.common.api.response.NoApiResponse;
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
 * 用户 Controller
 *
 * @author cdrcool
 */
@Api(tags = "用户管理")
@RequestMapping("user")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("创建用户（返回新建用户的密码）")
    @PostMapping("create")
    public String create(@Valid @RequestBody UserCreateDTO createDTO) {
        return userService.create(createDTO);
    }

    @ApiOperation("修改用户密码")
    @PostMapping("changePassword")
    public void changePassword(@Valid @RequestBody UserChangePasswordDTO changePasswordDTO) {
        userService.changePassword(changePasswordDTO);
    }

    @ApiOperation("修改用户资料")
    @PostMapping("update")
    public void update(@Valid @RequestBody UserUpdateDTO updateDTO) {
        userService.update(updateDTO);
    }

    @ApiOperation("删除用户")
    @PostMapping("delete")
    public void delete(@RequestParam Integer id) {
        userService.deleteById(id);
    }

    @ApiOperation("查看用户详情")
    @GetMapping("get")
    public UserVO get(@RequestParam Integer id) {
        return userService.findById(id);
    }

    @NoApiResponse
    @ApiOperation("根据用户名查找用户")
    @GetMapping("getByUsername")
    public UserDTO getByUsername(@RequestParam String username) {
        return userService.findByUserName(username);
    }

    @ApiOperation("分页查询用户")
    @PostMapping("page")
    public Page<UserVO> queryPage(@PageableDefault(sort = "userNo", direction = Sort.Direction.ASC) Pageable pageable,
                                  @Valid @RequestBody UserPageQueryDTO queryDTO) {
        return userService.queryPage(pageable, queryDTO);
    }

    @ApiOperation("启用/禁用用户")
    @PostMapping("changeEnabled")
    public void changeEnabled(@RequestParam Integer id, @RequestParam boolean enable) {
        userService.changeEnabled(id, enable);
    }

    @ApiOperation("分配角色")
    @PostMapping("assignRoles")
    public void assignRoles(@Valid @RequestBody RoleAssignDTO roleAssignDTO) {
        userService.assignRoles(roleAssignDTO.getId(), roleAssignDTO.getRoleIds());
    }

    @ApiOperation("分配资源动作")
    @PostMapping("assignResourceActions")
    public void assignResourceActions(@Valid @RequestBody ResourceAssignDTO resourceAssignDTO) {
        userService.assignResourceActions(resourceAssignDTO.getId(), resourceAssignDTO.getResourceIds());
    }

    @ApiOperation("分配菜单")
    @PostMapping("assignMenus")
    public void assignMenus(@Valid @RequestBody MenuAssignDTO menuAssignDTO) {
        userService.assignMenus(menuAssignDTO.getId(), menuAssignDTO.getMenuIds());
    }

    @ApiOperation("查找所有角色，以及用户分配情况")
    @GetMapping("loadRoles")
    public List<RoleAssignVO> loadRoles(@RequestParam Integer id) {
        return userService.loadRoles(id);
    }

    @ApiOperation("查找所有资源，以及用户分配情况")
    @GetMapping("loadResources")
    public List<ResourceCategoryAssignVO> loadResources(@RequestParam Integer id) {
        return userService.loadResources(id);
    }

    @ApiOperation("查找所有菜单，以及用户分配情况")
    @GetMapping("loadMenus")
    public List<MenuAssignVO> loadMenus(@RequestParam Integer id) {
        return userService.loadMenus(id);
    }
}

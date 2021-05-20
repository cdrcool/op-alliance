package com.op.admin.controller;

import com.github.pagehelper.Page;
import com.op.admin.dto.UserChangePasswordDto;
import com.op.admin.dto.UserCreateDto;
import com.op.admin.dto.UserUpdateDto;
import com.op.admin.entity.User;
import com.op.admin.service.UserService;
import com.op.admin.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @ApiOperation("创建用户")
    @PostMapping("create")
    public void create(@Valid @RequestBody UserCreateDto userCreateDto) {
        userService.create(userCreateDto);
    }

    @ApiOperation("修改用户密码")
    @PostMapping("changePassword")
    public void changePassword(@Valid @RequestBody UserChangePasswordDto userChangePasswordDto) {
        userService.changePassword(userChangePasswordDto);
    }

    @ApiOperation("修改用户资料")
    @PostMapping("update")
    public void update(@Valid @RequestBody UserUpdateDto userUpdateDto) {
        userService.update(userUpdateDto);
    }

    @ApiOperation("删除用户")
    @PostMapping("delete")
    public void delete(@RequestParam Integer id) {
        userService.deleteById(id);
    }

    @ApiOperation("查看用户详情")
    @GetMapping("get")
    public UserVo get(@RequestParam Integer id) {
        return userService.findById(id);
    }

    @ApiOperation("分页查询用户")
    @PostMapping("page")
    public Page<UserVo> queryPage(@PageableDefault(sort = "createTime", direction = Sort.Direction.ASC) Pageable pageable) {
        return userService.queryPage(pageable);
    }
}

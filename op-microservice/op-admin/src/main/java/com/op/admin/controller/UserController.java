package com.op.admin.controller;

import com.op.admin.dto.UserChangePasswordDTO;
import com.op.admin.dto.UserCreateDTO;
import com.op.admin.dto.UserUpdateDTO;
import com.op.admin.service.UserService;
import com.op.admin.vo.UserVO;
import com.op.framework.web.common.api.criterion.Criterion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
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
    public String create(@Valid @RequestBody UserCreateDTO userCreateDto) {
        return userService.create(userCreateDto);
    }

    @ApiOperation("修改用户密码")
    @PostMapping("changePassword")
    public void changePassword(@Valid @RequestBody UserChangePasswordDTO userChangePasswordDto) {
        userService.changePassword(userChangePasswordDto);
    }

    @ApiOperation("修改用户资料")
    @PostMapping("update")
    public void update(@Valid @RequestBody UserUpdateDTO userUpdateDto) {
        userService.update(userUpdateDto);
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

    @ApiOperation("分页查询用户")
    @PostMapping("page")
    public Page<UserVO> queryPage(@PageableDefault(sort = "userNo", direction = Sort.Direction.ASC) Pageable pageable,
                                  @RequestBody Criterion criterion) {
        return userService.queryPage(pageable, criterion);
    }
}

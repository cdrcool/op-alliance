package com.op.admin.service;

import com.github.pagehelper.Page;
import com.op.admin.dto.UserChangePasswordDto;
import com.op.admin.dto.UserCreateDto;
import com.op.admin.dto.UserUpdateDto;
import com.op.admin.vo.UserVo;
import org.springframework.data.domain.Pageable;

/**
 * 用户 Service
 *
 * @author chengdr01
 */
public interface UserService {

    /**
     * 创建用户
     *
     * @param userCreateDto 用户创建 dto
     */
    void create(UserCreateDto userCreateDto);

    /**
     * 修改用户密码
     *
     * @param changePasswordDto 用户修改密码 dto
     */
    void changePassword(UserChangePasswordDto changePasswordDto);

    /**
     * 修改用户资料
     *
     * @param userUpdateDto 用户更新 dto
     */
    void update(UserUpdateDto userUpdateDto);

    /**
     * 删除用户
     *
     * @param id 用户id
     */
    void deleteById(Integer id);

    /**
     * 查找用户
     *
     * @param id 用户id
     * @return 用户 vo
     */
    UserVo findById(Integer id);

    /**
     * 分页查询用户
     *
     * @param pageable 分页对象
     * @return 用户 vo 分页列表
     */
    Page<UserVo> queryPage(Pageable pageable);
}

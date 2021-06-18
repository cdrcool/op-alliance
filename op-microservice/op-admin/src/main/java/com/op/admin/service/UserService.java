package com.op.admin.service;

import com.op.admin.dto.UserChangePasswordDTO;
import com.op.admin.dto.UserCreateDTO;
import com.op.admin.dto.UserListQueryDTO;
import com.op.admin.dto.UserUpdateDTO;
import com.op.admin.vo.UserVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 用户 Service
 *
 * @author cdrcool
 */
public interface UserService {

    /**
     * 创建用户
     *
     * @param createDTO 用户创建 dto
     * @return 初始密码
     */
    String create(UserCreateDTO createDTO);

    /**
     * 修改用户密码
     *
     * @param changePasswordDto 用户修改密码 dto
     */
    void changePassword(UserChangePasswordDTO changePasswordDto);

    /**
     * 更新用户
     *
     * @param updateDTO 用户更新 dto
     */
    void update(UserUpdateDTO updateDTO);

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
    UserVO findById(Integer id);

    /**
     * 分页查询用户
     *
     * @param pageable 分页对象
     * @param queryDTO 查询对象
     * @return 用户 vo 分页列表
     */
    Page<UserVO> queryPage(Pageable pageable, UserListQueryDTO queryDTO);

    /**
     * 启用/禁用用户
     *
     * @param id     用户id
     * @param enable 启用 or 禁用
     */
    void changeEnabled(Integer id, boolean enable);
}

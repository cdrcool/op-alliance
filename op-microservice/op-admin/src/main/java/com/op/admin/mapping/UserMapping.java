package com.op.admin.mapping;

import com.op.admin.dto.UserChangePasswordDto;
import com.op.admin.dto.UserCreateDto;
import com.op.admin.dto.UserUpdateDto;
import com.op.admin.entity.User;
import com.op.admin.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * 用户 mapping
 *
 * @author cdrcool
 */
@Mapper(componentModel = "spring")
public interface UserMapping {

    /**
     * 用户创建 dto -> 用户
     *
     * @param userCreateDto 用户创建 dto
     * @return 用户
     */
    User toUser(UserCreateDto userCreateDto);

    /**
     * 根据用户修改密码 dto 更新用户
     *
     * @param changePasswordDto 用户修改密码 dto
     * @param user              用户
     */
    void update(UserChangePasswordDto changePasswordDto, @MappingTarget User user);

    /**
     * 根据用户更新 dto 更新用户
     *
     * @param userUpdateDto 用户更新 dto
     * @param user          用户
     */
    void update(UserUpdateDto userUpdateDto, @MappingTarget User user);

    /**
     * 用户 -> 用户 vo
     *
     * @param user 用户
     * @return 用户 vo
     */
    UserVo toUserVo(User user);

    /**
     * 用户列表 -> 用户 vo 列表
     *
     * @param users 用户列表
     * @return 用户 vo 列表
     */
    List<UserVo> toUserVoList(List<User> users);
}

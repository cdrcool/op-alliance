package com.op.admin.mapping;

import com.op.admin.dto.UserChangePasswordDTO;
import com.op.admin.dto.UserCreateDTO;
import com.op.admin.dto.UserDTO;
import com.op.admin.dto.UserUpdateDTO;
import com.op.admin.entity.User;
import com.op.admin.vo.UserVO;
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
     * @param createDTO 用户创建 dto
     * @return 用户
     */
    User toUser(UserCreateDTO createDTO);

    /**
     * 根据用户修改密码 dto 更新用户
     *
     * @param changePasswordDto 用户修改密码 dto
     * @param user              用户
     */
    void update(UserChangePasswordDTO changePasswordDto, @MappingTarget User user);

    /**
     * 根据用户更新 dto 更新用户
     *
     * @param updateDTO 用户更新 dto
     * @param user      用户
     */
    void update(UserUpdateDTO updateDTO, @MappingTarget User user);

    /**
     * 用户 -> 用户 vo
     *
     * @param user 用户
     * @return 用户 vo
     */
    UserVO toUserVo(User user);

    /**
     * 用户列表 -> 用户 vo 列表
     *
     * @param users 用户列表
     * @return 用户 vo 列表
     */
    List<UserVO> toUserVOList(List<User> users);

    /**
     * 用户 -> 用户 dto
     *
     * @param user 用户
     * @return 用户 dto
     */
    UserDTO toUserDto(User user);
}

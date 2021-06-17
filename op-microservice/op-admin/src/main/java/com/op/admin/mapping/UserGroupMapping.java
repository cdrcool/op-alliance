package com.op.admin.mapping;

import com.op.admin.dto.UserGroupSaveDTO;
import com.op.admin.entity.UserGroup;
import com.op.admin.vo.UserGroupVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * 用户组 mapping
 *
 * @author cdrcool
 */
@Mapper(componentModel = "spring")
public interface UserGroupMapping {

    /**
     * 用户组保存 dto -> 用户组
     *
     * @param saveDTO 用户组保存 dto
     * @return 用户组
     */
    UserGroup toUserGroup(UserGroupSaveDTO saveDTO);

    /**
     * 根据用户组保存 dto 更新用户组
     *
     * @param saveDTO          用户组保存 dto
     * @param userGroup 用户组
     */
    void update(UserGroupSaveDTO saveDTO, @MappingTarget UserGroup userGroup);

    /**
     * 用户组 -> 用户组 vo
     *
     * @param userGroup 用户组
     * @return 用户组 vo
     */
    UserGroupVO toUserGroupVO(UserGroup userGroup);

    /**
     * 用户组列表 -> 用户组 vo 列表
     *
     * @param userGroups 用户组列表
     * @return 用户组 vo 列表
     */
    List<UserGroupVO> toUserGroupVOList(List<UserGroup> userGroups);
}

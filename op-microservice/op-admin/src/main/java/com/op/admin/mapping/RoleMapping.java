package com.op.admin.mapping;

import com.op.admin.dto.RoleSaveDTO;
import com.op.admin.entity.Role;
import com.op.admin.vo.RoleAssignVO;
import com.op.admin.vo.RoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * 角色 mapping
 *
 * @author cdrcool
 */
@Mapper(componentModel = "spring")
public interface RoleMapping {

    /**
     * 角色保存 dto -> 角色
     *
     * @param saveDTO 角色保存 dto
     * @return 角色
     */
    Role toRole(RoleSaveDTO saveDTO);

    /**
     * 根据角色保存 dto 更新角色
     *
     * @param saveDTO          角色保存 dto
     * @param role 角色
     */
    void update(RoleSaveDTO saveDTO, @MappingTarget Role role);

    /**
     * 角色 -> 角色 vo
     *
     * @param userGroup 角色
     * @return 角色 vo
     */
    RoleVO toRoleVO(Role userGroup);

    /**
     * 角色列表 -> 角色 vo 列表
     *
     * @param roles 角色列表
     * @return 角色 vo 列表
     */
    List<RoleVO> toRoleVOList(List<Role> roles);

    /**
     * 角色列表 -> 角色分配 vo 列表
     *
     * @param roles 角色列表
     * @return 角色分配 vo 列表
     */
    List<RoleAssignVO> toRoleAssignVOList(List<Role> roles);
}

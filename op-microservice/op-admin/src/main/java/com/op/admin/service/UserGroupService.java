package com.op.admin.service;

import com.op.admin.dto.UserGroupPageQueryDTO;
import com.op.admin.dto.UserGroupSaveDTO;
import com.op.admin.vo.MenuAssignVO;
import com.op.admin.vo.ResourceCategoryAssignVO;
import com.op.admin.vo.RoleAssignVO;
import com.op.admin.vo.UserGroupVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 用户组 Service
 *
 * @author cdrcool
 */
public interface UserGroupService {

    /**
     * 保存用户组
     *
     * @param saveDTO 用户组保存 dto
     */
    void save(UserGroupSaveDTO saveDTO);

    /**
     * 删除用户组
     *
     * @param id 用户组 id
     */
    void deleteById(Integer id);

    /**
     * 批量删除用户组
     *
     * @param ids 用户组 ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 查找用户组
     *
     * @param id 用户组 id
     * @return 用户组 vo
     */
    UserGroupVO findById(Integer id);

    /**
     * 分页查询用户组
     *
     * @param pageable 分页对象
     * @param queryDTO 查询对象
     * @return 用户组 vo 分页列表
     */
    Page<UserGroupVO> queryPage(Pageable pageable, UserGroupPageQueryDTO queryDTO);

    /**
     * 分配角色
     *
     * @param id      用户组 id
     * @param roleIds 角色 ids
     */
    void assignRoles(Integer id, List<Integer> roleIds);

    /**
     * 分配资源动作
     *
     * @param id                用户组 id
     * @param resourceActionIds 资源动作 ids
     */
    void assignResourceActions(Integer id, List<Integer> resourceActionIds);

    /**
     * 分配菜单
     *
     * @param id      用户组 id
     * @param menuIds 菜单 ids
     */
    void assignMenus(Integer id, List<Integer> menuIds);

    /**
     * 获取用户组所分配的角色 ids
     *
     * @param ids 用户组 ids
     * @return 角色 ids
     */
    List<Integer> getAssignedRoleIds(List<Integer> ids);

    /**
     * 获取用户组所分配的资源 ids
     *
     * @param ids 用户组 ids
     * @return 资源 ids
     */
    List<Integer> getAssignedResourceActionIds(List<Integer> ids);

    /**
     * 获取用户组所分配的菜单 ids
     *
     * @param ids 用户组 ids
     * @return 菜单 ids
     */
    List<Integer> getAssignedMenuIds(List<Integer> ids);

    /**
     * 查找所有角色，以及用户组分配情况
     *
     * @param id 用户组 id
     * @return 角色分配 VO 列表
     */
    List<RoleAssignVO> loadRoles(Integer id);

    /**
     * 查找所有资源，以及用户组分配情况
     *
     * @param id 用户组 id
     * @return 资源分类分配 VO 列表
     */
    List<ResourceCategoryAssignVO> loadResources(Integer id);

    /**
     * 查找所有菜单，以及用户组分配情况
     *
     * @param id 用户组 id
     * @return 菜单分配 VO 列表
     */
    List<MenuAssignVO> loadMenus(Integer id);
}

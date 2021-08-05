package com.op.admin.service;

import com.op.admin.dto.UserGroupPageQueryDTO;
import com.op.admin.dto.UserGroupSaveDTO;
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
     * 获取角色分配情况
     *
     * @param id 用户组 id
     * @return 角色分配 VO 列表
     */
    List<RoleAssignVO> loadAssignedRoles(Integer id);

    /**
     * 获取分配的角色 ids
     *
     * @param ids 用户组 ids
     * @param status 角色启用状态
     * @return 角色 ids
     */
    List<Integer> getAssignedRoleIds(List<Integer> ids, Integer status);

    /**
     * 分配资源动作
     *
     * @param id                用户组 id
     * @param resourceActionIds 资源动作 ids
     */
    void assignResourceActions(Integer id, List<Integer> resourceActionIds);

    /**
     * 获取资源分配情况
     *
     * @param id 用户组 id
     * @return 资源分类分配 VO 列表
     */
    List<ResourceCategoryAssignVO> loadAssignedResources(Integer id);

    /**
     * 获取分配的资源动作 ids
     *
     * @param ids 用户组 ids
     * @return 资源 ids
     */
    List<Integer> getAssignedResourceActionIds(List<Integer> ids);
}

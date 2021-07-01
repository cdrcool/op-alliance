package com.op.admin.service;

import com.op.admin.dto.OrganizationListQueryDTO;
import com.op.admin.dto.OrganizationSaveDTO;
import com.op.admin.dto.OrganizationTreeQueryDTO;
import com.op.admin.server.vo.*;
import com.op.admin.vo.*;

import java.util.List;

/**
 * 组织 Service
 *
 * @author cdrcool
 */
public interface OrganizationService {

    /**
     * 保存组织
     *
     * @param saveDTO 组织保存 dto
     */
    void save(OrganizationSaveDTO saveDTO);

    /**
     * 删除组织
     *
     * @param id 组织 id
     */
    void deleteById(Integer id);

    /**
     * 查找组织
     *
     * @param id 组织 id
     * @return 组织 vo
     */
    OrganizationVO findById(Integer id);

    /**
     * 查询组织树
     *
     * @param queryDTO 查询对象
     * @return 组织树 vo
     */
    OrganizationTreeVO queryTree(OrganizationTreeQueryDTO queryDTO);

    /**
     * 查询组织列表
     *
     * @param queryDTO 查询对象
     * @return 组织 vo 列表
     */
    List<OrganizationVO> queryList(OrganizationListQueryDTO queryDTO);

    /**
     * 分配角色
     *
     * @param id      组织 id
     * @param roleIds 角色 ids
     */
    void assignRoles(Integer id, List<Integer> roleIds);

    /**
     * 分配资源动作
     *
     * @param id                组织 id
     * @param resourceActionIds 资源动作 ids
     */
    void assignResourceActions(Integer id, List<Integer> resourceActionIds);

    /**
     * 分配菜单
     *
     * @param id      组织 id
     * @param menuIds 菜单 ids
     */
    void assignMenus(Integer id, List<Integer> menuIds);

    /**
     * 获取组织所分配的角色 ids
     *
     * @param id 组织 id
     * @return 角色 ids
     */
    List<Integer> getAssignedRoleIds(Integer id);

    /**
     * 获取组织所分配的资源动作 ids
     *
     * @param id 组织 id
     * @return 资源动作 ids
     */
    List<Integer> getAssignedResourceActionIds(Integer id);

    /**
     * 获取组织所分配的菜单 ids
     *
     * @param id 组织 id
     * @return 菜单 ids
     */
    List<Integer> getAssignedMenuIds(Integer id);

    /**
     * 查找所有角色，以及组织分配情况
     *
     * @param id 组织 id
     * @return 角色分配 VO 列表
     */
    List<RoleAssignVO> loadRoles(Integer id);

    /**
     * 查找所有资源，以及组织分配情况
     *
     * @param id 组织 id
     * @return 资源分类分配 VO 列表
     */
    List<ResourceCategoryAssignVO> loadResources(Integer id);

    /**
     * 查找所有菜单，以及组织分配情况
     *
     * @param id 组织 id
     * @return 菜单分配 VO 列表
     */
    List<MenuAssignVO> loadMenus(Integer id);
}

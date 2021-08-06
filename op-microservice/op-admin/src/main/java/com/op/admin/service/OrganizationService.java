package com.op.admin.service;

import com.op.admin.dto.OrganizationSaveDTO;
import com.op.admin.dto.OrganizationTreeQueryDTO;
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
     * 批量删除组织
     *
     * @param ids 组织 ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 查找组织
     *
     * @param id 组织 id
     * @return 组织 vo
     */
    OrganizationVO findById(Integer id);

    /**
     * 查询组织树列表
     *
     * @param queryDTO 查询对象
     * @return 组织树 vo 列表
     */
    List<OrganizationTreeVO> queryTreeList(OrganizationTreeQueryDTO queryDTO);

    /**
     * 分配角色
     *
     * @param id      组织 id
     * @param roleIds 角色 ids
     */
    void assignRoles(Integer id, List<Integer> roleIds);

    /**
     * 获取角色分配情况
     *
     * @param id 组织 id
     * @return 角色分配 VO 列表
     */
    List<RoleAssignVO> loadAssignedRoles(Integer id);

    /**
     * 获取分配的角色 ids（本上级）
     *
     * @param id     组织 id
     * @param status 角色启用状态
     * @return 角色 ids
     */
    List<Integer> getAssignedRoleIds(Integer id, Integer status);

    /**
     * 分配资源动作
     *
     * @param id                组织 id
     * @param resourceActionIds 资源动作 ids
     */
    void assignResourceActions(Integer id, List<Integer> resourceActionIds);

    /**
     * 获取资源分配情况
     *
     * @param id 组织 id
     * @return 资源分类分配 VO 列表
     */
    List<ResourceCategoryAssignVO> loadAssignedResources(Integer id);

    /**
     * 获取分配的资源动作 ids（本上级）
     *
     * @param id 组织 id
     * @return 资源动作 ids
     */
    List<Integer> getAssignedResourceActionIds(Integer id);

    /**
     * 查询组织异步树（平展的）
     *
     * @param queryDTO 查询对象
     * @return 组织树选择列表
     */
    List<TreeNodeVO> queryForAsyncTreeFlat(OrganizationTreeQueryDTO queryDTO);

    /**
     * 查询组织异步树
     *
     * @param queryDTO 查询对象
     * @return 组织树选择列表
     */
    List<TreeNodeVO> queryForAsyncTree(OrganizationTreeQueryDTO queryDTO);

    /**
     * 获取本上级 ids
     *
     * @param id 组织 id
     * @return 本上级 ids
     */
    List<Integer> getParentsIds(Integer id);

    /**
     * 获取本下级 ids
     *
     * @param id 组织 id
     * @return 本下级 ids
     */
    List<Integer> getChildrenIds(Integer id);
}

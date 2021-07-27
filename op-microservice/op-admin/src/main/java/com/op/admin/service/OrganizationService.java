package com.op.admin.service;

import com.op.admin.dto.OrganizationListQueryDTO;
import com.op.admin.dto.OrganizationSaveDTO;
import com.op.admin.dto.OrganizationTreeQueryDTO;
import com.op.admin.entity.Organization;
import com.op.admin.vo.*;
import com.op.admin.vo.*;
import org.apache.ibatis.annotations.Select;

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
     * 查询组织树
     *
     * @param queryDTO 查询对象
     * @return 组织树 vo
     */
    OrganizationTreeVO queryTree(OrganizationTreeQueryDTO queryDTO);

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
     * 获取组织角色分配情况
     *
     * @param id 组织 id
     * @return 角色分配 VO 列表
     */
    List<RoleAssignVO> loadRoles(Integer id);

    /**
     * 获取组织资源分配情况
     *
     * @param id 组织 id
     * @return 资源分类分配 VO 列表
     */
    List<ResourceCategoryAssignVO> loadAssignedResources(Integer id);

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

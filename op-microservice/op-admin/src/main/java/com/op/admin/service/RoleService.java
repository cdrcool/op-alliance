package com.op.admin.service;

import com.op.admin.dto.RoleSaveDTO;
import com.op.admin.dto.RolePageQueryDTO;
import com.op.admin.vo.RoleVO;
import com.op.admin.vo.ResourceCategoryAssignVO;
import com.op.admin.vo.RoleAssignVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 角色 Service
 *
 * @author cdrcool
 */
public interface RoleService {

    /**
     * 保存角色
     *
     * @param saveDTO 角色保存 dto
     */
    void save(RoleSaveDTO saveDTO);

    /**
     * 删除角色
     *
     * @param id 角色 id
     */
    void deleteById(Integer id);

    /**
     * 批量删除角色
     *
     * @param ids 角色 ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 查找角色
     *
     * @param id 角色 id
     * @return 角色 vo
     */
    RoleVO findById(Integer id);

    /**
     * 分页查询角色列表
     *
     * @param pageable 分页对象
     * @param queryDTO 查询对象
     * @return 角色 vo 分页列表
     */
    Page<RoleVO> queryPage(Pageable pageable, RolePageQueryDTO queryDTO);

    /**
     * 查找所有角色，用于分配角色使用
     *
     * @return 角色分配 VO 列表
     */
    List<RoleAssignVO> findAllForAssign();

    /**
     * 启用/禁用角色
     *
     * @param ids     角色 ids
     * @param enable 启用 or 禁用
     */
    void changeEnabled(List<Integer> ids, boolean enable);

    /**
     * 分配资源动作
     *
     * @param id                角色 id
     * @param resourceActionIds 资源动作 ids
     */
    void assignResourceActions(Integer id, List<Integer> resourceActionIds);

    /**
     * 获取角色所分配的资源动作 ids
     *
     * @param ids 角色 ids
     * @return 资源动作 ids
     */
    List<Integer> getAssignedResourceActionIds(List<Integer> ids);

    /**
     * 查找所有资源，以及角色分配情况
     *
     * @param id 角色 id
     * @return 资源分类分配 VO 列表
     */
    List<ResourceCategoryAssignVO> loadAssignedResources(Integer id);
}

package com.op.admin.service;

import com.op.admin.dto.OrganizationListQueryDTO;
import com.op.admin.dto.OrganizationTreeQueryDTO;
import com.op.admin.dto.OrganizationSaveDTO;
import com.op.admin.vo.OrganizationTreeVO;
import com.op.admin.vo.OrganizationVO;

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
     * @param id 组织id
     */
    void deleteById(Integer id);

    /**
     * 查找组织
     *
     * @param id 组织id
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
     * @param id 组织id
     * @param roleIds 角色 ids
     */
    void assignRoles(Integer id, List<Integer> roleIds);

    /**
     * 分配资源
     *
     * @param id 组织id
     * @param resourceActionIds 资源动作 ids
     */
    void assignResources(Integer id, List<Integer> resourceActionIds);

    /**
     * 分配菜单
     *
     * @param id 组织id
     * @param menuIds 菜单 ids
     */
    void assignMenus(Integer id, List<Integer> menuIds);
}

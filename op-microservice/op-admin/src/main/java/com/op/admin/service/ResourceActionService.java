package com.op.admin.service;

import com.op.admin.dto.ResourceActionPageQueryDTO;
import com.op.admin.dto.ResourceActionSaveDTO;
import com.op.admin.vo.ResourceActionAssignVO;
import com.op.admin.vo.ResourceActionVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 资源动作 Service
 *
 * @author cdrcool
 */
public interface ResourceActionService {

    /**
     * 保存资源动作
     *
     * @param saveDTO 资源动作保存 dto
     */
    void save(ResourceActionSaveDTO saveDTO);

    /**
     * 删除资源动作
     *
     * @param id 资源动作 id
     */
    void deleteById(Integer id);

    /**
     * 批量删除资源动作
     *
     * @param ids 资源动作 ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 删除资源下所有的资源动作
     *
     * @param resourceId 资源动作 id
     */
    void deleteByResourceId(Integer resourceId);

    /**
     * 查找资源动作
     *
     * @param id 资源动作 id
     * @return 资源动作 vo
     */
    ResourceActionVO findById(Integer id);

    /**
     * 根据资源 id 查找资源动作列表
     *
     * @param resourceId 资源 id
     * @return 资源动作 vo 列表
     */
    List<ResourceActionVO> findByResourceId(Integer resourceId);

    /**
     * 根据资源 id 查找资源动作列表的 ids
     *
     * @param resourceId 资源 id
     * @return 资源动作列表的 ids
     */
    List<Integer> findIdsByResourceId(Integer resourceId);


    /**
     * 根据资源 ids 查找资源动作列表 map
     *
     * @param resourceIds 资源 ids
     * @return 资源动作列表 map(key: 资源 id; value: 资源动作列表)
     */
    Map<Integer, List<ResourceActionVO>> findByResourceIds(List<Integer> resourceIds);

    /**
     * 分页查询资源动作
     *
     * @param pageable 分页对象
     * @param queryDTO 查询对象
     * @return 资源动作 vo 分页列表
     */
    Page<ResourceActionVO> queryPage(Pageable pageable, ResourceActionPageQueryDTO queryDTO);

    /**
     * 查找所有资源动作，用于分配资源使用
     *
     * @return 资源动作分配 VO Map（key：资源 id；value：资源动作列表）
     */
    Map<Integer, List<ResourceActionAssignVO>> findAllForAssign();

    /**
     * 根据 ids 获取权限列表
     *
     * @param ids ids
     * @return 权限列表
     */
    List<String> getPermissions(List<Integer> ids);
}

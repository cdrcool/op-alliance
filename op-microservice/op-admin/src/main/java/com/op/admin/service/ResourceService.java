package com.op.admin.service;

import com.op.admin.dto.ResourcePageQueryDTO;
import com.op.admin.dto.ResourceSaveDTO;
import com.op.admin.vo.ResourceAssignVO;
import com.op.admin.vo.ResourceVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 资源 Service
 *
 * @author cdrcool
 */
public interface ResourceService {

    /**
     * 保存资源
     *
     * @param saveDTO 资源保存 dto
     */
    void save(ResourceSaveDTO saveDTO);

    /**
     * 删除资源
     *
     * @param id 资源 id
     */
    void deleteById(Integer id);

    /**
     * 批量删除资源
     *
     * @param ids 资源 id
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 删除资源分类下所有的资源
     *
     * @param categoryId 资源分类 id
     */
    void deleteByCategoryId(Integer categoryId);

    /**
     * 查找资源
     *
     * @param id 资源 id
     * @return 资源 vo
     */
    ResourceVO findById(Integer id);

    /**
     * 分页查询资源
     *
     * @param pageable 分页对象
     * @param queryDTO 查询对象
     * @return 资源 vo 分页列表
     */
    Page<ResourceVO> queryPage(Pageable pageable, ResourcePageQueryDTO queryDTO);

    /**
     * 查找所有资源，用于分配资源使用
     *
     * @return 资源分配 VO Map（key：资源分类 id；value：资源列表）
     */
    Map<Integer, List<ResourceAssignVO>> findAllForAssign();
}

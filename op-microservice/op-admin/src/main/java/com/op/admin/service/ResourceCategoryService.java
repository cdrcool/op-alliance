package com.op.admin.service;

import com.op.admin.dto.ResourceCategoryPageQueryDTO;
import com.op.admin.dto.ResourceCategorySaveDTO;
import com.op.admin.vo.ResourceCategoryVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 资源分类 Service
 *
 * @author cdrcool
 */
public interface ResourceCategoryService {

    /**
     * 保存资源分类
     *
     * @param saveDTO 资源分类保存 dto
     */
    void save(ResourceCategorySaveDTO saveDTO);

    /**
     * 删除资源分类
     *
     * @param id 资源分类id
     */
    void deleteById(Integer id);

    /**
     * 查找资源分类
     *
     * @param id 资源分类id
     * @return 资源分类 vo
     */
    ResourceCategoryVO findById(Integer id);

    /**
     * 分页查询资源分类
     *
     * @param pageable 分页对象
     * @param queryDTO 查询对象
     * @return 资源分类 vo 分页列表
     */
    Page<ResourceCategoryVO> queryPage(Pageable pageable, ResourceCategoryPageQueryDTO queryDTO);
}

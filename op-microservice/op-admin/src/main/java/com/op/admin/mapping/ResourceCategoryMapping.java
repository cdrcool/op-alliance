package com.op.admin.mapping;

import com.op.admin.dto.ResourceCategorySaveDTO;
import com.op.admin.entity.ResourceCategory;
import com.op.admin.vo.ResourceCategoryAssignVO;
import com.op.admin.vo.ResourceCategoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * 资源分类 mapping
 *
 * @author cdrcool
 */
@Mapper(componentModel = "spring")
public interface ResourceCategoryMapping {

    /**
     * 资源分类保存 dto -> 资源分类
     *
     * @param saveDTO 资源分类保存 dto
     * @return 资源分类
     */
    ResourceCategory toResourceCategory(ResourceCategorySaveDTO saveDTO);

    /**
     * 根据资源分类保存 dto 更新资源分类
     *
     * @param saveDTO          资源分类保存 dto
     * @param resourceCategory 资源分类
     */
    void update(ResourceCategorySaveDTO saveDTO, @MappingTarget ResourceCategory resourceCategory);

    /**
     * 资源分类 -> 资源分类 vo
     *
     * @param resourceCategory 资源分类
     * @return 资源分类 vo
     */
    ResourceCategoryVO toResourceCategoryVO(ResourceCategory resourceCategory);

    /**
     * 资源分类列表 -> 资源分类 vo 列表
     *
     * @param resourceCategories 资源分类列表
     * @return 资源分类 vo 列表
     */
    List<ResourceCategoryVO> toResourceCategoryVOList(List<ResourceCategory> resourceCategories);

    /**
     * 资源分类列表 -> 资源分类分配 vo 列表
     *
     * @param resourceCategories 资源分类列表
     * @return 资源分类分配 vo 列表
     */
    List<ResourceCategoryAssignVO> toResourceCategoryAssignVOList(List<ResourceCategory> resourceCategories);
}

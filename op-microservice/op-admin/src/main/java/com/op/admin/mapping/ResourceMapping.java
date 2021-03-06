package com.op.admin.mapping;

import com.op.admin.dto.ResourceSaveDTO;
import com.op.admin.entity.Resource;
import com.op.admin.vo.ResourceAssignVO;
import com.op.admin.vo.ResourceVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * 资源 mapping
 *
 * @author cdrcool
 */
@Mapper(componentModel = "spring")
public interface ResourceMapping {

    /**
     * 资源保存 dto -> 资源
     *
     * @param saveDTO 资源保存 dto
     * @return 资源
     */
    Resource toResource(ResourceSaveDTO saveDTO);

    /**
     * 根据资源保存 dto 更新资源
     *
     * @param saveDTO  资源保存 dto
     * @param resource 资源
     */
    void update(ResourceSaveDTO saveDTO, @MappingTarget Resource resource);

    /**
     * 资源 -> 资源 vo
     *
     * @param resource 资源
     * @return 资源 vo
     */
    ResourceVO toResourceVO(Resource resource);

    /**
     * 资源列表 -> 资源 vo 列表
     *
     * @param resources 资源列表
     * @return 资源 vo 列表
     */
    List<ResourceVO> toResourceVOList(List<Resource> resources);

    /**
     * 资源列表 -> 资源分配 vo 列表
     *
     * @param resources 资源列表
     * @return 资源分配 vo 列表
     */
    List<ResourceAssignVO> toResourceAssignVOList(List<Resource> resources);
}

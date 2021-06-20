package com.op.admin.mapping;

import com.op.admin.dto.ResourceActionSaveDTO;
import com.op.admin.entity.ResourceAction;
import com.op.admin.vo.ResourceActionAssignVO;
import com.op.admin.vo.ResourceActionVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * 资源动作 mapping
 *
 * @author cdrcool
 */
@Mapper(componentModel = "spring")
public interface ResourceActionMapping {

    /**
     * 资源动作保存 dto -> 资源动作
     *
     * @param saveDTO 资源动作保存 dto
     * @return 资源动作
     */
    ResourceAction toResourceAction(ResourceActionSaveDTO saveDTO);

    /**
     * 根据资源动作保存 dto 更新资源动作
     *
     * @param saveDTO 资源动作保存 dto
     * @param resourceAction    资源动作
     */
    void update(ResourceActionSaveDTO saveDTO, @MappingTarget ResourceAction resourceAction);

    /**
     * 资源动作 -> 资源动作 vo
     *
     * @param menu 资源动作
     * @return 资源动作 vo
     */
    ResourceActionVO toResourceActionVO(ResourceAction menu);

    /**
     * 资源动作列表 -> 资源动作 vo 列表
     *
     * @param resourceActions 资源动作列表
     * @return 资源动作 vo 列表
     */
    List<ResourceActionVO> toResourceActionVOList(List<ResourceAction> resourceActions);

    /**
     * 资源动作列表 -> 资源动作分配 vo 列表
     *
     * @param resourceActions 资源动作列表
     * @return 资源动作分配 vo 列表
     */
    List<ResourceActionAssignVO> toResourceActionAssignVOVOList(List<ResourceAction> resourceActions);
}

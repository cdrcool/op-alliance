package com.op.admin.mapping;

import com.op.admin.dto.OrganizationSaveDTO;
import com.op.admin.entity.Organization;
import com.op.admin.vo.OrganizationVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * 组织 mapping
 *
 * @author cdrcool
 */
@Mapper(componentModel = "spring")
public interface OrganizationMapping {

    /**
     * 组织保存 dto -> 组织
     *
     * @param saveDTO 组织保存 dto
     * @return 组织
     */
    Organization toOrganization(OrganizationSaveDTO saveDTO);

    /**
     * 根据组织保存 dto 更新组织
     *
     * @param saveDTO          组织保存 dto
     * @param organization 组织
     */
    void update(OrganizationSaveDTO saveDTO, @MappingTarget Organization organization);

    /**
     * 组织 -> 组织 vo
     *
     * @param organization 组织
     * @return 组织 vo
     */
    OrganizationVO toOrganizationVO(Organization organization);

    /**
     * 组织列表 -> 组织 vo 列表
     *
     * @param organizations 组织列表
     * @return 组织 vo 列表
     */
    List<OrganizationVO> toOrganizationVOList(List<Organization> organizations);
}

package com.op.admin.mapping;

import com.op.admin.dto.OrganizationSaveDTO;
import com.op.admin.entity.Organization;
import com.op.admin.vo.OrganizationTreeVO;
import com.op.admin.vo.OrganizationVO;
import com.op.admin.vo.TreeNodeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Objects;

/**
 * 组织 mapping
 *
 * @author cdrcool
 */
@Mapper(componentModel = "spring", imports = {Objects.class})
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
     * @param saveDTO      组织保存 dto
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

    /**
     * 组织 -> 组织树 vo
     *
     * @param organization 组织
     * @return 组织树 vo
     */
    OrganizationTreeVO toOrganizationTreeVO(Organization organization);

    /**
     * 组织 -> 树节点 vo
     *
     * @param organization 组织
     * @return 树节点 vo
     */
    @Mappings({
            @Mapping(source = "id", target = "key"),
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "pid", target = "pid"),
            @Mapping(source = "orgName", target = "title"),
            @Mapping(source = "id", target = "value"),
            @Mapping(target = "isLeaf", expression = "java(Objects.equals(organization.getOrgType(), 5))"),
    })
    TreeNodeVO toTreeNodeVO(Organization organization);

    /**
     * 组织列表 -> 树节点 vo 列表
     *
     * @param organizations 组织列表
     * @return 树节点 vo 列表
     */
    List<TreeNodeVO> toTreeNodeVOList(List<Organization> organizations);
}

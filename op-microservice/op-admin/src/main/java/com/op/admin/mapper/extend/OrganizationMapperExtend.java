package com.op.admin.mapper.extend;

import com.op.admin.entity.Organization;
import com.op.admin.mapper.OrganizationMapper;
import com.op.admin.vo.MenuVO;
import com.op.admin.vo.OrganizationVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

/**
 * 组织 Mapper Extend
 *
 * @author chengdr01
 */
@Mapper
public interface OrganizationMapperExtend extends OrganizationMapper {

    /**
     * 根据 id 查找组织
     *
     * @param id 组织 id
     * @return 组织 VO
     */
    @Select(" SELECT o.*, po.org_name AS parent_name FROM admin_organization o" +
            " LEFT JOIN admin_organization po ON po.id = o.pid" +
            " WHERE o.id = #{id}")
    Optional<OrganizationVO> findById(Integer id);

    /**
     * 获取本上级 ids
     *
     * @param id 组织 id
     * @return 本上级 ids
     */
    @Select(" SELECT id FROM admin_organization\n" +
            " WHERE (SELECT org_code_link FROM admin_organization WHERE id = #{id}) LIKE  CONCAT(org_code_link, '%')")
    List<Integer> getParentsIds(Integer id);

    /**
     * 获取本下级 ids
     *
     * @param id 组织 id
     * @return 本下级 ids
     */
    @Select(" SELECT id FROM admin_organization\n" +
            " WHERE org_code_link LIKE  CONCAT((SELECT org_code_link FROM admin_organization WHERE id = #{id}), '%')")
    List<Integer> getChildrenIds(Integer id);

    /**
     * 根据组织编码链获取链路中所有组织
     *
     * @param orgCodeLink 组织编码链
     * @return 链路中所有组织
     */
    @Select(" SELECT * FROM admin_organization" +
            " WHERE org_code_link LIKE CONCAT(#{orgCodeLink}, '%') OR #{orgCodeLink} LIKE CONCAT(org_code_link, '%') ")
    List<Organization> findAllInPath(String orgCodeLink);
}

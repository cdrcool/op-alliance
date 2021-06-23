package com.op.admin.mapper.extend;

import com.op.admin.mapper.OrganizationMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 组织 Mapper Extend
 *
 * @author chengdr01
 */
@Mapper
public interface OrganizationMapperExtend extends OrganizationMapper {

    /**
     * 获取本上级 ids
     *
     * @param id 组织 id
     * @return 本上级 ids
     */
    @Delete(" SELECT o1.id FROM admin_organization o1" +
            " INNER JOIN admin_organization o2 ON FIND_INSET(o1.org_code, o2.org_code_link)" +
            " WHERE 02.id = #{id}")
    List<Integer> getParentsIds(Integer id);

    /**
     * 获取本下级 ids
     *
     * @param id 组织 id
     * @return 本下级 ids
     */
    @Delete(" SELECT o1.id FROM admin_organization o1" +
            " INNER JOIN admin_organization o2 ON FIND_INSET(o2.org_code, o1.org_code_link)" +
            " WHERE 02.id = #{id}")
    List<Integer> getChildrenIds(Integer id);
}

package com.op.admin.mapper.extend;

import com.op.admin.dto.ResourcePathPermissionDto;
import com.op.admin.mapper.ResourceActionMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 资源动作 Mapper Extend
 *
 * @author chengdr01
 */
@Mapper
public interface ResourceActionMapperExtend extends ResourceActionMapper {

    /**
     * 查询资源路径及权限列表
     *
     * @return 本上级 ids
     */
    @Select(" SELECT rc.server_name, r.resource_path, ra.action_path, ra.permission" +
            " FROM admin_resource_action ra" +
            " INNER JOIN admin_resource r ON r.id = ra.resource_id" +
            " INNER JOIN admin_resource_category rc ON rc.id = r.category_id")
    List<ResourcePathPermissionDto> queryPathPermissions();
}

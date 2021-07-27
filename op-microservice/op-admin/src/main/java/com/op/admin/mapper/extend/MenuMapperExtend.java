package com.op.admin.mapper.extend;

import com.op.admin.mapper.MenuMapper;
import com.op.admin.vo.MenuVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

/**
 * 菜单 Mapper Extend
 *
 * @author chengdr01
 */
@Mapper
public interface MenuMapperExtend extends MenuMapper {

    /**
     * 根据 id 查找菜单
     *
     * @param id 菜单 id
     * @return 菜单 VO
     */
    @Select(" SELECT m.*, pm.menu_name AS parent_name FROM admin_menu m" +
            " LEFT JOIN admin_menu pm ON pm.id = m.pid" +
            " WHERE m.id = #{id}")
    Optional<MenuVO> findById(Integer id);

    /**
     * 删除菜单及其子菜单列表
     *
     * @param id 菜单 id
     */
    @Delete("DELETE FROM admin_menu WHERE id = #{id} OR FIND_IN_SET(#{id}, parent_ids)")
    void deleteById(Integer id);
}

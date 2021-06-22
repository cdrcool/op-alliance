package com.op.admin.mapper.extend;

import com.op.admin.mapper.MenuMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单 Mapper Extend
 *
 * @author chengdr01
 */
@Mapper
public interface MenuMapperExtend extends MenuMapper {

    /**
     * 删除菜单
     *
     * @param id 菜单 id
     */
    @Delete("DELETE FROM admin_menu WHERE id = #{id} OR FIND_INSET(#{id}, parentIds)")
    void deleteById(Integer id);
}
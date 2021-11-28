package com.op.samples.tenant.mapper;

import com.op.samples.tenant.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Mybatis Generator
 * @date 2021/08/18 10:31
 */
@Mapper
public interface MenuMapper {

    /**
     * 查找所有菜单
     *
     * @return 菜单列表
     */
    @Select("select * from admin_menu")
    List<Menu> selectAll();
}

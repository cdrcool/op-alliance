package com.op.admin.mapping;

import com.op.admin.dto.MenuSaveDTO;
import com.op.admin.entity.Menu;
import com.op.admin.entity.ResourceCategory;
import com.op.admin.vo.MenuTreeVO;
import com.op.admin.vo.MenuVO;
import com.op.admin.vo.SelectOptionVO;
import com.op.admin.vo.TreeNodeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 菜单 mapping
 *
 * @author cdrcool
 */
@Mapper(componentModel = "spring")
public interface MenuMapping {

    /**
     * 菜单保存 dto -> 菜单
     *
     * @param saveDTO 菜单保存 dto
     * @return 菜单
     */
    Menu toMenu(MenuSaveDTO saveDTO);

    /**
     * 根据菜单保存 dto 更新菜单
     *
     * @param saveDTO 菜单保存 dto
     * @param menu    菜单
     */
    void update(MenuSaveDTO saveDTO, @MappingTarget Menu menu);

    /**
     * 菜单 -> 菜单 vo
     *
     * @param menu 菜单
     * @return 菜单 vo
     */
    MenuVO toMenuVO(Menu menu);

    /**
     * 菜单列表 -> 菜单 vo 列表
     *
     * @param menus 菜单列表
     * @return 菜单 vo 列表
     */
    List<MenuVO> toMenuVOList(List<Menu> menus);

    /**
     * 菜单 -> 菜单树 vo
     *
     * @param menu 菜单
     * @return 菜单树 vo
     */
    MenuTreeVO toMenuTreeVO(Menu menu);

    /**
     * 菜单 -> 树节点 vo
     *
     * @param menu 菜单
     * @return 树节点 vo
     */
    @Mappings({
            @Mapping(source = "menuName", target = "title"),
            @Mapping(source = "id", target = "value")
    })
    TreeNodeVO toTreeNodeVO(Menu menu);
}

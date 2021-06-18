package com.op.admin.service;

import com.op.admin.dto.MenuListQueryDTO;
import com.op.admin.dto.MenuSaveDTO;
import com.op.admin.vo.MenuTreeVO;
import com.op.admin.vo.MenuVO;

import java.util.List;

/**
 * 菜单 Service
 *
 * @author cdrcool
 */
public interface MenuService {

    /**
     * 保存菜单
     *
     * @param saveDTO 菜单保存 dto
     */
    void save(MenuSaveDTO saveDTO);

    /**
     * 删除菜单
     *
     * @param id 菜单id
     */
    void deleteById(Integer id);

    /**
     * 查找菜单
     *
     * @param id 菜单id
     * @return 菜单 vo
     */
    MenuVO findById(Integer id);

    /**
     * 查询菜单树列表
     *
     * @param queryDTO 查询对象
     * @return 菜单树 vo 列表
     */
    List<MenuTreeVO> queryTreeList(MenuListQueryDTO queryDTO);

    /**
     * 显示/隐藏菜单
     *
     * @param id   菜单id
     * @param show 显示 or 隐藏
     */
    void changeVisibility(Integer id, boolean show);
}

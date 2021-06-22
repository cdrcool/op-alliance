package com.op.admin.service;

import com.op.admin.dto.MenuListQueryDTO;
import com.op.admin.dto.MenuSaveDTO;
import com.op.admin.vo.MenuAssignVO;
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
     * @param id 菜单 id
     */
    void deleteById(Integer id);

    /**
     * 查找菜单
     *
     * @param id 菜单 id
     * @return 菜单 vo
     */
    MenuVO findById(Integer id);

    /**
     * 查询菜单树列表
     *
     * @return 菜单树 vo 列表
     */
    List<MenuTreeVO> queryTreeList();

    /**
     * 查询菜单列表
     *
     * @param queryDTO 查询对象
     * @return 菜单 vo 列表
     */
    List<MenuVO> queryList(MenuListQueryDTO queryDTO);

    /**
     * 查找所有菜单，用于分配菜单使用
     *
     * @return 菜单分配 VO 列表
     */
    List<MenuAssignVO> findAllForAssign();

    /**
     * 显示/隐藏菜单
     *
     * @param id   菜单 id
     * @param show 显示 or 隐藏
     */
    void changeVisibility(Integer id, boolean show);
}
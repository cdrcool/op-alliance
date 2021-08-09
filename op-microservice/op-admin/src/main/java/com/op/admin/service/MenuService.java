package com.op.admin.service;

import com.op.admin.dto.MenuSaveDTO;
import com.op.admin.dto.MenuTreeListQueryDTO;
import com.op.admin.vo.MenuTreeVO;
import com.op.admin.vo.MenuVO;
import com.op.admin.vo.TreeNodeVO;

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
     * @return 菜单 vo
     */
    MenuVO save(MenuSaveDTO saveDTO);

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
     * 查询用户菜单树列表
     *
     * @return 菜单树 vo 列表
     */
    List<MenuTreeVO> queryUserTreeList();

    /**
     * 查询菜单树列表
     *
     * @param queryDTO 查询对象
     * @return 菜单树 vo 列表
     */
    List<MenuTreeVO> queryTreeList(MenuTreeListQueryDTO queryDTO);

    /**
     * 查询菜单列表
     *
     * @param keyword 关键字
     * @return 菜单 vo 列表
     */
    List<MenuVO> queryList(String keyword);

    /**
     * 显示/隐藏菜单
     *
     * @param id   菜单 id
     * @param show 显示 or 隐藏
     * @return 菜单 vo
     */
    MenuVO changeVisibility(Integer id, boolean show);

    /**
     * 查询菜单树选择列表
     *
     * @param queryDTO 查询对象
     * @return 菜单树选择列表
     */
    List<TreeNodeVO> queryForAsyncTreeFlat(MenuTreeListQueryDTO queryDTO);
}

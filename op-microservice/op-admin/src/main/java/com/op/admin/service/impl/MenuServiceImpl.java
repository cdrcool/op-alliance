package com.op.admin.service.impl;

import com.op.admin.dto.MenuSaveDTO;
import com.op.admin.dto.MenuTreeListQueryDTO;
import com.op.admin.entity.Menu;
import com.op.admin.mapper.MenuDynamicSqlSupport;
import com.op.admin.mapper.extend.MenuMapperExtend;
import com.op.admin.mapping.MenuMapping;
import com.op.admin.service.MenuService;
import com.op.admin.utils.TreeUtils;
import com.op.admin.vo.MenuTreeVO;
import com.op.admin.vo.MenuVO;
import com.op.admin.vo.TreeNodeVO;
import com.op.framework.web.common.api.response.ResultCode;
import com.op.framework.web.common.api.response.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

/**
 * 菜单 Service Impl
 *
 * @author cdrcool
 */
@CacheConfig(cacheNames = "menus")
@Service
public class MenuServiceImpl implements MenuService {
    private final MenuMapperExtend menuMapper;
    private final MenuMapping menuMapping;

    public MenuServiceImpl(MenuMapperExtend menuMapper, MenuMapping menuMapping) {
        this.menuMapper = menuMapper;
        this.menuMapping = menuMapping;
    }

    @CachePut(key = "#result.id")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public MenuVO save(MenuSaveDTO saveDTO) {
        saveDTO.setPid(Optional.ofNullable(saveDTO.getPid()).orElse(-1));

        // 校验同一菜单下，子菜单名称是否重复
        validateMenuName(saveDTO.getPid(), saveDTO.getId(), saveDTO.getMenuName());

        if (saveDTO.getId() == null) {
            Menu menu = menuMapping.toMenu(saveDTO);
            setMenuProps(menu, saveDTO.getPid());
            menuMapper.insert(menu);

            return menuMapping.toMenuVO(menu);
        } else {
            Integer id = saveDTO.getId();
            Menu menu = menuMapper.selectByPrimaryKey(id)
                    .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的菜单"));
            menuMapping.update(saveDTO, menu);
            setMenuProps(menu, saveDTO.getPid());
            menuMapper.updateByPrimaryKey(menu);

            return menuMapping.toMenuVO(menu);
        }
    }

    /**
     * 校验同一菜单下，子菜单名称是否重复
     *
     * @param pid      父 id
     * @param id       主键
     * @param menuName 菜单名称
     */
    private void validateMenuName(Integer pid, Integer id, String menuName) {
        SelectStatementProvider selectStatementProvider = countFrom(MenuDynamicSqlSupport.menu)
                .where(MenuDynamicSqlSupport.pid, isEqualTo(pid))
                .and(MenuDynamicSqlSupport.menuName, isEqualTo(menuName))
                .and(MenuDynamicSqlSupport.id, isNotEqualToWhenPresent(id))
                .build().render(RenderingStrategies.MYBATIS3);
        long count = menuMapper.count(selectStatementProvider);
        if (count > 0) {
            throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "同一菜单下，已存在相同菜单名称的子菜单，菜单名称不能重复");
        }
    }

    /**
     * 设置菜单 pid、parentIds、menuLevel 等属性
     *
     * @param menu 菜单
     * @param pid  父菜单id
     */
    private void setMenuProps(Menu menu, Integer pid) {
        if (pid == -1) {
            menu.setMenuLevel(1);
        } else {
            Menu pMenu = menuMapper.selectByPrimaryKey(pid)
                    .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + pid + "】的父菜单"));
            String parentIds = pMenu.getParentIds();
            parentIds = StringUtils.isNotBlank(parentIds) ? parentIds + "," + pid : String.valueOf(pid);
            menu.setParentIds(parentIds);
            menu.setMenuLevel(pMenu.getMenuLevel() + 1);
        }
        menu.setMenuNo(Optional.ofNullable(menu.getMenuNo()).orElse(999));
    }

    @CacheEvict(key = "#id", beforeInvocation = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        // 删除菜单及其子菜单列表
        menuMapper.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Integer> ids) {
        ids.forEach(this::deleteById);
    }

    @Cacheable(key = "#id", sync = true)
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public MenuVO findById(Integer id) {
        return menuMapper.findById(id)
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的菜单"));
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<MenuTreeVO> queryTreeList(MenuTreeListQueryDTO queryDTO) {
        String keyword = queryDTO.getKeyword();
        List<Menu> menus = menuMapper.select(SelectDSLCompleter.allRows());

        List<MenuTreeVO> treeList = TreeUtils.buildTreeRecursion(
                menus,
                Menu::getPid,
                Menu::getId,
                menuMapping::toMenuTreeVO,
                MenuTreeVO::setChildren,
                -1,
                menu -> StringUtils.isBlank(keyword) ||
                        Optional.ofNullable(menu.getMenuName()).orElse("").contains(keyword) ||
                        Optional.ofNullable(menu.getMenuPath()).orElse("").contains(keyword) ||
                        Optional.ofNullable(menu.getPermission()).orElse("").contains(keyword)
        );
        return CollectionUtils.isNotEmpty(treeList) ? treeList : new ArrayList<>();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MenuVO changeVisibility(Integer id, boolean show) {
        UpdateStatementProvider updateStatement = SqlBuilder.update(MenuDynamicSqlSupport.menu)
                .set(MenuDynamicSqlSupport.isHidden).equalTo(!show)
                .where(MenuDynamicSqlSupport.id, isEqualTo(id))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        menuMapper.update(updateStatement);

        return this.findById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changeVisibility(List<Integer> ids, boolean show) {
        UpdateStatementProvider updateStatement = SqlBuilder.update(MenuDynamicSqlSupport.menu)
                .set(MenuDynamicSqlSupport.isHidden).equalTo(!show)
                .where(MenuDynamicSqlSupport.id, isIn(ids))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        menuMapper.update(updateStatement);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<TreeNodeVO> queryTreeSelectList(MenuTreeListQueryDTO queryDTO) {
        String keyword = queryDTO.getKeyword();
        Integer id = queryDTO.getId();
        List<Menu> menus = id == null ? menuMapper.select(SelectDSLCompleter.allRows()) : menuMapper.findMenusNotChildren(id);

        List<TreeNodeVO> treeList = TreeUtils.buildTreeRecursion(
                menus,
                Menu::getPid,
                Menu::getId,
                menuMapping::toTreeNodeVO,
                TreeNodeVO::setChildren,
                -1,
                menu -> StringUtils.isBlank(keyword) ||
                        Optional.ofNullable(menu.getMenuName()).orElse("").contains(keyword) ||
                        Optional.ofNullable(menu.getMenuPath()).orElse("").contains(keyword) ||
                        Optional.ofNullable(menu.getPermission()).orElse("").contains(keyword)
        );
        return CollectionUtils.isNotEmpty(treeList) ? treeList : new ArrayList<>();
    }
}

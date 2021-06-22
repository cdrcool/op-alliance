package com.op.admin.service.impl;

import com.op.admin.dto.MenuListQueryDTO;
import com.op.admin.dto.MenuSaveDTO;
import com.op.admin.entity.Menu;
import com.op.admin.mapper.MenuDynamicSqlSupport;
import com.op.admin.mapper.MenuMapper;
import com.op.admin.mapper.extend.MenuMapperExtend;
import com.op.admin.mapping.MenuMapping;
import com.op.admin.service.MenuService;
import com.op.admin.utils.TreeUtils;
import com.op.admin.vo.MenuAssignVO;
import com.op.admin.vo.MenuTreeVO;
import com.op.admin.vo.MenuVO;
import com.op.framework.web.common.api.response.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

/**
 * 菜单 Service Impl
 *
 * @author cdrcool
 */
@Service
public class MenuServiceImpl implements MenuService {
    private final MenuMapperExtend menuMapper;
    private final MenuMapping menuMapping;

    public MenuServiceImpl(MenuMapperExtend menuMapper, MenuMapping menuMapping) {
        this.menuMapper = menuMapper;
        this.menuMapping = menuMapping;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(MenuSaveDTO saveDTO) {
        if (saveDTO.getId() == null) {
            Menu menu = menuMapping.toMenu(saveDTO);
            setMenuProps(menu, saveDTO.getPid());
            menuMapper.insert(menu);
        } else {
            Integer id = saveDTO.getId();
            Menu menu = menuMapper.selectByPrimaryKey(id).orElseThrow(() -> new BusinessException("找不到菜单，菜单id：" + id));
            setMenuProps(menu, saveDTO.getPid());
            menuMapping.update(saveDTO, menu);
            menuMapper.updateByPrimaryKey(menu);
        }
    }

    /**
     * 设置菜单 pid、parentIds、menuLevel 等属性
     *
     * @param menu 菜单
     * @param pid  父菜单id
     */
    private void setMenuProps(Menu menu, Integer pid) {
        if (pid == null) {
            menu.setPid(-1);
            menu.setParentIds("");
            menu.setMenuLevel(1);
        } else if (!pid.equals(menu.getPid())) {
            Menu pMenu = menuMapper.selectByPrimaryKey(pid)
                    .orElseThrow(() -> new BusinessException("找不到父菜单，父菜单id：" + pid));
            String parentIds = pMenu.getParentIds();
            parentIds = StringUtils.isNoneBlank(parentIds) ? parentIds + "," + pid : String.valueOf(pid);
            menu.setParentIds(parentIds);
            menu.setMenuLevel(pMenu.getMenuLevel() + 1);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        menuMapper.deleteById(id);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public MenuVO findById(Integer id) {
        Menu menu = menuMapper.selectByPrimaryKey(id).orElse(new Menu());
        return menuMapping.toMenuVO(menu);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<MenuTreeVO> queryTreeList() {
        List<Menu> menus = menuMapper.select(SelectDSLCompleter.allRows());
        return TreeUtils.buildTree(menus, menuMapping::toMenuTreeVO, MenuTreeVO::getPid, MenuTreeVO::getId,
                (menu, children) -> menu.setChildren(children.stream().sorted(Comparator.comparing(MenuTreeVO::getMenuNo)).collect(Collectors.toList())), -1);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<MenuVO> queryList(MenuListQueryDTO queryDTO) {
        SelectStatementProvider selectStatementProvider = select(MenuMapper.selectList)
                .from(MenuDynamicSqlSupport.menu)
                .where(MenuDynamicSqlSupport.menuName, isLikeWhenPresent(queryDTO.getSearchText()),
                        or(MenuDynamicSqlSupport.menuCode, isLikeWhenPresent(queryDTO.getSearchText())),
                        or(MenuDynamicSqlSupport.menuRoute, isLikeWhenPresent(queryDTO.getSearchText())))
                .build().render(RenderingStrategies.MYBATIS3);
        List<Menu> menus = menuMapper.selectMany(selectStatementProvider);

        return menuMapping.toMenuVOList(menus);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<MenuAssignVO> findAllForAssign() {
        List<Menu> menus = menuMapper.select(SelectDSLCompleter.allRows());
        return TreeUtils.buildTree(menus, menuMapping::toMenuAssignVO, MenuAssignVO::getPid, MenuAssignVO::getId,
                (menu, children) -> menu.setChildren(children.stream().sorted(Comparator.comparing(MenuAssignVO::getMenuNo)).collect(Collectors.toList())), -1);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public void changeVisibility(Integer id, boolean show) {
        UpdateStatementProvider updateStatement = SqlBuilder.update(MenuDynamicSqlSupport.menu)
                .set(MenuDynamicSqlSupport.isHidden).equalTo(!show)
                .where(MenuDynamicSqlSupport.id, isEqualTo(id))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        menuMapper.update(updateStatement);
    }
}

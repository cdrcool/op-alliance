package com.op.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.op.admin.dto.RolePageQueryDTO;
import com.op.admin.dto.RoleSaveDTO;
import com.op.admin.entity.Role;
import com.op.admin.entity.RoleMenuRelation;
import com.op.admin.entity.RoleResourceActionRelation;
import com.op.admin.mapper.*;
import com.op.admin.mapping.RoleMapping;
import com.op.admin.service.MenuService;
import com.op.admin.service.ResourceCategoryService;
import com.op.admin.service.RoleService;
import com.op.admin.vo.MenuAssignVO;
import com.op.admin.vo.ResourceCategoryAssignVO;
import com.op.admin.vo.RoleAssignVO;
import com.op.admin.vo.RoleVO;
import com.op.framework.web.common.api.response.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.SimpleSortSpecification;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

/**
 * 角色 Service Impl
 *
 * @author cdrcool
 */
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleMapper roleMapper;
    private final RoleMapping roleMapping;
    private final RoleResourceActionRelationMapper roleResourceActionRelationMapper;
    private final RoleMenuRelationMapper roleMenuRelationMapper;
    private final ResourceCategoryService resourceCategoryService;
    private final MenuService menuService;

    public RoleServiceImpl(RoleMapper roleMapper, RoleMapping roleMapping,
                           RoleResourceActionRelationMapper roleResourceActionRelationMapper,
                           RoleMenuRelationMapper roleMenuRelationMapper,
                           ResourceCategoryService resourceCategoryService,
                           MenuService menuService) {
        this.roleMapper = roleMapper;
        this.roleMapping = roleMapping;
        this.roleResourceActionRelationMapper = roleResourceActionRelationMapper;
        this.roleMenuRelationMapper = roleMenuRelationMapper;
        this.resourceCategoryService = resourceCategoryService;
        this.menuService = menuService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(RoleSaveDTO saveDTO) {
        if (saveDTO.getId() == null) {
            Role role = roleMapping.toRole(saveDTO);
            roleMapper.insert(role);
        } else {
            Integer id = saveDTO.getId();
            Role role = roleMapper.selectByPrimaryKey(id).orElseThrow(() -> new BusinessException("找不到角色，角色id：" + id));
            roleMapping.update(saveDTO, role);
            roleMapper.updateByPrimaryKey(role);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public RoleVO findById(Integer id) {
        Role role = roleMapper.selectByPrimaryKey(id).orElse(new Role());
        return roleMapping.toRoleVO(role);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Page<RoleVO> queryPage(Pageable pageable, RolePageQueryDTO queryDTO) {
        SortSpecification[] specifications = pageable.getSort().stream()
                .map(order -> {
                    SortSpecification specification = SimpleSortSpecification.of(order.getProperty());
                    if (order.isDescending()) {
                        return specification.descending();
                    }
                    return specification;
                }).toArray(SortSpecification[]::new);

        SelectStatementProvider selectStatementProvider = select(RoleMapper.selectList)
                .from(RoleDynamicSqlSupport.role)
                .where(RoleDynamicSqlSupport.roleName, isLikeWhenPresent(queryDTO.getSearchText()),
                        or(RoleDynamicSqlSupport.roleCode, isLikeWhenPresent(queryDTO.getSearchText())))
                .orderBy(specifications)
                .limit(pageable.getPageSize()).offset(pageable.getOffset())
                .build().render(RenderingStrategies.MYBATIS3);

        com.github.pagehelper.Page<RoleVO> result = PageHelper
                .startPage(pageable.getPageNumber(), pageable.getPageSize())
                .doSelectPage(() -> roleMapping.toRoleVOList(roleMapper.selectMany(selectStatementProvider)));

        return new PageImpl<>(result.getResult(), pageable, result.getTotal());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<RoleAssignVO> findAllForAssign() {
        SelectStatementProvider selectStatementProvider = select(RoleDynamicSqlSupport.id,
                RoleDynamicSqlSupport.roleName, RoleDynamicSqlSupport.roleCode)
                .from(RoleDynamicSqlSupport.role)
                .where(RoleDynamicSqlSupport.status, isEqualTo(1))
                .orderBy(RoleDynamicSqlSupport.roleNo)
                .build().render(RenderingStrategies.MYBATIS3);
        List<Role> roles = roleMapper.selectMany(selectStatementProvider);
        return roleMapping.toRoleAssignVOList(roles);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changeEnabled(Integer id, boolean enable) {
        UpdateStatementProvider updateStatement = SqlBuilder.update(RoleDynamicSqlSupport.role)
                .set(RoleDynamicSqlSupport.status).equalTo(enable ? 1 : 0)
                .where(RoleDynamicSqlSupport.id, isEqualTo(id))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        roleMapper.update(updateStatement);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignResources(Integer id, List<Integer> resourceActionIds) {
        // 获取已建立关联的资源动作ids
        List<Integer> preActionIds = getAssignedResourceActionIds(id);

        // 获取要新建关联的资源动作ids
        List<Integer> toAddActionIds = resourceActionIds.stream()
                .filter(actionId -> !preActionIds.contains(actionId)).collect(Collectors.toList());

        // 获取要删除关联的资源动作ids
        List<Integer> toDelActionIds = preActionIds.stream()
                .filter(actionId -> !resourceActionIds.contains(actionId)).collect(Collectors.toList());

        // 插入要新建的角色-资源动作关联
        List<RoleResourceActionRelation> relations = toAddActionIds.stream()
                .map(actionId -> {
                    RoleResourceActionRelation relation = new RoleResourceActionRelation();
                    relation.setRoleId(id);
                    relation.setActionId(actionId);

                    return relation;
                }).collect(Collectors.toList());
        relations.forEach(roleResourceActionRelationMapper::insert);

        // 删除要删除的角色-资源动作关联
        if (!CollectionUtils.isEmpty(toDelActionIds)) {
            DeleteStatementProvider deleteStatementProvider = deleteFrom(RoleResourceActionRelationDynamicSqlSupport.roleResourceActionRelation)
                    .where(RoleResourceActionRelationDynamicSqlSupport.roleId, isEqualTo(id))
                    .and(RoleResourceActionRelationDynamicSqlSupport.actionId, isIn(toDelActionIds))
                    .build().render(RenderingStrategies.MYBATIS3);
            roleResourceActionRelationMapper.delete(deleteStatementProvider);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignMenus(Integer id, List<Integer> menuIds) {
        // 获取已建立关联的菜单ids
        List<Integer> preMenuIds = getAssignedMenuIds(id);

        // 获取要新建关联的菜单ids
        List<Integer> toAddMenuIds = menuIds.stream()
                .filter(menuId -> !preMenuIds.contains(menuId)).collect(Collectors.toList());

        // 获取要删除关联的菜单ids
        List<Integer> toDelMenuIds = preMenuIds.stream()
                .filter(menuId -> !menuIds.contains(menuId)).collect(Collectors.toList());

        // 插入要新建的角色-菜单关联
        List<RoleMenuRelation> relations = toAddMenuIds.stream()
                .map(menuId -> {
                    RoleMenuRelation relation = new RoleMenuRelation();
                    relation.setRoleId(id);
                    relation.setMenuId(menuId);

                    return relation;
                }).collect(Collectors.toList());
        relations.forEach(roleMenuRelationMapper::insert);

        // 删除要删除的角色-菜单关联
        if (!CollectionUtils.isEmpty(toDelMenuIds)) {
            DeleteStatementProvider deleteStatementProvider = deleteFrom(RoleMenuRelationDynamicSqlSupport.roleMenuRelation)
                    .where(RoleMenuRelationDynamicSqlSupport.roleId, isEqualTo(id))
                    .and(RoleMenuRelationDynamicSqlSupport.menuId, isIn(toDelMenuIds))
                    .build().render(RenderingStrategies.MYBATIS3);
            roleMenuRelationMapper.delete(deleteStatementProvider);
        }
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> getAssignedResourceActionIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(RoleResourceActionRelationDynamicSqlSupport.actionId)
                .from(RoleResourceActionRelationDynamicSqlSupport.roleResourceActionRelation)
                .where(RoleResourceActionRelationDynamicSqlSupport.roleId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return roleResourceActionRelationMapper.selectMany(selectStatementProvider).stream()
                .map(RoleResourceActionRelation::getActionId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> getAssignedMenuIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(RoleMenuRelationDynamicSqlSupport.menuId)
                .from(RoleMenuRelationDynamicSqlSupport.roleMenuRelation)
                .where(RoleMenuRelationDynamicSqlSupport.roleId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return roleMenuRelationMapper.selectMany(selectStatementProvider).stream()
                .map(RoleMenuRelation::getMenuId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<ResourceCategoryAssignVO> loadResources(Integer id) {
        List<Integer> assignedActionIds = getAssignedResourceActionIds(id);

        List<ResourceCategoryAssignVO> categories = resourceCategoryService.findAllForAssign();
        categories.forEach(category ->
                category.getResources().forEach(resource ->
                        resource.getActions().forEach(action -> {
                            action.setChecked(assignedActionIds.contains(action.getId()));
                            action.setEnableUncheck(true);
                        })));

        return categories;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<MenuAssignVO> loadMenus(Integer id) {
        List<Integer> assignedMenuIds = getAssignedMenuIds(id);

        List<MenuAssignVO> menus = menuService.findAllForAssign();
        setMenuItems(menus, assignedMenuIds);

        return menus;
    }

    private void setMenuItems(List<MenuAssignVO> menus, List<Integer> assignedMenuIds) {
        menus.forEach(menu -> {
            menu.setChecked(assignedMenuIds.contains(menu.getId()));
            menu.setEnableUncheck(true);

            if (!CollectionUtils.isEmpty(menu.getChildren())) {
                setMenuItems(menu.getChildren(), assignedMenuIds);
            }
        });
    }
}

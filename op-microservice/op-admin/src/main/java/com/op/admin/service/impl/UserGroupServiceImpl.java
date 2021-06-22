package com.op.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.op.admin.dto.UserGroupPageQueryDTO;
import com.op.admin.dto.UserGroupSaveDTO;
import com.op.admin.entity.UserGroup;
import com.op.admin.entity.UserGroupMenuRelation;
import com.op.admin.entity.UserGroupResourceActionRelation;
import com.op.admin.entity.UserGroupRoleRelation;
import com.op.admin.mapper.*;
import com.op.admin.mapping.UserGroupMapping;
import com.op.admin.service.MenuService;
import com.op.admin.service.ResourceCategoryService;
import com.op.admin.service.RoleService;
import com.op.admin.service.UserGroupService;
import com.op.admin.vo.MenuAssignVO;
import com.op.admin.vo.ResourceCategoryAssignVO;
import com.op.admin.vo.RoleAssignVO;
import com.op.admin.vo.UserGroupVO;
import com.op.framework.web.common.api.response.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.SimpleSortSpecification;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

/**
 * 用户组 Service Impl
 *
 * @author cdrcool
 */
@Service
public class UserGroupServiceImpl implements UserGroupService {
    private final UserGroupMapper userGroupMapper;
    private final UserGroupMapping userGroupMapping;
    private final UserGroupRoleRelationMapper userGroupRoleRelationMapper;
    private final UserGroupResourceActionRelationMapper userGroupResourceActionRelationMapper;
    private final UserGroupMenuRelationMapper userGroupMenuRelationMapper;
    private final RoleService roleService;
    private final ResourceCategoryService resourceCategoryService;
    private final MenuService menuService;

    public UserGroupServiceImpl(UserGroupMapper userGroupMapper, UserGroupMapping userGroupMapping,
                                UserGroupRoleRelationMapper userGroupRoleRelationMapper,
                                UserGroupResourceActionRelationMapper userGroupResourceActionRelationMapper,
                                UserGroupMenuRelationMapper userGroupMenuRelationMapper, RoleService roleService, ResourceCategoryService resourceCategoryService, MenuService menuService) {
        this.userGroupMapper = userGroupMapper;
        this.userGroupMapping = userGroupMapping;
        this.userGroupRoleRelationMapper = userGroupRoleRelationMapper;
        this.userGroupResourceActionRelationMapper = userGroupResourceActionRelationMapper;
        this.userGroupMenuRelationMapper = userGroupMenuRelationMapper;
        this.roleService = roleService;
        this.resourceCategoryService = resourceCategoryService;
        this.menuService = menuService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(UserGroupSaveDTO saveDTO) {
        if (saveDTO.getId() == null) {
            UserGroup userGroup = userGroupMapping.toUserGroup(saveDTO);
            userGroupMapper.insert(userGroup);
        } else {
            Integer id = saveDTO.getId();
            UserGroup userGroup = userGroupMapper.selectByPrimaryKey(id).orElseThrow(() -> new BusinessException("找不到用户组，用户组id：" + id));
            userGroupMapping.update(saveDTO, userGroup);
            userGroupMapper.updateByPrimaryKey(userGroup);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        userGroupMapper.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public UserGroupVO findById(Integer id) {
        UserGroup userGroup = userGroupMapper.selectByPrimaryKey(id).orElse(new UserGroup());
        return userGroupMapping.toUserGroupVO(userGroup);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Page<UserGroupVO> queryPage(Pageable pageable, UserGroupPageQueryDTO queryDTO) {
        SortSpecification[] specifications = pageable.getSort().stream()
                .map(order -> {
                    SortSpecification specification = SimpleSortSpecification.of(order.getProperty());
                    if (order.isDescending()) {
                        return specification.descending();
                    }
                    return specification;
                }).toArray(SortSpecification[]::new);

        SelectStatementProvider selectStatementProvider = select(UserGroupMapper.selectList)
                .from(UserGroupDynamicSqlSupport.userGroup)
                .where(UserGroupDynamicSqlSupport.groupName, isLikeWhenPresent(queryDTO.getSearchText()))
                .orderBy(specifications)
                .limit(pageable.getPageSize()).offset(pageable.getOffset())
                .build().render(RenderingStrategies.MYBATIS3);

        com.github.pagehelper.Page<UserGroupVO> result = PageHelper
                .startPage(pageable.getPageNumber(), pageable.getPageSize())
                .doSelectPage(() -> userGroupMapping.toUserGroupVOList(userGroupMapper.selectMany(selectStatementProvider)));

        return new PageImpl<>(result.getResult(), pageable, result.getTotal());
    }

    @Override
    public void assignRoles(Integer id, List<Integer> roleIds) {
        // 获取已建立关联的角色ids
        List<Integer> preRoleIds = getAssignedRoleIds(id);

        // 获取要新建关联的角色ids
        List<Integer> toAddRoleIds = roleIds.stream()
                .filter(roleId -> !preRoleIds.contains(roleId)).collect(Collectors.toList());

        // 获取要删除关联的角色ids
        List<Integer> toDelRoleIds = preRoleIds.stream()
                .filter(actionId -> !roleIds.contains(actionId)).collect(Collectors.toList());

        // 插入要新建的用户组-角色关联
        List<UserGroupRoleRelation> relations = toAddRoleIds.stream()
                .map(roleId -> {
                    UserGroupRoleRelation relation = new UserGroupRoleRelation();
                    relation.setGroupId(id);
                    relation.setRoleId(roleId);

                    return relation;
                }).collect(Collectors.toList());
        relations.forEach(userGroupRoleRelationMapper::insert);

        // 删除要删除的用户组-角色关联
        if (!CollectionUtils.isEmpty(toDelRoleIds)) {
            DeleteStatementProvider deleteStatementProvider = deleteFrom(UserGroupRoleRelationDynamicSqlSupport.userGroupRoleRelation)
                    .where(UserGroupRoleRelationDynamicSqlSupport.groupId, isEqualTo(id))
                    .and(UserGroupRoleRelationDynamicSqlSupport.roleId, isIn(toDelRoleIds))
                    .build().render(RenderingStrategies.MYBATIS3);
            userGroupRoleRelationMapper.delete(deleteStatementProvider);
        }
    }

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

        // 插入要新建的用户组-资源动作关联
        List<UserGroupResourceActionRelation> relations = toAddActionIds.stream()
                .map(actionId -> {
                    UserGroupResourceActionRelation relation = new UserGroupResourceActionRelation();
                    relation.setGroupId(id);
                    relation.setActionId(actionId);

                    return relation;
                }).collect(Collectors.toList());
        relations.forEach(userGroupResourceActionRelationMapper::insert);

        // 删除要删除的用户组-资源动作关联
        if (!CollectionUtils.isEmpty(toDelActionIds)) {
            DeleteStatementProvider deleteStatementProvider = deleteFrom(UserGroupResourceActionRelationDynamicSqlSupport.userGroupResourceActionRelation)
                    .where(UserGroupResourceActionRelationDynamicSqlSupport.groupId, isEqualTo(id))
                    .and(UserGroupResourceActionRelationDynamicSqlSupport.actionId, isIn(toDelActionIds))
                    .build().render(RenderingStrategies.MYBATIS3);
            userGroupResourceActionRelationMapper.delete(deleteStatementProvider);
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

        // 插入要新建的用户组-菜单关联
        List<UserGroupMenuRelation> relations = toAddMenuIds.stream()
                .map(menuId -> {
                    UserGroupMenuRelation relation = new UserGroupMenuRelation();
                    relation.setGroupId(id);
                    relation.setMenuId(menuId);

                    return relation;
                }).collect(Collectors.toList());
        relations.forEach(userGroupMenuRelationMapper::insert);

        // 删除要删除的用户组-菜单关联
        if (!CollectionUtils.isEmpty(toDelMenuIds)) {
            DeleteStatementProvider deleteStatementProvider = deleteFrom(UserGroupMenuRelationDynamicSqlSupport.userGroupMenuRelation)
                    .where(UserGroupMenuRelationDynamicSqlSupport.groupId, isEqualTo(id))
                    .and(UserGroupMenuRelationDynamicSqlSupport.menuId, isIn(toDelMenuIds))
                    .build().render(RenderingStrategies.MYBATIS3);
            userGroupMenuRelationMapper.delete(deleteStatementProvider);
        }
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> getAssignedRoleIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(UserGroupRoleRelationDynamicSqlSupport.roleId)
                .from(UserGroupRoleRelationDynamicSqlSupport.userGroupRoleRelation)
                .where(UserGroupRoleRelationDynamicSqlSupport.groupId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return userGroupRoleRelationMapper.selectMany(selectStatementProvider).stream()
                .map(UserGroupRoleRelation::getRoleId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> getAssignedResourceActionIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(UserGroupResourceActionRelationDynamicSqlSupport.actionId)
                .from(UserGroupResourceActionRelationDynamicSqlSupport.userGroupResourceActionRelation)
                .where(UserGroupResourceActionRelationDynamicSqlSupport.groupId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return userGroupResourceActionRelationMapper.selectMany(selectStatementProvider).stream()
                .map(UserGroupResourceActionRelation::getActionId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> getAssignedMenuIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(UserGroupMenuRelationDynamicSqlSupport.menuId)
                .from(UserGroupMenuRelationDynamicSqlSupport.userGroupMenuRelation)
                .where(UserGroupMenuRelationDynamicSqlSupport.groupId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return userGroupMenuRelationMapper.selectMany(selectStatementProvider).stream()
                .map(UserGroupMenuRelation::getMenuId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<RoleAssignVO> loadRoles(Integer id) {
        List<Integer> assignedRoleIds = getAssignedRoleIds(id);

        List<RoleAssignVO> roles = roleService.findAllForAssign();
        roles.forEach(role -> {
            role.setChecked(assignedRoleIds.contains(role.getId()));
            role.setEnableUncheck(true);
        });

        return roles;
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
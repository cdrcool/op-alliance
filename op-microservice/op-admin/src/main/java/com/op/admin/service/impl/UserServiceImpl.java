package com.op.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.op.admin.dto.UserChangePasswordDTO;
import com.op.admin.dto.UserCreateDTO;
import com.op.admin.dto.UserPageQueryDTO;
import com.op.admin.dto.UserUpdateDTO;
import com.op.admin.entity.User;
import com.op.admin.entity.UserMenuRelation;
import com.op.admin.entity.UserResourceActionRelation;
import com.op.admin.entity.UserRoleRelation;
import com.op.admin.mapper.*;
import com.op.admin.mapping.UserMapping;
import com.op.admin.service.*;
import com.op.admin.utils.BCryptPasswordEncoder;
import com.op.admin.utils.PasswordGenerator;
import com.op.admin.vo.MenuAssignVO;
import com.op.admin.vo.ResourceCategoryAssignVO;
import com.op.admin.vo.RoleAssignVO;
import com.op.admin.vo.UserVO;
import com.op.framework.web.common.api.response.ResultCode;
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
 * 用户 Service Impl
 *
 * @author cdrcool
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * 密码编码器
     */
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserMapper userMapper;
    private final UserMapping userMapping;
    private final UserRoleRelationMapper userRoleRelationMapper;
    private final UserResourceActionRelationMapper userResourceActionRelationMapper;
    private final UserMenuRelationMapper userMenuRelationMapper;
    private final RoleService roleService;
    private final ResourceCategoryService resourceCategoryService;
    private final MenuService menuService;
    private final UserGroupService userGroupService;
    private final OrganizationService organizationService;

    public UserServiceImpl(UserMapper userMapper, UserMapping userMapping,
                           UserRoleRelationMapper userRoleRelationMapper,
                           UserResourceActionRelationMapper userResourceActionRelationMapper,
                           UserMenuRelationMapper userMenuRelationMapper,
                           RoleService roleService,
                           ResourceCategoryService resourceCategoryService,
                           MenuService menuService,
                           UserGroupService userGroupService,
                           OrganizationService organizationService) {
        this.userMapper = userMapper;
        this.userMapping = userMapping;
        this.userRoleRelationMapper = userRoleRelationMapper;
        this.userResourceActionRelationMapper = userResourceActionRelationMapper;
        this.userMenuRelationMapper = userMenuRelationMapper;
        this.roleService = roleService;
        this.resourceCategoryService = resourceCategoryService;
        this.menuService = menuService;
        this.userGroupService = userGroupService;
        this.organizationService = organizationService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String create(UserCreateDTO createDTO) {
        User user = userMapping.toUser(createDTO);

        // 新建用户随机生成6位数密码
        String password = PasswordGenerator.generate(6);
        user.setPassword(passwordEncoder.encode(password));

        userMapper.insert(user);
        return password;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changePassword(UserChangePasswordDTO changePasswordDto) {
        Integer id = changePasswordDto.getId();
        User user = userMapper.selectByPrimaryKey(id)
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的用户"));

        String newPassword = changePasswordDto.getPassword();
        if (!passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "旧密码不正确");
        }

        userMapping.update(changePasswordDto, user);

        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.updateByPrimaryKey(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(UserUpdateDTO updateDTO) {
        Integer id = updateDTO.getId();
        User user = userMapper.selectByPrimaryKey(id)
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的用户"));
        userMapping.update(updateDTO, user);
        userMapper.updateByPrimaryKey(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public UserVO findById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id).orElse(new User());
        return userMapping.toUserVo(user);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Page<UserVO> queryPage(Pageable pageable, UserPageQueryDTO queryDTO) {
        SortSpecification[] specifications = pageable.getSort().stream()
                .map(order -> {
                    SortSpecification specification = SimpleSortSpecification.of(order.getProperty());
                    if (order.isDescending()) {
                        return specification.descending();
                    }
                    return specification;
                }).toArray(SortSpecification[]::new);

        SelectStatementProvider selectStatementProvider = select(UserMapper.selectList)
                .from(UserDynamicSqlSupport.user)
                .where(UserDynamicSqlSupport.orgId, isEqualToWhenPresent(queryDTO.getOrgId()))
                .and(UserDynamicSqlSupport.gender, isEqualToWhenPresent(queryDTO.getGender()))
                .and(UserDynamicSqlSupport.status, isInWhenPresent(queryDTO.getStatus()))
                .and(UserDynamicSqlSupport.username, isLikeWhenPresent(queryDTO.getSearchText()),
                        or(UserDynamicSqlSupport.nickname, isLikeWhenPresent(queryDTO.getSearchText())),
                        or(UserDynamicSqlSupport.phone, isLikeWhenPresent(queryDTO.getSearchText())),
                        or(UserDynamicSqlSupport.email, isLikeWhenPresent(queryDTO.getSearchText())))
                .orderBy(specifications)
                .limit(pageable.getPageSize()).offset(pageable.getOffset())
                .build().render(RenderingStrategies.MYBATIS3);

        com.github.pagehelper.Page<UserVO> result = PageHelper
                .startPage(pageable.getPageNumber(), pageable.getPageSize())
                .doSelectPage(() -> userMapping.toUserVOList(userMapper.selectMany(selectStatementProvider)));

        return new PageImpl<>(result.getResult(), pageable, result.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changeEnabled(Integer id, boolean enable) {
        UpdateStatementProvider updateStatement = SqlBuilder.update(UserDynamicSqlSupport.user)
                .set(UserDynamicSqlSupport.status).equalTo(enable ? 1 : 0)
                .where(UserDynamicSqlSupport.id, isEqualTo(id))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        userMapper.update(updateStatement);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignRoles(Integer id, List<Integer> roleIds) {
        // 获取已建立关联的角色 ids
        List<Integer> preRoleIds = this.getAssignedRoleIds(id);

        // 获取要新建关联的角色 ids
        List<Integer> toAddRoleIds = roleIds.stream()
                .filter(roleId -> !preRoleIds.contains(roleId)).collect(Collectors.toList());

        // 获取要删除关联的角色 ids
        List<Integer> toDelRoleIds = preRoleIds.stream()
                .filter(actionId -> !roleIds.contains(actionId)).collect(Collectors.toList());

        // 插入要新建的用户-角色关联
        List<UserRoleRelation> relations = toAddRoleIds.stream()
                .map(roleId -> {
                    UserRoleRelation relation = new UserRoleRelation();
                    relation.setUserId(id);
                    relation.setRoleId(roleId);

                    return relation;
                }).collect(Collectors.toList());
        relations.forEach(userRoleRelationMapper::insert);

        // 删除要删除的用户-角色关联
        if (!CollectionUtils.isEmpty(toDelRoleIds)) {
            DeleteStatementProvider deleteStatementProvider =
                    deleteFrom(UserRoleRelationDynamicSqlSupport.userRoleRelation)
                            .where(UserRoleRelationDynamicSqlSupport.userId, isEqualTo(id))
                            .and(UserRoleRelationDynamicSqlSupport.roleId, isIn(toDelRoleIds))
                            .build().render(RenderingStrategies.MYBATIS3);
            userRoleRelationMapper.delete(deleteStatementProvider);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignResources(Integer id, List<Integer> resourceActionIds) {
        // 获取已建立关联的资源动作 ids
        List<Integer> preActionIds = this.getAssignedResourceActionIds(id);

        // 获取要新建关联的资源动作 ids
        List<Integer> toAddActionIds = resourceActionIds.stream()
                .filter(actionId -> !preActionIds.contains(actionId)).collect(Collectors.toList());

        // 获取要删除关联的资源动作 ids
        List<Integer> toDelActionIds = preActionIds.stream()
                .filter(actionId -> !resourceActionIds.contains(actionId)).collect(Collectors.toList());

        // 插入要新建的用户-资源动作关联
        List<UserResourceActionRelation> relations = toAddActionIds.stream()
                .map(actionId -> {
                    UserResourceActionRelation relation = new UserResourceActionRelation();
                    relation.setUserId(id);
                    relation.setActionId(actionId);

                    return relation;
                }).collect(Collectors.toList());
        relations.forEach(userResourceActionRelationMapper::insert);

        // 删除要删除的用户-资源动作关联
        if (!CollectionUtils.isEmpty(toDelActionIds)) {
            DeleteStatementProvider deleteStatementProvider =
                    deleteFrom(UserResourceActionRelationDynamicSqlSupport.userResourceActionRelation)
                            .where(UserResourceActionRelationDynamicSqlSupport.userId, isEqualTo(id))
                            .and(UserResourceActionRelationDynamicSqlSupport.actionId, isIn(toDelActionIds))
                            .build().render(RenderingStrategies.MYBATIS3);
            userResourceActionRelationMapper.delete(deleteStatementProvider);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignMenus(Integer id, List<Integer> menuIds) {
        // 获取已建立关联的菜单 ids
        List<Integer> preMenuIds = this.getAssignedMenuIds(id);

        // 获取要新建关联的菜单 ids
        List<Integer> toAddMenuIds = menuIds.stream()
                .filter(menuId -> !preMenuIds.contains(menuId)).collect(Collectors.toList());

        // 获取要删除关联的菜单 ids
        List<Integer> toDelMenuIds = preMenuIds.stream()
                .filter(menuId -> !menuIds.contains(menuId)).collect(Collectors.toList());

        // 插入要新建的角色-菜单关联
        List<UserMenuRelation> relations = toAddMenuIds.stream()
                .map(menuId -> {
                    UserMenuRelation relation = new UserMenuRelation();
                    relation.setUserId(id);
                    relation.setMenuId(menuId);

                    return relation;
                }).collect(Collectors.toList());
        relations.forEach(userMenuRelationMapper::insert);

        // 删除要删除的角色-菜单关联
        if (!CollectionUtils.isEmpty(toDelMenuIds)) {
            DeleteStatementProvider deleteStatementProvider = deleteFrom(UserMenuRelationDynamicSqlSupport.userMenuRelation)
                    .where(UserMenuRelationDynamicSqlSupport.userId, isEqualTo(id))
                    .and(UserMenuRelationDynamicSqlSupport.menuId, isIn(toDelMenuIds))
                    .build().render(RenderingStrategies.MYBATIS3);
            userMenuRelationMapper.delete(deleteStatementProvider);
        }
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> getAssignedRoleIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(UserRoleRelationDynamicSqlSupport.roleId)
                .from(UserRoleRelationDynamicSqlSupport.userRoleRelation)
                .where(UserRoleRelationDynamicSqlSupport.userId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return userRoleRelationMapper.selectMany(selectStatementProvider).stream()
                .map(UserRoleRelation::getRoleId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> getAssignedResourceActionIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(UserResourceActionRelationDynamicSqlSupport.actionId)
                .from(UserResourceActionRelationDynamicSqlSupport.userResourceActionRelation)
                .where(UserResourceActionRelationDynamicSqlSupport.userId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return userResourceActionRelationMapper.selectMany(selectStatementProvider).stream()
                .map(UserResourceActionRelation::getActionId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> getAssignedMenuIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(UserMenuRelationDynamicSqlSupport.menuId)
                .from(UserMenuRelationDynamicSqlSupport.userMenuRelation)
                .where(UserMenuRelationDynamicSqlSupport.userId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return userMenuRelationMapper.selectMany(selectStatementProvider).stream()
                .map(UserMenuRelation::getMenuId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<RoleAssignVO> loadRoles(Integer id) {
        List<Integer> assignedRoleIds = this.getAssignedRoleIds(id);
        List<Integer> groupAssignedRoleIds = userGroupService.getAssignedRoleIds(id);
        List<Integer> orgAssignedRoleIds = organizationService.getAssignedRoleIds(id);

        List<RoleAssignVO> roles = roleService.findAllForAssign();
        roles.forEach(role -> {
            role.setChecked(assignedRoleIds.contains(role.getId()) ||
                    groupAssignedRoleIds.contains(role.getId()) ||
                    orgAssignedRoleIds.contains(role.getId()));
            role.setEnableUncheck(groupAssignedRoleIds.contains(role.getId()) &&
                    !orgAssignedRoleIds.contains(role.getId()));
        });

        return roles;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<ResourceCategoryAssignVO> loadResources(Integer id) {
        List<Integer> assignedActionIds = this.getAssignedResourceActionIds(id);
        List<Integer> groupAssignedActionIds = userGroupService.getAssignedResourceActionIds(id);
        List<Integer> orgAssignedActionIds = organizationService.getAssignedResourceActionIds(id);

        List<ResourceCategoryAssignVO> categories = resourceCategoryService.findAllForAssign();
        categories.forEach(category ->
                category.getResources().forEach(resource ->
                        resource.getActions().forEach(action -> {
                            action.setChecked(assignedActionIds.contains(action.getId()) ||
                                    groupAssignedActionIds.contains(action.getId()) ||
                                    orgAssignedActionIds.contains(action.getId()));
                            action.setEnableUncheck(groupAssignedActionIds.contains(action.getId()) &&
                                    !orgAssignedActionIds.contains(action.getId()));
                        })));

        return categories;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<MenuAssignVO> loadMenus(Integer id) {
        List<Integer> assignedMenuIds = this.getAssignedMenuIds(id);
        List<Integer> groupAssignedMenuIds = userGroupService.getAssignedMenuIds(id);
        List<Integer> orgAssignedMenuIds = organizationService.getAssignedMenuIds(id);

        List<MenuAssignVO> menus = menuService.findAllForAssign();
        setMenuItems(menus, assignedMenuIds, groupAssignedMenuIds, orgAssignedMenuIds);

        return menus;
    }

    private void setMenuItems(List<MenuAssignVO> menus, List<Integer> assignedMenuIds,
                              List<Integer> groupAssignedMenuIds, List<Integer> orgAssignedMenuIds) {
        menus.forEach(menu -> {
            menu.setChecked(assignedMenuIds.contains(menu.getId()) ||
                    groupAssignedMenuIds.contains(menu.getId()) ||
                    orgAssignedMenuIds.contains(menu.getId()));
            menu.setEnableUncheck(groupAssignedMenuIds.contains(menu.getId()) &&
                    !orgAssignedMenuIds.contains(menu.getId()));

            if (!CollectionUtils.isEmpty(menu.getChildren())) {
                setMenuItems(menu.getChildren(), assignedMenuIds, groupAssignedMenuIds, orgAssignedMenuIds);
            }
        });
    }
}

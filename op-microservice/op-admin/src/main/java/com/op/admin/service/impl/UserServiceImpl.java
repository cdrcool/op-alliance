package com.op.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.op.admin.dto.*;
import com.op.admin.entity.*;
import com.op.admin.mapper.*;
import com.op.admin.mapping.UserMapping;
import com.op.admin.service.*;
import com.op.admin.utils.BCryptPasswordEncoder;
import com.op.admin.utils.PasswordGenerator;
import com.op.admin.vo.ResourceCategoryAssignVO;
import com.op.admin.vo.RoleAssignVO;
import com.op.admin.vo.UserVO;
import com.op.framework.web.common.api.response.ResultCode;
import com.op.framework.web.common.api.response.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.SimpleSortSpecification;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private final UserGroupUserRelationMapper userGroupUserRelationMapper;
    private final UserOrganizationRelationMapper userOrganizationRelationMapper;
    private final RoleService roleService;
    private final ResourceCategoryService resourceCategoryService;
    private final ResourceActionService resourceActionService;
    private final UserGroupService userGroupService;
    private final OrganizationService organizationService;

    public UserServiceImpl(UserMapper userMapper, UserMapping userMapping,
                           UserRoleRelationMapper userRoleRelationMapper,
                           UserResourceActionRelationMapper userResourceActionRelationMapper,
                           UserGroupUserRelationMapper userGroupUserRelationMapper,
                           UserOrganizationRelationMapper userOrganizationRelationMapper,
                           RoleService roleService,
                           ResourceCategoryService resourceCategoryService,
                           ResourceActionService resourceActionService,
                           UserGroupService userGroupService,
                           @Lazy OrganizationService organizationService) {
        this.userMapper = userMapper;
        this.userMapping = userMapping;
        this.userRoleRelationMapper = userRoleRelationMapper;
        this.userResourceActionRelationMapper = userResourceActionRelationMapper;
        this.userGroupUserRelationMapper = userGroupUserRelationMapper;
        this.userOrganizationRelationMapper = userOrganizationRelationMapper;
        this.roleService = roleService;
        this.resourceCategoryService = resourceCategoryService;
        this.resourceActionService = resourceActionService;
        this.userGroupService = userGroupService;
        this.organizationService = organizationService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String create(UserCreateDTO createDTO) {
        // 校验用户名是否重复
        validateUserName(null, createDTO.getUsername());

        User user = userMapping.toUser(createDTO);

        // 新建用户随机生成6位数密码
        String password = PasswordGenerator.generate(6);
        user.setPassword(passwordEncoder.encode(password));

        userMapper.insert(user);
        return password;
    }

    /**
     * 校验用户名是否重复
     *
     * @param id       主键
     * @param username 用户名
     */
    private void validateUserName(Integer id, String username) {
        SelectStatementProvider selectStatementProvider = countFrom(UserDynamicSqlSupport.user)
                .where(UserDynamicSqlSupport.username, isEqualTo(username))
                .and(UserDynamicSqlSupport.id, isNotEqualToWhenPresent(id))
                .build().render(RenderingStrategies.MYBATIS3);
        long count = userMapper.count(selectStatementProvider);
        if (count > 0) {
            throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "已存在相同用户名的用户，用户名不能重复");
        }
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

        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.updateByPrimaryKey(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(UserSaveDTO saveDTO) {
        // 校验用户名是否重复
        validateUserName(saveDTO.getId(), saveDTO.getUsername());

        if (saveDTO.getId() == null) {
            User user = userMapping.toUser(saveDTO);
            userMapper.insert(user);
        } else {
            Integer id = saveDTO.getId();
            User user = userMapper.selectByPrimaryKey(id)
                    .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的用户"));
            userMapping.update(saveDTO, user);
            userMapper.updateByPrimaryKey(user);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        userMapper.deleteByPrimaryKey(id);

        // 删除用户-角色关联列表
        DeleteStatementProvider userRoleRelationProvider = deleteFrom(UserRoleRelationDynamicSqlSupport.userRoleRelation)
                .where(UserRoleRelationDynamicSqlSupport.userId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        userRoleRelationMapper.delete(userRoleRelationProvider);

        // 删除用户-资源动作关联列表
        DeleteStatementProvider userResourceActionRelationProvider = deleteFrom(UserResourceActionRelationDynamicSqlSupport.userResourceActionRelation)
                .where(UserResourceActionRelationDynamicSqlSupport.userId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        userResourceActionRelationMapper.delete(userResourceActionRelationProvider);

        // 删除用户组-用户关联列表
        DeleteStatementProvider userGroupRelationProvider = deleteFrom(UserGroupUserRelationDynamicSqlSupport.userGroupUserRelation)
                .where(UserGroupUserRelationDynamicSqlSupport.userId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        userGroupUserRelationMapper.delete(userGroupRelationProvider);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Integer> ids) {
        ids.forEach(this::deleteById);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByOrgIds(List<Integer> orgIds) {
        SelectStatementProvider selectStatementProvider = select(UserDynamicSqlSupport.id)
                .from(UserDynamicSqlSupport.user)
                .where(UserDynamicSqlSupport.orgId, isIn(orgIds))
                .build().render(RenderingStrategies.MYBATIS3);
        List<Integer> ids = userMapper.selectMany(selectStatementProvider).stream()
                .map(User::getId).collect(Collectors.toList());
        ids.forEach(this::deleteById);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public UserVO findById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id)
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的用户"));
        return userMapping.toUserVo(user);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<UserVO> findByIds(List<Integer> ids) {
        SelectStatementProvider selectStatementProvider = select(UserMapper.selectList)
                .from(UserDynamicSqlSupport.user)
                .where(UserDynamicSqlSupport.id, isIn(ids))
                .build().render(RenderingStrategies.MYBATIS3);
        List<User> users = userMapper.selectMany(selectStatementProvider);
        return userMapping.toUserVOList(users);
    }

    @Override
    public UserDTO findByUserName(String username) {
        SelectStatementProvider selectStatementProvider = select(UserDynamicSqlSupport.id,
                UserDynamicSqlSupport.username,
                UserDynamicSqlSupport.password,
                UserDynamicSqlSupport.status)
                .from(UserDynamicSqlSupport.user)
                .where(UserDynamicSqlSupport.username, isEqualTo(username))
                .build().render(RenderingStrategies.MYBATIS3);
        User user = userMapper.selectOne(selectStatementProvider)
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到用户名为【" + username + "】的用户"));

        UserDTO userDTO = userMapping.toUserDto(user);
        userDTO.setPermissions(this.loadAssignPermissions(user.getId(), user.getOrgId()));

        return userDTO;
    }

    /**
     * 加载用户所分配的所有权限
     *
     * @param id    主键
     * @param orgId 组织 id
     * @return 权限列表
     */
    private List<String> loadAssignPermissions(Integer id, Integer orgId) {
        // 获取用户所分配的资源动作 ids
        List<Integer> userAssignedActionIds = this.getAssignedResourceActionIds(id);

        // 获取用户的角色所分配的资源动作 ids
        List<Integer> roleIds = this.getAssignedRoleIds(id);
        if (CollectionUtils.isNotEmpty(roleIds)) {
            List<Integer> roleAssignedActionIds = roleService.getAssignedResourceActionIds(roleIds);
            userAssignedActionIds.addAll(roleAssignedActionIds);
        }

        // 获取用户的用户组所分配的资源动作 ids
        List<Integer> groupIds = this.getAssignedGroupIds(id);
        if (CollectionUtils.isNotEmpty(groupIds)) {
            List<Integer> groupAssignedActionIds = userGroupService.getAssignedResourceActionIds(groupIds);
            userAssignedActionIds.addAll(groupAssignedActionIds);
        }

        // 获取用户的组织所分配的资源动作 ids
        List<Integer> orgAssignedActionIds = organizationService.getAssignedResourceActionIds(orgId);
        userAssignedActionIds.addAll(orgAssignedActionIds);

        if (CollectionUtils.isEmpty(userAssignedActionIds)) {
            return new ArrayList<>();
        }

        return resourceActionService.getPermissions(userAssignedActionIds);
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
                .where(UserDynamicSqlSupport.orgId, isIn(organizationService.getChildrenIds(queryDTO.getOrgId())))
                .and(UserDynamicSqlSupport.gender, isEqualToWhenPresent(queryDTO.getGender()))
                .and(UserDynamicSqlSupport.status, isInWhenPresent(queryDTO.getStatus()))
                .and(UserDynamicSqlSupport.username, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%"),
                        or(UserDynamicSqlSupport.nickname, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%")),
                        or(UserDynamicSqlSupport.phone, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%")),
                        or(UserDynamicSqlSupport.email, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%")))
                .orderBy(specifications)
                .build().render(RenderingStrategies.MYBATIS3);

        com.github.pagehelper.Page<User> result = PageHelper
                .startPage(pageable.getPageNumber() + 1, pageable.getPageSize())
                .doSelectPage(() -> userMapper.selectMany(selectStatementProvider));

        return new PageImpl<>(userMapping.toUserVOList(result.getResult()), pageable, result.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changeEnabled(List<Integer> ids, boolean enable) {
        UpdateStatementProvider updateStatement = SqlBuilder.update(UserDynamicSqlSupport.user)
                .set(UserDynamicSqlSupport.status).equalTo(enable ? 1 : 0)
                .where(UserDynamicSqlSupport.id, isIn(ids))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        userMapper.update(updateStatement);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignRoles(Integer id, List<Integer> roleIds) {
        // 获取继承的分配的角色 ids
        List<Integer> inheritedRoleIds = this.loadInheritedAssignedRoleIds(id);

        // 获取已建立关联的角色 ids
        List<Integer> preRoleIds = this.getAssignedRoleIds(id);

        // 获取要新建关联的角色 ids
        List<Integer> toAddRoleIds = roleIds.stream()
                .filter(roleId -> !inheritedRoleIds.contains(roleId))
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

    /**
     * 获取继承的分配的角色 ids
     *
     * @param id 用户 id
     * @return 继承的分配的角色 ids
     */
    private List<Integer> loadInheritedAssignedRoleIds(Integer id) {
        List<Integer> assignedRoleIds = new ArrayList<>();

        // 获取用户的用户组所分配的角色 ids
        List<Integer> groupIds = this.getAssignedGroupIds(id);
        if (!CollectionUtils.isEmpty(groupIds)) {
            assignedRoleIds.addAll(userGroupService.getAssignedRoleIds(groupIds));
        }

        // 获取用户的组织所分配的角色 ids
        assignedRoleIds.addAll(organizationService.getAssignedRoleIds(id));

        return assignedRoleIds;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignResourceActions(Integer id, List<Integer> resourceActionIds) {
        // 获取继承的分配的资源动作 ids
        List<Integer> inheritedActionIds = this.loadInheritedAssignedActionIds(id);

        // 获取已建立关联的资源动作 ids
        List<Integer> preActionIds = this.getAssignedResourceActionIds(id);

        // 获取要新建关联的资源动作 ids
        List<Integer> toAddActionIds = resourceActionIds.stream()
                .filter(actionId -> !inheritedActionIds.contains(actionId))
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

    /**
     * 获取继承的分配的资源动作 ids
     *
     * @param id 用户 id
     * @return 继承的分配的资源动作 ids
     */
    private List<Integer> loadInheritedAssignedActionIds(Integer id) {
        List<Integer> assignedActionIds = new ArrayList<>();

        // 获取用户的角色所分配的资源动作 ids
        List<Integer> roleIds = this.getAssignedRoleIds(id);
        if (!CollectionUtils.isEmpty(roleIds)) {
            assignedActionIds.addAll(roleService.getAssignedResourceActionIds(roleIds));
        }

        // 获取用户的用户组所分配的资源动作 ids
        List<Integer> groupIds = this.getAssignedGroupIds(id);
        if (!CollectionUtils.isEmpty(groupIds)) {
            assignedActionIds.addAll(userGroupService.getAssignedResourceActionIds(groupIds));
        }

        // 获取用户的组织所分配的资源动作 ids
        assignedActionIds.addAll(organizationService.getAssignedResourceActionIds(id));

        return assignedActionIds;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignOrganizations(Integer id, List<Integer> organizationIds) {
        // 获取已建立关联的组织 ids
        List<Integer> preOrgIds = this.getAssignedOrganizationIds(id);

        // 获取要新建关联的组织 ids
        List<Integer> toAddOrgIds = organizationIds.stream()
                .filter(orgId -> !preOrgIds.contains(orgId)).collect(Collectors.toList());

        // 获取要删除关联的组织 ids
        List<Integer> toDelOrgIds = preOrgIds.stream()
                .filter(orgId -> !organizationIds.contains(orgId)).collect(Collectors.toList());

        // 插入要新建的用户-组织关联
        List<UserOrganizationRelation> relations = toAddOrgIds.stream()
                .map(orgId -> {
                    UserOrganizationRelation relation = new UserOrganizationRelation();
                    relation.setUserId(id);
                    relation.setOrgId(orgId);

                    return relation;
                }).collect(Collectors.toList());
        relations.forEach(userOrganizationRelationMapper::insert);

        // 删除要删除的用户-组织关联
        if (!CollectionUtils.isEmpty(toDelOrgIds)) {
            DeleteStatementProvider deleteStatementProvider =
                    deleteFrom(UserOrganizationRelationDynamicSqlSupport.userOrganizationRelation)
                            .where(UserOrganizationRelationDynamicSqlSupport.userId, isEqualTo(id))
                            .and(UserOrganizationRelationDynamicSqlSupport.orgId, isIn(toDelOrgIds))
                            .build().render(RenderingStrategies.MYBATIS3);
            userOrganizationRelationMapper.delete(deleteStatementProvider);
        }
    }

    /**
     * 获取用户所分配的组织 ids
     *
     * @param id 主键
     * @return 组织 ids
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> getAssignedOrganizationIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(UserOrganizationRelationDynamicSqlSupport.orgId)
                .from(UserOrganizationRelationDynamicSqlSupport.userOrganizationRelation)
                .where(UserResourceActionRelationDynamicSqlSupport.userId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return userOrganizationRelationMapper.selectMany(selectStatementProvider).stream()
                .map(UserOrganizationRelation::getOrgId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<RoleAssignVO> loadAssignedRoles(Integer id) {
        // 获取用户所分配的角色 ids
        List<Integer> assignedRoleIds = this.getAssignedRoleIds(id);

        // 获取用户的用户组所分配的角色 ids
        List<Integer> groupAssignedRoleIds = new ArrayList<>();
        List<Integer> groupIds = this.getAssignedGroupIds(id);
        if (!CollectionUtils.isEmpty(groupIds)) {
            groupAssignedRoleIds.addAll(userGroupService.getAssignedRoleIds(groupIds));
        }

        // 获取用户的组织所分配的角色 ids
        List<Integer> orgAssignedRoleIds = organizationService.getAssignedRoleIds(id);

        List<RoleAssignVO> roles = roleService.findAllForAssign();
        roles.forEach(role -> {
            role.setChecked(assignedRoleIds.contains(role.getId()) ||
                    groupAssignedRoleIds.contains(role.getId()) ||
                    orgAssignedRoleIds.contains(role.getId()));
            role.setEnableUncheck(!groupAssignedRoleIds.contains(role.getId()) &&
                    !orgAssignedRoleIds.contains(role.getId()));
        });

        return roles;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<ResourceCategoryAssignVO> loadAssignedResources(Integer id) {
        // 获取用户所分配的资源动作 ids
        List<Integer> assignedActionIds = this.getAssignedResourceActionIds(id);

        // 获取用户的角色所分配的资源动作 ids
        List<Integer> roleAssignedActionIds = new ArrayList<>();
        List<Integer> roleIds = this.getAssignedRoleIds(id);
        if (!CollectionUtils.isEmpty(roleIds)) {
            roleAssignedActionIds.addAll(roleService.getAssignedResourceActionIds(roleIds));
        }

        // 获取用户的用户组所分配的资源动作 ids
        List<Integer> groupAssignedActionIds = new ArrayList<>();
        List<Integer> groupIds = this.getAssignedGroupIds(id);
        if (!CollectionUtils.isEmpty(groupIds)) {
            groupAssignedActionIds.addAll(userGroupService.getAssignedResourceActionIds(groupIds));
        }

        // 获取用户的组织所分配的资源动作 ids
        List<Integer> orgAssignedActionIds = organizationService.getAssignedResourceActionIds(id);

        List<ResourceCategoryAssignVO> categories = resourceCategoryService.findAllForAssign();
        categories.forEach(category ->
                Optional.ofNullable(category.getResources()).orElse(new ArrayList<>()).forEach(resource ->
                        Optional.ofNullable(resource.getActions()).orElse(new ArrayList<>()).forEach(action -> {
                            action.setChecked(assignedActionIds.contains(action.getId()) ||
                                    roleAssignedActionIds.contains(action.getId()) ||
                                    groupAssignedActionIds.contains(action.getId()) ||
                                    orgAssignedActionIds.contains(action.getId()));
                            action.setEnableUncheck(!roleAssignedActionIds.contains(action.getId()) &&
                                    !groupAssignedActionIds.contains(action.getId()) &&
                                    !orgAssignedActionIds.contains(action.getId()));
                        })));

        return categories;
    }

    /**
     * 获取用户所分配的角色 ids
     *
     * @param id 主键
     * @return 角色 ids
     */
    private List<Integer> getAssignedRoleIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(UserRoleRelationDynamicSqlSupport.roleId)
                .from(UserRoleRelationDynamicSqlSupport.userRoleRelation)
                .where(UserRoleRelationDynamicSqlSupport.userId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return userRoleRelationMapper.selectMany(selectStatementProvider).stream()
                .map(UserRoleRelation::getRoleId).collect(Collectors.toList());
    }

    /**
     * 获取用户所分配的资源动作 ids
     *
     * @param id 主键
     * @return 资源动作 ids
     */
    private List<Integer> getAssignedResourceActionIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(UserResourceActionRelationDynamicSqlSupport.actionId)
                .from(UserResourceActionRelationDynamicSqlSupport.userResourceActionRelation)
                .where(UserResourceActionRelationDynamicSqlSupport.userId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return userResourceActionRelationMapper.selectMany(selectStatementProvider).stream()
                .map(UserResourceActionRelation::getActionId).collect(Collectors.toList());
    }

    /**
     * 获取用户所分配的用户组 ids
     *
     * @param id 主键
     * @return 用户组 ids
     */
    private List<Integer> getAssignedGroupIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(UserGroupUserRelationDynamicSqlSupport.groupId)
                .from(UserGroupUserRelationDynamicSqlSupport.userGroupUserRelation)
                .where(UserGroupUserRelationDynamicSqlSupport.userId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return userGroupUserRelationMapper.selectMany(selectStatementProvider).stream()
                .map(UserGroupUserRelation::getGroupId).collect(Collectors.toList());
    }
}

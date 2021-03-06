package com.op.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.op.admin.dto.UserGroupPageQueryDTO;
import com.op.admin.dto.UserGroupSaveDTO;
import com.op.admin.entity.UserGroup;
import com.op.admin.entity.UserGroupResourceActionRelation;
import com.op.admin.entity.UserGroupRoleRelation;
import com.op.admin.entity.UserGroupUserRelation;
import com.op.admin.mapper.*;
import com.op.admin.mapping.UserGroupMapping;
import com.op.admin.service.ResourceCategoryService;
import com.op.admin.service.RoleService;
import com.op.admin.service.UserGroupService;
import com.op.admin.service.UserService;
import com.op.admin.vo.ResourceCategoryAssignVO;
import com.op.admin.vo.RoleAssignVO;
import com.op.admin.vo.UserGroupVO;
import com.op.admin.vo.UserVO;
import com.op.framework.web.common.api.response.ResultCode;
import com.op.framework.web.common.api.response.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.SimpleSortSpecification;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    private final UserGroupUserRelationMapper userGroupUserRelationMapper;
    private final UserGroupRoleRelationMapper userGroupRoleRelationMapper;
    private final UserGroupResourceActionRelationMapper userGroupResourceActionRelationMapper;
    private final UserService userService;
    private final RoleService roleService;
    private final ResourceCategoryService resourceCategoryService;

    public UserGroupServiceImpl(UserGroupMapper userGroupMapper, UserGroupMapping userGroupMapping,
                                UserGroupUserRelationMapper userGroupUserRelationMapper,
                                UserGroupRoleRelationMapper userGroupRoleRelationMapper,
                                UserGroupResourceActionRelationMapper userGroupResourceActionRelationMapper,
                                @Lazy
                                        UserService userService,
                                RoleService roleService,
                                ResourceCategoryService resourceCategoryService) {
        this.userGroupMapper = userGroupMapper;
        this.userGroupMapping = userGroupMapping;
        this.userGroupUserRelationMapper = userGroupUserRelationMapper;
        this.userGroupRoleRelationMapper = userGroupRoleRelationMapper;
        this.userGroupResourceActionRelationMapper = userGroupResourceActionRelationMapper;
        this.userService = userService;
        this.roleService = roleService;
        this.resourceCategoryService = resourceCategoryService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(UserGroupSaveDTO saveDTO) {
        // 校验组名是否重复
        validateGroupName(saveDTO.getId(), saveDTO.getGroupName());

        List<Integer> curUserIds = saveDTO.getUserIds();

        if (saveDTO.getId() == null) {
            UserGroup userGroup = userGroupMapping.toUserGroup(saveDTO);
            userGroupMapper.insert(userGroup);

            // 保存用户组-用户关联列表
            curUserIds.forEach(userId -> {
                UserGroupUserRelation relation = new UserGroupUserRelation();
                relation.setGroupId(userGroup.getId());
                relation.setUserId(userId);
                userGroupUserRelationMapper.insert(relation);
            });
        } else {
            Integer id = saveDTO.getId();
            UserGroup userGroup = userGroupMapper.selectByPrimaryKey(id)
                    .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的用户组"));
            userGroupMapping.update(saveDTO, userGroup);
            userGroupMapper.updateByPrimaryKey(userGroup);

            // 保存用户组-用户关联列表
            List<Integer> preUserIds = findGroupUserIds(id);

            List<Integer> toAddUserIds = curUserIds.stream().filter(userId -> !preUserIds.contains(userId)).collect(Collectors.toList());
            toAddUserIds.forEach(userId -> {
                UserGroupUserRelation relation = new UserGroupUserRelation();
                relation.setGroupId(id);
                relation.setUserId(userId);
                userGroupUserRelationMapper.insert(relation);
            });

            List<Integer> toDelUserIds = preUserIds.stream().filter(userId -> !curUserIds.contains(userId)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(toDelUserIds)) {
                DeleteStatementProvider deleteStatementProvider = deleteFrom(UserGroupUserRelationDynamicSqlSupport.userGroupUserRelation)
                        .where(UserGroupUserRelationDynamicSqlSupport.groupId, isEqualTo(id))
                        .and(UserGroupUserRelationDynamicSqlSupport.userId, isIn(toDelUserIds))
                        .build().render(RenderingStrategies.MYBATIS3);
                userGroupUserRelationMapper.delete(deleteStatementProvider);
            }
        }
    }

    /**
     * 获取用户组下的用户 ids
     *
     * @param id 主键
     * @return 用户 ids
     */
    private List<Integer> findGroupUserIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(UserGroupUserRelationDynamicSqlSupport.userId)
                .from(UserGroupUserRelationDynamicSqlSupport.userGroupUserRelation)
                .where(UserGroupUserRelationDynamicSqlSupport.groupId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return userGroupUserRelationMapper.selectMany(selectStatementProvider).stream()
                .map(UserGroupUserRelation::getUserId).collect(Collectors.toList());
    }

    /**
     * 校验组名是否重复
     *
     * @param id        主键
     * @param groupName 组名
     */
    private void validateGroupName(Integer id, String groupName) {
        SelectStatementProvider selectStatementProvider = countFrom(UserGroupDynamicSqlSupport.userGroup)
                .where(UserGroupDynamicSqlSupport.groupName, isEqualTo(groupName))
                .and(UserGroupDynamicSqlSupport.id, isNotEqualToWhenPresent(id))
                .build().render(RenderingStrategies.MYBATIS3);
        long count = userGroupMapper.count(selectStatementProvider);
        if (count > 0) {
            throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "已存在相同组名的用户组，组名不能重复");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        userGroupMapper.deleteByPrimaryKey(id);

        // 删除用户组-用户关联列表
        DeleteStatementProvider userGroupUserRelationProvider = deleteFrom(UserGroupUserRelationDynamicSqlSupport.userGroupUserRelation)
                .where(UserGroupRoleRelationDynamicSqlSupport.groupId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        userGroupUserRelationMapper.delete(userGroupUserRelationProvider);

        // 删除用户组-角色关联列表
        DeleteStatementProvider userGroupRoleRelationProvider = deleteFrom(UserGroupRoleRelationDynamicSqlSupport.userGroupRoleRelation)
                .where(UserGroupRoleRelationDynamicSqlSupport.groupId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        userGroupRoleRelationMapper.delete(userGroupRoleRelationProvider);

        // 删除用户组-资源动作关联列表
        DeleteStatementProvider userGroupResourceActionRelationProvider = deleteFrom(UserGroupResourceActionRelationDynamicSqlSupport.userGroupResourceActionRelation)
                .where(UserGroupResourceActionRelationDynamicSqlSupport.groupId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        userGroupResourceActionRelationMapper.delete(userGroupResourceActionRelationProvider);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Integer> ids) {
        ids.forEach(this::deleteById);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public UserGroupVO findById(Integer id) {
        UserGroup userGroup = userGroupMapper.selectByPrimaryKey(id)
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的用户组"));
        UserGroupVO userGroupVO = userGroupMapping.toUserGroupVO(userGroup);

        // 获取用户组下的用户列表
        List<Integer> userIds = findGroupUserIds(id);
        if (CollectionUtils.isNotEmpty(userIds)) {
            List<UserVO> users = userService.findByIds(userIds);
            userGroupVO.setUsers(users);
        }

        return userGroupVO;
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
                .where(UserGroupDynamicSqlSupport.groupName, isLike(queryDTO.getKeyword())
                        .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%"))
                .orderBy(specifications)
                .build().render(RenderingStrategies.MYBATIS3);

        com.github.pagehelper.Page<UserGroup> result = PageHelper
                .startPage(pageable.getPageNumber() + 1, pageable.getPageSize())
                .doSelectPage(() -> userGroupMapper.selectMany(selectStatementProvider));

        return new PageImpl<>(userGroupMapping.toUserGroupVOList(result.getResult()), pageable, result.getTotal());
    }

    @Override
    public void assignRoles(Integer id, List<Integer> roleIds) {
        // 获取已建立关联的角色 ids
        List<Integer> preRoleIds = getAssignedRoleIds(Collections.singletonList(id), null);

        // 获取要新建关联的角色 ids
        List<Integer> toAddRoleIds = roleIds.stream()
                .filter(roleId -> !preRoleIds.contains(roleId)).collect(Collectors.toList());

        // 获取要删除关联的角色 ids
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

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<RoleAssignVO> loadAssignedRoles(Integer id) {
        List<Integer> assignedRoleIds = getAssignedRoleIds(Collections.singletonList(id), null);

        List<RoleAssignVO> roles = roleService.findAllForAssign();
        roles.forEach(role -> {
            role.setChecked(assignedRoleIds.contains(role.getId()));
            role.setEnableUncheck(true);
        });

        return roles;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> getAssignedRoleIds(List<Integer> ids, Integer status) {
        SelectStatementProvider selectStatementProvider = select(UserGroupRoleRelationDynamicSqlSupport.roleId)
                .from(UserGroupRoleRelationDynamicSqlSupport.userGroupRoleRelation)
                .join(RoleDynamicSqlSupport.role)
                .on(RoleDynamicSqlSupport.id, equalTo(UserGroupRoleRelationDynamicSqlSupport.roleId))
                .where(UserGroupRoleRelationDynamicSqlSupport.groupId, isIn(ids))
                .and(RoleDynamicSqlSupport.status, isEqualToWhenPresent(status))
                .build().render(RenderingStrategies.MYBATIS3);
        return userGroupRoleRelationMapper.selectMany(selectStatementProvider).stream()
                .map(UserGroupRoleRelation::getRoleId).collect(Collectors.toList());
    }

    @Override
    public void assignResourceActions(Integer id, List<Integer> resourceActionIds) {
        // 获取继承的分配的资源动作 ids
        List<Integer> inheritedActionIds = loadInheritedAssignedActionIds(id);

        // 获取已建立关联的资源动作 ids
        List<Integer> preActionIds = getAssignedResourceActionIds(Collections.singletonList(id));

        // 获取要新建关联的资源动作 ids
        List<Integer> toAddActionIds = resourceActionIds.stream()
                .filter(actionId -> !inheritedActionIds.contains(actionId))
                .filter(actionId -> !preActionIds.contains(actionId)).collect(Collectors.toList());

        // 获取要删除关联的资源动作 ids
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

    /**
     * 获取继承的分配的资源动作 ids
     *
     * @param id 用户组 id
     * @return 继承的分配的资源动作 ids
     */
    private List<Integer> loadInheritedAssignedActionIds(Integer id) {
        List<Integer> assignedActionIds = new ArrayList<>();

        // 获取用户组的角色所分配的资源动作 ids
        List<Integer> roleIds = getAssignedRoleIds(Collections.singletonList(id), 1);
        if (!CollectionUtils.isEmpty(roleIds)) {
            assignedActionIds.addAll(roleService.getAssignedResourceActionIds(roleIds));
        }

        return assignedActionIds;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<ResourceCategoryAssignVO> loadAssignedResources(Integer id) {
        // 获取用户组所分配的资源动作 ids
        List<Integer> assignedActionIds = getAssignedResourceActionIds(Collections.singletonList(id));

        // 获取用户组的角色所分配的资源动作 ids
        List<Integer> roleAssignedActionIds = new ArrayList<>();
        List<Integer> roleIds = getAssignedRoleIds(Collections.singletonList(id), 1);
        if (!CollectionUtils.isEmpty(roleIds)) {
            roleAssignedActionIds.addAll(roleService.getAssignedResourceActionIds(roleIds));
        }

        List<ResourceCategoryAssignVO> categories = resourceCategoryService.findAllForAssign();
        categories.forEach(category ->
                Optional.ofNullable(category.getResources()).orElse(new ArrayList<>()).forEach(resource ->
                        Optional.ofNullable(resource.getActions()).orElse(new ArrayList<>()).forEach(action -> {
                            Integer actionId = action.getId();
                            action.setChecked(assignedActionIds.contains(actionId) || roleAssignedActionIds.contains(actionId));
                            action.setEnableUncheck(!roleAssignedActionIds.contains(actionId));
                        })));

        return categories;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> getAssignedResourceActionIds(List<Integer> ids) {
        SelectStatementProvider selectStatementProvider = select(UserGroupResourceActionRelationDynamicSqlSupport.actionId)
                .from(UserGroupResourceActionRelationDynamicSqlSupport.userGroupResourceActionRelation)
                .where(UserGroupResourceActionRelationDynamicSqlSupport.groupId, isIn(ids))
                .build().render(RenderingStrategies.MYBATIS3);
        return userGroupResourceActionRelationMapper.selectMany(selectStatementProvider).stream()
                .map(UserGroupResourceActionRelation::getActionId).collect(Collectors.toList());
    }
}

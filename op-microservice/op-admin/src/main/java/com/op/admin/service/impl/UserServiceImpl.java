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
import com.op.admin.service.UserService;
import com.op.admin.utils.BCryptPasswordEncoder;
import com.op.admin.utils.PasswordGenerator;
import com.op.admin.vo.UserVO;
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

    public UserServiceImpl(UserMapper userMapper, UserMapping userMapping,
                           UserRoleRelationMapper userRoleRelationMapper,
                           UserResourceActionRelationMapper userResourceActionRelationMapper,
                           UserMenuRelationMapper userMenuRelationMapper) {
        this.userMapper = userMapper;
        this.userMapping = userMapping;
        this.userRoleRelationMapper = userRoleRelationMapper;
        this.userResourceActionRelationMapper = userResourceActionRelationMapper;
        this.userMenuRelationMapper = userMenuRelationMapper;
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
        User user = userMapper.selectByPrimaryKey(id).orElseThrow(() -> new BusinessException("找不到用户，用户id：" + id));

        String newPassword = changePasswordDto.getPassword();
        if (!passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new BusinessException("旧密码不正确");
        }

        userMapping.update(changePasswordDto, user);

        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.updateByPrimaryKey(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(UserUpdateDTO updateDTO) {
        Integer id = updateDTO.getId();
        User user = userMapper.selectByPrimaryKey(id).orElseThrow(() -> new BusinessException("找不到用户，用户id：" + id));
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

    @Override
    public void assignRoles(Integer id, List<Integer> roleIds) {
        // 获取已建立关联的角色ids
        List<Integer> preRoleIds = loadRoleIds(id);

        // 获取要新建关联的角色ids
        List<Integer> toAddRoleIds = roleIds.stream()
                .filter(roleId -> !preRoleIds.contains(roleId)).collect(Collectors.toList());

        // 获取要删除关联的角色ids
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
            DeleteStatementProvider deleteStatementProvider = deleteFrom(UserRoleRelationDynamicSqlSupport.userRoleRelation)
                    .where(UserRoleRelationDynamicSqlSupport.userId, isEqualTo(id))
                    .and(UserRoleRelationDynamicSqlSupport.roleId, isIn(toDelRoleIds))
                    .build().render(RenderingStrategies.MYBATIS3);
            userRoleRelationMapper.delete(deleteStatementProvider);
        }
    }

    @Override
    public void assignResources(Integer id, List<Integer> resourceActionIds) {
        // 获取已建立关联的资源动作ids
        List<Integer> preActionIds = loadResourceIds(id);

        // 获取要新建关联的资源动作ids
        List<Integer> toAddActionIds = resourceActionIds.stream()
                .filter(actionId -> !preActionIds.contains(actionId)).collect(Collectors.toList());

        // 获取要删除关联的资源动作ids
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
            DeleteStatementProvider deleteStatementProvider = deleteFrom(UserResourceActionRelationDynamicSqlSupport.userResourceActionRelation)
                    .where(UserResourceActionRelationDynamicSqlSupport.userId, isEqualTo(id))
                    .and(UserResourceActionRelationDynamicSqlSupport.actionId, isIn(toDelActionIds))
                    .build().render(RenderingStrategies.MYBATIS3);
            userResourceActionRelationMapper.delete(deleteStatementProvider);
        }
    }

    @Override
    public void assignMenus(Integer id, List<Integer> menuIds) {
        // 获取已建立关联的菜单ids
        List<Integer> preMenuIds = loadMenuIds(id);

        // 获取要新建关联的菜单ids
        List<Integer> toAddMenuIds = menuIds.stream()
                .filter(menuId -> !preMenuIds.contains(menuId)).collect(Collectors.toList());

        // 获取要删除关联的菜单ids
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

    @Override
    public List<Integer> loadRoleIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(UserRoleRelationDynamicSqlSupport.roleId)
                .from(UserRoleRelationDynamicSqlSupport.userRoleRelation)
                .where(UserRoleRelationDynamicSqlSupport.userId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return userRoleRelationMapper.selectMany(selectStatementProvider).stream()
                .map(UserRoleRelation::getRoleId).collect(Collectors.toList());
    }

    @Override
    public List<Integer> loadResourceIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(UserResourceActionRelationDynamicSqlSupport.actionId)
                .from(UserResourceActionRelationDynamicSqlSupport.userResourceActionRelation)
                .where(UserResourceActionRelationDynamicSqlSupport.userId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return userResourceActionRelationMapper.selectMany(selectStatementProvider).stream()
                .map(UserResourceActionRelation::getActionId).collect(Collectors.toList());
    }

    @Override
    public List<Integer> loadMenuIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(UserMenuRelationDynamicSqlSupport.menuId)
                .from(UserMenuRelationDynamicSqlSupport.userMenuRelation)
                .where(UserMenuRelationDynamicSqlSupport.userId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return userMenuRelationMapper.selectMany(selectStatementProvider).stream()
                .map(UserMenuRelation::getMenuId).collect(Collectors.toList());
    }
}

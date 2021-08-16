package com.op.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.op.admin.dto.RolePageQueryDTO;
import com.op.admin.dto.RoleSaveDTO;
import com.op.admin.entity.Role;
import com.op.admin.entity.RoleResourceActionRelation;
import com.op.admin.mapper.RoleDynamicSqlSupport;
import com.op.admin.mapper.RoleMapper;
import com.op.admin.mapper.RoleResourceActionRelationDynamicSqlSupport;
import com.op.admin.mapper.RoleResourceActionRelationMapper;
import com.op.admin.mapping.RoleMapping;
import com.op.admin.service.ResourceCategoryService;
import com.op.admin.service.RoleService;
import com.op.admin.vo.ResourceCategoryAssignVO;
import com.op.admin.vo.RoleAssignVO;
import com.op.admin.vo.RoleVO;
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
 * 角色 Service Impl
 *
 * @author cdrcool
 */
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleMapper roleMapper;
    private final RoleMapping roleMapping;
    private final RoleResourceActionRelationMapper roleResourceActionRelationMapper;
    private final ResourceCategoryService resourceCategoryService;

    public RoleServiceImpl(RoleMapper roleMapper, RoleMapping roleMapping,
                           RoleResourceActionRelationMapper roleResourceActionRelationMapper,
                           ResourceCategoryService resourceCategoryService) {
        this.roleMapper = roleMapper;
        this.roleMapping = roleMapping;
        this.roleResourceActionRelationMapper = roleResourceActionRelationMapper;
        this.resourceCategoryService = resourceCategoryService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(RoleSaveDTO saveDTO) {
        // 校验角色名/角色编码是否重复
        validateRoleNameAndRoleCode(saveDTO.getId(), saveDTO.getRoleName(), saveDTO.getRoleCode());

        if (saveDTO.getId() == null) {
            Role role = roleMapping.toRole(saveDTO);
            roleMapper.insert(role);
        } else {
            Integer id = saveDTO.getId();
            Role role = roleMapper.selectByPrimaryKey(id)
                    .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的角色"));
            roleMapping.update(saveDTO, role);
            roleMapper.updateByPrimaryKey(role);
        }
    }

    /**
     * 校验角色名/角色编码是否重复
     *
     * @param id       主键
     * @param roleName 角色名
     * @param roleCode 角色编码
     */
    private void validateRoleNameAndRoleCode(Integer id, String roleName, String roleCode) {
        SelectStatementProvider selectStatementProvider = select(RoleDynamicSqlSupport.roleName, RoleDynamicSqlSupport.roleCode)
                .from(RoleDynamicSqlSupport.role)
                .where(RoleDynamicSqlSupport.roleName, isEqualTo(roleName), or(RoleDynamicSqlSupport.roleCode, isEqualTo(roleCode)))
                .and(RoleDynamicSqlSupport.id, isNotEqualToWhenPresent(id))
                .build().render(RenderingStrategies.MYBATIS3);
        Optional<Role> optional = roleMapper.selectOne(selectStatementProvider);
        optional.ifPresent(role -> {
            if (roleName.equals(role.getRoleName())) {
                throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "已存在相同角色名的角色，角色名不能重复");
            } else {
                throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "已存在相同角色编码的角色，角色编码不能重复");
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        roleMapper.deleteByPrimaryKey(id);

        // 删除角色-资源动作关联列表
        DeleteStatementProvider roleResourceActionRelationProvider = deleteFrom(RoleResourceActionRelationDynamicSqlSupport.roleResourceActionRelation)
                .where(RoleResourceActionRelationDynamicSqlSupport.roleId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        roleResourceActionRelationMapper.delete(roleResourceActionRelationProvider);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Integer> ids) {
        ids.forEach(this::deleteById);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public RoleVO findById(Integer id) {
        Role role = roleMapper.selectByPrimaryKey(id)
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的角色"));
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
                .where(RoleDynamicSqlSupport.status, isEqualToWhenPresent(queryDTO.getStatus()))
                .and(RoleDynamicSqlSupport.roleName, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%"),
                        or(RoleDynamicSqlSupport.roleCode, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%")))
                .orderBy(specifications)
                .build().render(RenderingStrategies.MYBATIS3);

        com.github.pagehelper.Page<Role> result = PageHelper
                .startPage(pageable.getPageNumber() + 1, pageable.getPageSize())
                .doSelectPage(() -> roleMapper.selectMany(selectStatementProvider));

        return new PageImpl<>(roleMapping.toRoleVOList(result.getResult()), pageable, result.getTotal());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<RoleAssignVO> findAllForAssign() {
        SelectStatementProvider selectStatementProvider = select(RoleDynamicSqlSupport.id,
                RoleDynamicSqlSupport.roleName, RoleDynamicSqlSupport.roleCode, RoleDynamicSqlSupport.roleDesc)
                .from(RoleDynamicSqlSupport.role)
                .where(RoleDynamicSqlSupport.status, isEqualTo(1))
                .orderBy(RoleDynamicSqlSupport.roleNo)
                .build().render(RenderingStrategies.MYBATIS3);
        List<Role> roles = roleMapper.selectMany(selectStatementProvider);
        return roleMapping.toRoleAssignVOList(roles);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changeEnabled(List<Integer> ids, boolean enable) {
        UpdateStatementProvider updateStatement = SqlBuilder.update(RoleDynamicSqlSupport.role)
                .set(RoleDynamicSqlSupport.status).equalTo(enable ? 1 : 0)
                .where(RoleDynamicSqlSupport.id, isIn(ids))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        roleMapper.update(updateStatement);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignResourceActions(Integer id, List<Integer> resourceActionIds) {
        // 获取已建立关联的资源动作 ids
        List<Integer> preActionIds = getAssignedResourceActionIds(Collections.singletonList(id));

        // 获取要新建关联的资源动作 ids
        List<Integer> toAddActionIds = Optional.of(resourceActionIds).orElse(new ArrayList<>()).stream()
                .filter(actionId -> !preActionIds.contains(actionId)).collect(Collectors.toList());

        // 获取要删除关联的资源动作 ids
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

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> getAssignedResourceActionIds(List<Integer> ids) {
        SelectStatementProvider selectStatementProvider = select(RoleResourceActionRelationDynamicSqlSupport.actionId)
                .from(RoleResourceActionRelationDynamicSqlSupport.roleResourceActionRelation)
                .where(RoleResourceActionRelationDynamicSqlSupport.roleId, isIn(ids))
                .build().render(RenderingStrategies.MYBATIS3);
        return roleResourceActionRelationMapper.selectMany(selectStatementProvider).stream()
                .map(RoleResourceActionRelation::getActionId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<ResourceCategoryAssignVO> loadAssignedResources(Integer id) {
        List<Integer> assignedActionIds = getAssignedResourceActionIds(Collections.singletonList(id));

        List<ResourceCategoryAssignVO> categories = resourceCategoryService.findAllForAssign();
        categories.forEach(category ->
                Optional.ofNullable(category.getResources()).orElse(new ArrayList<>()).forEach(resource ->
                        Optional.ofNullable(resource.getActions()).orElse(new ArrayList<>()).forEach(action -> {
                            action.setChecked(assignedActionIds.contains(action.getId()));
                            action.setEnableUncheck(true);
                        })));

        return categories;
    }
}

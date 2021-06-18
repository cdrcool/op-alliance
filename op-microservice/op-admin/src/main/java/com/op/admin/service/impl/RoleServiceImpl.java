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
import com.op.admin.service.RoleService;
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

    public RoleServiceImpl(RoleMapper roleMapper, RoleMapping roleMapping,
                           RoleResourceActionRelationMapper roleResourceActionRelationMapper) {
        this.roleMapper = roleMapper;
        this.roleMapping = roleMapping;
        this.roleResourceActionRelationMapper = roleResourceActionRelationMapper;
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

    @Override
    public void changeEnabled(Integer id, boolean enable) {
        UpdateStatementProvider updateStatement = SqlBuilder.update(RoleDynamicSqlSupport.role)
                .set(RoleDynamicSqlSupport.status).equalTo(enable ? 1 : 0)
                .where(RoleDynamicSqlSupport.id, isEqualTo(id))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        roleMapper.update(updateStatement);
    }

    @Override
    public void assignResources(Integer id, List<Integer> resourceActionIds) {
        // 获取已建立关联的资源动作ids
        SelectStatementProvider selectStatementProvider = select(RoleResourceActionRelationDynamicSqlSupport.actionId)
                .from(RoleResourceActionRelationDynamicSqlSupport.roleResourceActionRelation)
                .where(RoleResourceActionRelationDynamicSqlSupport.roleId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        List<Integer> preActionIds = roleResourceActionRelationMapper.selectMany(selectStatementProvider).stream()
                .map(RoleResourceActionRelation::getActionId).collect(Collectors.toList());

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

    @Override
    public void assignMenus(Integer id, List<Integer> menuIds) {

    }
}

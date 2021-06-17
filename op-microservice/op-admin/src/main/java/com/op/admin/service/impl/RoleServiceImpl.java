package com.op.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.op.admin.dto.RoleListQueryDTO;
import com.op.admin.dto.RoleSaveDTO;
import com.op.admin.entity.Role;
import com.op.admin.mapper.RoleDynamicSqlSupport;
import com.op.admin.mapper.RoleMapper;
import com.op.admin.mapping.RoleMapping;
import com.op.admin.service.RoleService;
import com.op.admin.vo.RoleTreeVO;
import com.op.admin.vo.RoleVO;
import com.op.framework.web.common.api.response.exception.BusinessException;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.SimpleSortSpecification;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.mybatis.dynamic.sql.SqlBuilder.isLikeWhenPresent;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

/**
 * 角色 Service Impl
 *
 * @author cdrcool
 */
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleMapper resourceMapper;
    private final RoleMapping resourceMapping;

    public RoleServiceImpl(RoleMapper resourceMapper, RoleMapping resourceMapping) {
        this.resourceMapper = resourceMapper;
        this.resourceMapping = resourceMapping;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(RoleSaveDTO saveDTO) {
        if (saveDTO.getId() == null) {
            Role resource = resourceMapping.toRole(saveDTO);
            resourceMapper.insert(resource);
        } else {
            Integer id = saveDTO.getId();
            Role resource = resourceMapper.selectByPrimaryKey(id).orElseThrow(() -> new BusinessException("找不到角色，角色id：" + id));
            resourceMapping.update(saveDTO, resource);
            resourceMapper.updateByPrimaryKey(resource);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        resourceMapper.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public RoleVO findById(Integer id) {
        Role resource = resourceMapper.selectByPrimaryKey(id).orElse(new Role());
        return resourceMapping.toRoleVO(resource);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Page<RoleTreeVO> queryPage(Pageable pageable, RoleListQueryDTO queryDTO) {
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
                .where(RoleDynamicSqlSupport.roleName, isLikeWhenPresent(queryDTO.getSearchText()))
                .or(RoleDynamicSqlSupport.roleCode, isLikeWhenPresent(queryDTO.getSearchText()))
                .orderBy(specifications)
                .limit(pageable.getPageSize()).offset(pageable.getOffset())
                .build().render(RenderingStrategies.MYBATIS3);

        com.github.pagehelper.Page<RoleTreeVO> result = PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPage(() ->
                resourceMapping.toRoleVOList(resourceMapper.selectMany(selectStatementProvider)));

        return new PageImpl<>(result.getResult(), pageable, result.getTotal());
    }

    @Override
    public void enableDisable(Integer id) {

    }
}

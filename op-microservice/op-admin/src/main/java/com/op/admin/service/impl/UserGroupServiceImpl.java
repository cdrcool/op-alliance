package com.op.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.op.admin.dto.UserGroupListQueryDTO;
import com.op.admin.dto.UserGroupSaveDTO;
import com.op.admin.entity.UserGroup;
import com.op.admin.mapper.UserGroupDynamicSqlSupport;
import com.op.admin.mapper.UserGroupMapper;
import com.op.admin.mapping.UserGroupMapping;
import com.op.admin.service.UserGroupService;
import com.op.admin.vo.UserGroupVO;
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

    public UserGroupServiceImpl(UserGroupMapper userGroupMapper, UserGroupMapping userGroupMapping) {
        this.userGroupMapper = userGroupMapper;
        this.userGroupMapping = userGroupMapping;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(UserGroupSaveDTO saveDTO) {
        if (saveDTO.getId() == null) {
            UserGroup resource = userGroupMapping.toUserGroup(saveDTO);
            userGroupMapper.insert(resource);
        } else {
            Integer id = saveDTO.getId();
            UserGroup resource = userGroupMapper.selectByPrimaryKey(id).orElseThrow(() -> new BusinessException("找不到用户组，用户组id：" + id));
            userGroupMapping.update(saveDTO, resource);
            userGroupMapper.updateByPrimaryKey(resource);
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
        UserGroup resource = userGroupMapper.selectByPrimaryKey(id).orElse(new UserGroup());
        return userGroupMapping.toUserGroupVO(resource);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Page<UserGroupVO> queryPage(Pageable pageable, UserGroupListQueryDTO queryDTO) {
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
}

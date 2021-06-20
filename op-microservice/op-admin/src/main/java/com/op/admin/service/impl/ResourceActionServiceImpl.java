package com.op.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.op.admin.dto.ResourceActionPageQueryDTO;
import com.op.admin.dto.ResourceActionSaveDTO;
import com.op.admin.entity.ResourceAction;
import com.op.admin.mapper.ResourceActionDynamicSqlSupport;
import com.op.admin.mapper.ResourceActionMapper;
import com.op.admin.mapping.ResourceActionMapping;
import com.op.admin.service.ResourceActionService;
import com.op.admin.vo.ResourceActionAssignVO;
import com.op.admin.vo.ResourceActionVO;
import com.op.framework.web.common.api.response.exception.BusinessException;
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
import java.util.Map;
import java.util.stream.Collectors;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

/**
 * 资源动作 Service Impl
 *
 * @author cdrcool
 */
@Service
public class ResourceActionServiceImpl implements ResourceActionService {
    private final ResourceActionMapper resourceActionMapper;
    private final ResourceActionMapping resourceActionMapping;

    public ResourceActionServiceImpl(ResourceActionMapper resourceActionMapper, ResourceActionMapping resourceActionMapping) {
        this.resourceActionMapper = resourceActionMapper;
        this.resourceActionMapping = resourceActionMapping;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(ResourceActionSaveDTO saveDTO) {
        if (saveDTO.getId() == null) {
            ResourceAction resourceAction = resourceActionMapping.toResourceAction(saveDTO);
            resourceActionMapper.insert(resourceAction);
        } else {
            Integer id = saveDTO.getId();
            ResourceAction resourceAction = resourceActionMapper.selectByPrimaryKey(id).orElseThrow(() -> new BusinessException("找不到资源动作，资源动作id：" + id));
            resourceActionMapping.update(saveDTO, resourceAction);
            resourceActionMapper.updateByPrimaryKey(resourceAction);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        resourceActionMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByResourceId(Integer categoryId) {
        DeleteStatementProvider deleteStatementProvider = deleteFrom(ResourceActionDynamicSqlSupport.resourceAction)
                .where(ResourceActionDynamicSqlSupport.resourceId, isEqualTo(categoryId))
                .build().render(RenderingStrategies.MYBATIS3);
        resourceActionMapper.delete(deleteStatementProvider);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public ResourceActionVO findById(Integer id) {
        ResourceAction resource = resourceActionMapper.selectByPrimaryKey(id).orElse(new ResourceAction());
        return resourceActionMapping.toResourceActionVO(resource);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Page<ResourceActionVO> queryPage(Pageable pageable, ResourceActionPageQueryDTO queryDTO) {
        SortSpecification[] specifications = pageable.getSort().stream()
                .map(order -> {
                    SortSpecification specification = SimpleSortSpecification.of(order.getProperty());
                    if (order.isDescending()) {
                        return specification.descending();
                    }
                    return specification;
                }).toArray(SortSpecification[]::new);

        SelectStatementProvider selectStatementProvider = select(ResourceActionMapper.selectList)
                .from(ResourceActionDynamicSqlSupport.resourceAction)
                .where(ResourceActionDynamicSqlSupport.actionName, isLikeWhenPresent(queryDTO.getSearchText()),
                        or(ResourceActionDynamicSqlSupport.actionPath, isLikeWhenPresent(queryDTO.getSearchText())))
                .orderBy(specifications)
                .limit(pageable.getPageSize()).offset(pageable.getOffset())
                .build().render(RenderingStrategies.MYBATIS3);

        com.github.pagehelper.Page<ResourceActionVO> result = PageHelper
                .startPage(pageable.getPageNumber(), pageable.getPageSize())
                .doSelectPage(() -> resourceActionMapping.toResourceActionVOList(resourceActionMapper.selectMany(selectStatementProvider)));

        return new PageImpl<>(result.getResult(), pageable, result.getTotal());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Map<Integer, List<ResourceActionAssignVO>> findAllToAssign() {
        SelectStatementProvider selectStatementProvider = select(ResourceActionDynamicSqlSupport.id, ResourceActionDynamicSqlSupport.actionName)
                .from(ResourceActionDynamicSqlSupport.resourceAction)
                .orderBy(ResourceActionDynamicSqlSupport.actionNo)
                .build().render(RenderingStrategies.MYBATIS3);
        List<ResourceAction> actions = resourceActionMapper.selectMany(selectStatementProvider);
        List<ResourceActionAssignVO> actionAssignList = resourceActionMapping.toResourceActionAssignVOVOList(actions);
        return actionAssignList.stream().collect(Collectors.groupingBy(ResourceActionAssignVO::getResourceId));
    }
}

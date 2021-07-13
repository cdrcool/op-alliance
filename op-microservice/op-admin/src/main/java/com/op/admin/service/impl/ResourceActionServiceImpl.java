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
import com.op.framework.web.common.api.response.ResultCode;
import com.op.framework.web.common.api.response.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
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
    public Integer save(ResourceActionSaveDTO saveDTO) {
        if (saveDTO.getId() == null) {
            ResourceAction resourceAction = resourceActionMapping.toResourceAction(saveDTO);
            resourceActionMapper.insert(resourceAction);
            return resourceAction.getId();
        } else {
            Integer id = saveDTO.getId();
            ResourceAction resourceAction = resourceActionMapper.selectByPrimaryKey(id)
                    .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的资源动作"));
            resourceActionMapping.update(saveDTO, resourceAction);
            resourceActionMapper.updateByPrimaryKey(resourceAction);
            return resourceAction.getId();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        resourceActionMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Integer> ids) {
        ids.forEach(this::deleteById);
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
        ResourceAction resource = resourceActionMapper.selectByPrimaryKey(id)
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的资源动作"));
        return resourceActionMapping.toResourceActionVO(resource);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<ResourceActionVO> findByResourceId(Integer resourceId) {
        SelectStatementProvider selectStatementProvider = select(ResourceActionMapper.selectList)
                .from(ResourceActionDynamicSqlSupport.resourceAction)
                .where(ResourceActionDynamicSqlSupport.resourceId, isEqualTo(resourceId))
                .orderBy(ResourceActionDynamicSqlSupport.actionNo)
                .build().render(RenderingStrategies.MYBATIS3);
        List<ResourceAction> actions= resourceActionMapper.selectMany(selectStatementProvider);
        return resourceActionMapping.toResourceActionVOList(actions);
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
                .where(ResourceActionDynamicSqlSupport.actionName, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%"),
                        or(ResourceActionDynamicSqlSupport.actionPath, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%")))
                .orderBy(specifications)
                .build().render(RenderingStrategies.MYBATIS3);

        com.github.pagehelper.Page<ResourceAction> result = PageHelper
                .startPage(pageable.getPageNumber() + 1, pageable.getPageSize())
                .doSelectPage(() -> resourceActionMapper.selectMany(selectStatementProvider));

        return new PageImpl<>(resourceActionMapping.toResourceActionVOList(result.getResult()), pageable, result.getTotal());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Map<Integer, List<ResourceActionAssignVO>> findAllForAssign() {
        SelectStatementProvider selectStatementProvider =
                select(ResourceActionDynamicSqlSupport.id, ResourceActionDynamicSqlSupport.actionName)
                        .from(ResourceActionDynamicSqlSupport.resourceAction)
                        .orderBy(ResourceActionDynamicSqlSupport.actionNo)
                        .build().render(RenderingStrategies.MYBATIS3);
        List<ResourceAction> actions = resourceActionMapper.selectMany(selectStatementProvider);
        List<ResourceActionAssignVO> actionAssignList = resourceActionMapping.toResourceActionAssignVOVOList(actions);
        return actionAssignList.stream().collect(Collectors.groupingBy(ResourceActionAssignVO::getResourceId));
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<String> getPermissions(List<Integer> ids) {
        SelectStatementProvider selectStatementProvider =
                select(ResourceActionDynamicSqlSupport.permission)
                        .from(ResourceActionDynamicSqlSupport.resourceAction)
                        .build().render(RenderingStrategies.MYBATIS3);
        List<ResourceAction> actions = resourceActionMapper.selectMany(selectStatementProvider);
        return actions.stream().map(ResourceAction::getPermission).collect(Collectors.toList());
    }
}

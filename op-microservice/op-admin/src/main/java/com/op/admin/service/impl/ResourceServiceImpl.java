package com.op.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.op.admin.dto.ResourceActionSaveDTO;
import com.op.admin.dto.ResourcePageQueryDTO;
import com.op.admin.dto.ResourceSaveDTO;
import com.op.admin.entity.Resource;
import com.op.admin.mapper.ResourceDynamicSqlSupport;
import com.op.admin.mapper.ResourceMapper;
import com.op.admin.mapping.ResourceMapping;
import com.op.admin.service.ResourceActionService;
import com.op.admin.service.ResourceService;
import com.op.admin.vo.ResourceActionAssignVO;
import com.op.admin.vo.ResourceActionVO;
import com.op.admin.vo.ResourceAssignVO;
import com.op.admin.vo.ResourceVO;
import com.op.framework.web.common.api.response.ResultCode;
import com.op.framework.web.common.api.response.exception.BusinessException;
import io.swagger.models.auth.In;
import org.apache.commons.collections4.CollectionUtils;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

/**
 * 资源 Service Impl
 *
 * @author cdrcool
 */
@Service
public class ResourceServiceImpl implements ResourceService {
    private final ResourceMapper resourceMapper;
    private final ResourceMapping resourceMapping;
    private final ResourceActionService resourceActionService;

    public ResourceServiceImpl(ResourceMapper resourceMapper, ResourceMapping resourceMapping,
                               ResourceActionService resourceActionService) {
        this.resourceMapper = resourceMapper;
        this.resourceMapping = resourceMapping;
        this.resourceActionService = resourceActionService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(ResourceSaveDTO saveDTO) {
        List<ResourceActionSaveDTO> actions = saveDTO.getActions();

        if (saveDTO.getId() == null) {
            Resource resource = resourceMapping.toResource(saveDTO);
            resourceMapper.insert(resource);
            saveDTO.setId(resource.getId());

            // 保存资源动作列表
            actions.forEach(action -> {
                action.setResourceId(saveDTO.getId());
                resourceActionService.save(action);
            });
        } else {
            Integer id = saveDTO.getId();
            Resource resource = resourceMapper.selectByPrimaryKey(id)
                    .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的资源"));
            resourceMapping.update(saveDTO, resource);
            resourceMapper.updateByPrimaryKey(resource);

            // 保存资源动作列表
            List<Integer> curActionIds = new ArrayList<>();
            actions.forEach(action -> {
                action.setResourceId(id);
                resourceActionService.save(action);

                curActionIds.add(action.getId());
            });
            List<Integer> preActionsIds = resourceActionService.findIdsByResourceId(id);
            List<Integer> toDelActionIds = preActionsIds.stream().filter(actionId -> !curActionIds.contains(actionId)).collect(Collectors.toList());
            if (!CollectionUtils.isNotEmpty(toDelActionIds)) {
                resourceActionService.deleteByIds(toDelActionIds);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer ids) {
        resourceMapper.deleteByPrimaryKey(ids);
        resourceActionService.deleteByResourceId(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Integer> ids) {
        ids.forEach(this::deleteById);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByCategoryId(Integer categoryId) {
        DeleteStatementProvider deleteStatementProvider = deleteFrom(ResourceDynamicSqlSupport.resource)
                .where(ResourceDynamicSqlSupport.categoryId, isEqualTo(categoryId))
                .build().render(RenderingStrategies.MYBATIS3);
        resourceMapper.delete(deleteStatementProvider);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public ResourceVO findById(Integer id) {
        Resource resource = resourceMapper.selectByPrimaryKey(id)
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的资源"));
        ResourceVO resourceVO = resourceMapping.toResourceVO(resource);

        resourceVO.setActions(resourceActionService.findByResourceId(id));

        return resourceVO;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Page<ResourceVO> queryPage(Pageable pageable, ResourcePageQueryDTO queryDTO) {
        SortSpecification[] specifications = pageable.getSort().stream()
                .map(order -> {
                    SortSpecification specification = SimpleSortSpecification.of(order.getProperty());
                    if (order.isDescending()) {
                        return specification.descending();
                    }
                    return specification;
                }).toArray(SortSpecification[]::new);

        SelectStatementProvider selectStatementProvider = select(ResourceMapper.selectList)
                .from(ResourceDynamicSqlSupport.resource)
                .where(ResourceDynamicSqlSupport.categoryId, isEqualToWhenPresent(queryDTO.getCategoryId()))
                .and(ResourceDynamicSqlSupport.resourceName, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%"),
                        or(ResourceDynamicSqlSupport.resourcePath, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%")))
                .orderBy(specifications)
                .build().render(RenderingStrategies.MYBATIS3);

        com.github.pagehelper.Page<Resource> result = PageHelper
                .startPage(pageable.getPageNumber() + 1, pageable.getPageSize())
                .doSelectPage(() -> resourceMapper.selectMany(selectStatementProvider));

        return new PageImpl<>(resourceMapping.toResourceVOList(result.getResult()), pageable, result.getTotal());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<String> findNamesByCategoryId(Integer categoryId) {
        SelectStatementProvider selectStatementProvider = select(ResourceDynamicSqlSupport.resourceName)
                .from(ResourceDynamicSqlSupport.resource)
                .where(ResourceDynamicSqlSupport.categoryId, isEqualTo(categoryId))
                .build().render(RenderingStrategies.MYBATIS3);
        return resourceMapper.selectMany(selectStatementProvider).stream().map(Resource::getResourceName).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Map<Integer, List<ResourceAssignVO>> findAllForAssign() {
        SelectStatementProvider selectStatementProvider =
                select(ResourceDynamicSqlSupport.id, ResourceDynamicSqlSupport.resourceName)
                        .from(ResourceDynamicSqlSupport.resource)
                        .orderBy(ResourceDynamicSqlSupport.resourceNo)
                        .build().render(RenderingStrategies.MYBATIS3);
        List<Resource> resources = resourceMapper.selectMany(selectStatementProvider);
        List<ResourceAssignVO> resourceAssignList = resourceMapping.toResourceAssignVOList(resources);

        Map<Integer, List<ResourceActionAssignVO>> resourceActionAssignMap = resourceActionService.findAllForAssign();

        resourceAssignList.forEach(resource -> resource.setActions(resourceActionAssignMap.get(resource.getId())));

        return resourceAssignList.stream().collect(Collectors.groupingBy(ResourceAssignVO::getCategoryId));
    }
}

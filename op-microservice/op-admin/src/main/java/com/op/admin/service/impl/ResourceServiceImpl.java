package com.op.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.op.admin.dto.ResourcePageQueryDTO;
import com.op.admin.dto.ResourceSaveDTO;
import com.op.admin.entity.Resource;
import com.op.admin.mapper.ResourceDynamicSqlSupport;
import com.op.admin.mapper.ResourceMapper;
import com.op.admin.mapping.ResourceMapping;
import com.op.admin.service.ResourceActionService;
import com.op.admin.service.ResourceService;
import com.op.admin.vo.ResourceActionAssignVO;
import com.op.admin.vo.ResourceAssignVO;
import com.op.admin.vo.ResourceVO;
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
        if (saveDTO.getId() == null) {
            Resource resource = resourceMapping.toResource(saveDTO);
            resourceMapper.insert(resource);
        } else {
            Integer id = saveDTO.getId();
            Resource resource = resourceMapper.selectByPrimaryKey(id).orElseThrow(() -> new BusinessException("找不到资源，资源id：" + id));
            resourceMapping.update(saveDTO, resource);
            resourceMapper.updateByPrimaryKey(resource);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        resourceMapper.deleteByPrimaryKey(id);
        resourceActionService.deleteByResourceId(id);
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
        Resource resource = resourceMapper.selectByPrimaryKey(id).orElse(new Resource());
        return resourceMapping.toResourceVO(resource);
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
                .where(ResourceDynamicSqlSupport.resourceName, isLikeWhenPresent(queryDTO.getSearchText()),
                        or(ResourceDynamicSqlSupport.resourcePath, isLikeWhenPresent(queryDTO.getSearchText())))
                .orderBy(specifications)
                .limit(pageable.getPageSize()).offset(pageable.getOffset())
                .build().render(RenderingStrategies.MYBATIS3);

        com.github.pagehelper.Page<ResourceVO> result = PageHelper
                .startPage(pageable.getPageNumber(), pageable.getPageSize())
                .doSelectPage(() -> resourceMapping.toResourceVOList(resourceMapper.selectMany(selectStatementProvider)));

        return new PageImpl<>(result.getResult(), pageable, result.getTotal());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Map<Integer, List<ResourceAssignVO>> findAllForAssign() {
        SelectStatementProvider selectStatementProvider = select(ResourceDynamicSqlSupport.id, ResourceDynamicSqlSupport.resourceName)
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

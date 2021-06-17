package com.op.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.op.admin.dto.ResourceCategoryListQueryDTO;
import com.op.admin.dto.ResourceCategorySaveDTO;
import com.op.admin.entity.ResourceCategory;
import com.op.admin.mapper.ResourceCategoryDynamicSqlSupport;
import com.op.admin.mapper.ResourceCategoryMapper;
import com.op.admin.mapping.ResourceCategoryMapping;
import com.op.admin.service.ResourceCategoryService;
import com.op.admin.service.ResourceService;
import com.op.admin.vo.ResourceCategoryVO;
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
 * 资源分类 Service Impl
 *
 * @author cdrcool
 */
@Service
public class ResourceCategoryServiceImpl implements ResourceCategoryService {
    private final ResourceCategoryMapper resourceCategoryMapper;
    private final ResourceCategoryMapping resourceCategoryMapping;
    private final ResourceService resourceService;

    public ResourceCategoryServiceImpl(ResourceCategoryMapper resourceCategoryMapper, ResourceCategoryMapping resourceCategoryMapping,
                                       ResourceService resourceService) {
        this.resourceCategoryMapper = resourceCategoryMapper;
        this.resourceCategoryMapping = resourceCategoryMapping;
        this.resourceService = resourceService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(ResourceCategorySaveDTO saveDTO) {
        if (saveDTO.getId() == null) {
            ResourceCategory resourceCategory = resourceCategoryMapping.toResourceCategory(saveDTO);
            resourceCategoryMapper.insert(resourceCategory);
        } else {
            Integer id = saveDTO.getId();
            ResourceCategory resourceCategory = resourceCategoryMapper.selectByPrimaryKey(id).orElseThrow(() -> new BusinessException("找不到资源分类，资源分类id：" + id));
            resourceCategoryMapping.update(saveDTO, resourceCategory);
            resourceCategoryMapper.updateByPrimaryKey(resourceCategory);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        resourceCategoryMapper.deleteByPrimaryKey(id);
        resourceService.deleteByCategoryId(id);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public ResourceCategoryVO findById(Integer id) {
        ResourceCategory resourceCategory = resourceCategoryMapper.selectByPrimaryKey(id).orElse(new ResourceCategory());
        return resourceCategoryMapping.toResourceCategoryVO(resourceCategory);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Page<ResourceCategoryVO> queryPage(Pageable pageable, ResourceCategoryListQueryDTO queryDTO) {
        SortSpecification[] specifications = pageable.getSort().stream()
                .map(order -> {
                    SortSpecification specification = SimpleSortSpecification.of(order.getProperty());
                    if (order.isDescending()) {
                        return specification.descending();
                    }
                    return specification;
                }).toArray(SortSpecification[]::new);

        SelectStatementProvider selectStatementProvider = select(ResourceCategoryMapper.selectList)
                .from(ResourceCategoryDynamicSqlSupport.resourceCategory)
                .where(ResourceCategoryDynamicSqlSupport.categoryName, isLikeWhenPresent(queryDTO.getSearchText()))
                .or(ResourceCategoryDynamicSqlSupport.serverName, isLikeWhenPresent(queryDTO.getSearchText()))
                .orderBy(specifications)
                .limit(pageable.getPageSize()).offset(pageable.getOffset())
                .build().render(RenderingStrategies.MYBATIS3);

        com.github.pagehelper.Page<ResourceCategoryVO> result = PageHelper
                .startPage(pageable.getPageNumber(), pageable.getPageSize())
                .doSelectPage(() -> resourceCategoryMapping.toResourceCategoryVOList(resourceCategoryMapper.selectMany(selectStatementProvider)));

        return new PageImpl<>(result.getResult(), pageable, result.getTotal());
    }
}

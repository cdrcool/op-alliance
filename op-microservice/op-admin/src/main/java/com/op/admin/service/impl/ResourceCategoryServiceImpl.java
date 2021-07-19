package com.op.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.op.admin.dto.ResourceCategoryPageQueryDTO;
import com.op.admin.dto.ResourceCategorySaveDTO;
import com.op.admin.entity.ResourceCategory;
import com.op.admin.mapper.ResourceCategoryDynamicSqlSupport;
import com.op.admin.mapper.ResourceCategoryMapper;
import com.op.admin.mapping.ResourceCategoryMapping;
import com.op.admin.service.ResourceCategoryService;
import com.op.admin.service.ResourceService;
import com.op.admin.vo.ResourceAssignVO;
import com.op.admin.vo.ResourceCategoryAssignVO;
import com.op.admin.vo.ResourceCategoryVO;
import com.op.admin.vo.SelectVO;
import com.op.framework.web.common.api.response.ResultCode;
import com.op.framework.web.common.api.response.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.dynamic.sql.SortSpecification;
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

import static org.mybatis.dynamic.sql.SqlBuilder.*;

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
            ResourceCategory resourceCategory = resourceCategoryMapper.selectByPrimaryKey(id)
                    .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的资源分类"));
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Integer> ids) {
        ids.forEach(this::deleteById);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public ResourceCategoryVO findById(Integer id) {
        ResourceCategory resourceCategory = resourceCategoryMapper.selectByPrimaryKey(id)
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的资源分类"));
        ResourceCategoryVO resourceCategoryVO =  resourceCategoryMapping.toResourceCategoryVO(resourceCategory);

        resourceCategoryVO.setResourceNames(resourceService.findNamesByCategoryId(id));

        return resourceCategoryVO;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Page<ResourceCategoryVO> queryPage(Pageable pageable, ResourceCategoryPageQueryDTO queryDTO) {
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
                .where(ResourceCategoryDynamicSqlSupport.categoryName, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%"),
                        or(ResourceCategoryDynamicSqlSupport.serverName, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%")))
                .orderBy(specifications)
                .build().render(RenderingStrategies.MYBATIS3);

        com.github.pagehelper.Page<ResourceCategory> result = PageHelper
                .startPage(pageable.getPageNumber() + 1, pageable.getPageSize())
                .doSelectPage(() -> resourceCategoryMapper.selectMany(selectStatementProvider));

        return new PageImpl<>(resourceCategoryMapping.toResourceCategoryVOList(result.getResult()), pageable, result.getTotal());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<SelectVO> querySelectList(ResourceCategoryPageQueryDTO queryDTO) {
        SelectStatementProvider selectStatementProvider = select(ResourceCategoryDynamicSqlSupport.id, ResourceCategoryDynamicSqlSupport.categoryName)
                .from(ResourceCategoryDynamicSqlSupport.resourceCategory)
                .where(ResourceCategoryDynamicSqlSupport.categoryName, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%"),
                        or(ResourceCategoryDynamicSqlSupport.serverName, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%")))
                .orderBy(ResourceCategoryDynamicSqlSupport.categoryNo)
                .build().render(RenderingStrategies.MYBATIS3);
        List<ResourceCategory> list = resourceCategoryMapper.selectMany(selectStatementProvider);

        return resourceCategoryMapping.toSelectVOList(list);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<ResourceCategoryAssignVO> findAllForAssign() {
        SelectStatementProvider selectStatementProvider =
                select(ResourceCategoryDynamicSqlSupport.id, ResourceCategoryDynamicSqlSupport.categoryName)
                        .from(ResourceCategoryDynamicSqlSupport.resourceCategory)
                        .orderBy(ResourceCategoryDynamicSqlSupport.categoryNo)
                        .build().render(RenderingStrategies.MYBATIS3);
        List<ResourceCategory> categories = resourceCategoryMapper.selectMany(selectStatementProvider);
        List<ResourceCategoryAssignVO> categoryAssignList = resourceCategoryMapping.toResourceCategoryAssignVOList(categories);

        Map<Integer, List<ResourceAssignVO>> resourceAssignMap = resourceService.findAllForAssign();

        categoryAssignList.forEach(category -> category.setResources(resourceAssignMap.get(category.getId())));

        return categoryAssignList;
    }
}

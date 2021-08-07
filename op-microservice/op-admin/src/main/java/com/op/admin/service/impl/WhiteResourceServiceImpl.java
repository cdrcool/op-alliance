package com.op.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.op.admin.dto.WhiteResourcePageQueryDTO;
import com.op.admin.dto.WhiteResourceSaveDTO;
import com.op.admin.entity.WhiteResource;
import com.op.admin.mapper.RoleDynamicSqlSupport;
import com.op.admin.mapper.WhiteResourceDynamicSqlSupport;
import com.op.admin.mapper.WhiteResourceMapper;
import com.op.admin.mapping.WhiteResourceMapping;
import com.op.admin.service.WhiteResourceService;
import com.op.admin.vo.WhiteResourceVO;
import com.op.framework.web.common.api.response.ResultCode;
import com.op.framework.web.common.api.response.exception.BusinessException;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

/**
 * 白名单资源 Service
 *
 * @author cdrcool
 */
@Service
public class WhiteResourceServiceImpl implements WhiteResourceService {
    /**
     * 白名单资源缓存 key
     */
    public static final String WHITE_RESOURCE_KEY = "white:resource";

    private final WhiteResourceMapper whiteResourceMapper;
    private final WhiteResourceMapping whiteResourceMapping;
    private final RedisTemplate<String, Object> redisTemplate;

    public WhiteResourceServiceImpl(WhiteResourceMapper whiteResourceMapper,
                                    WhiteResourceMapping whiteResourceMapping,
                                    RedisTemplate<String, Object> redisTemplate) {
        this.whiteResourceMapper = whiteResourceMapper;
        this.whiteResourceMapping = whiteResourceMapping;
        this.redisTemplate = redisTemplate;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(WhiteResourceSaveDTO saveDTO) {
        // 校验资源名称/资源路径是否重复
        validateResourceNameAndResourcePath(saveDTO.getId(), saveDTO.getResourceName(), saveDTO.getResourcePath());

        if (saveDTO.getId() == null) {
            WhiteResource whiteResource = whiteResourceMapping.toWhiteResource(saveDTO);
            whiteResourceMapper.insert(whiteResource);
            saveDTO.setId(whiteResource.getId());
        } else {
            Integer id = saveDTO.getId();
            WhiteResource whiteResource = whiteResourceMapper.selectByPrimaryKey(id)
                    .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的资源"));
            whiteResourceMapping.update(saveDTO, whiteResource);
            whiteResourceMapper.updateByPrimaryKey(whiteResource);
        }
    }

    /**
     * 校验资源名称/资源路径是否重复
     *
     * @param id           主键
     * @param resourceName 资源名称
     * @param resourcePath 资源路径
     */
    private void validateResourceNameAndResourcePath(Integer id, String resourceName, String resourcePath) {
        SelectStatementProvider selectStatementProvider = select(WhiteResourceDynamicSqlSupport.resourceName, WhiteResourceDynamicSqlSupport.resourcePath)
                .from(WhiteResourceDynamicSqlSupport.whiteResource)
                .where(WhiteResourceDynamicSqlSupport.resourceName, isEqualTo(resourceName), or(WhiteResourceDynamicSqlSupport.resourcePath, isEqualTo(resourcePath)))
                .and(WhiteResourceDynamicSqlSupport.id, isNotEqualToWhenPresent(id))
                .build().render(RenderingStrategies.MYBATIS3);
        Optional<WhiteResource> optional = whiteResourceMapper.selectOne(selectStatementProvider);
        optional.ifPresent(whiteResource -> {
            if (resourceName.equals(whiteResource.getResourceName())) {
                throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "已存在相同资源名称的白名单资源，资源名称不能重复");
            } else {
                throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "已存在相同资源路径的白名单资源，资源路径不能重复");
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        whiteResourceMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Integer> ids) {
        DeleteStatementProvider deleteStatementProvider = deleteFrom(WhiteResourceDynamicSqlSupport.whiteResource)
                .where(WhiteResourceDynamicSqlSupport.id, isIn(ids))
                .build().render(RenderingStrategies.MYBATIS3);
        whiteResourceMapper.delete(deleteStatementProvider);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public WhiteResourceVO findById(Integer id) {
        WhiteResource whiteResource = whiteResourceMapper.selectByPrimaryKey(id)
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的白名单资源"));
        return whiteResourceMapping.toWhiteResourceVO(whiteResource);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Page<WhiteResourceVO> queryPage(Pageable pageable, WhiteResourcePageQueryDTO queryDTO) {
        SortSpecification[] specifications = pageable.getSort().stream()
                .map(order -> {
                    SortSpecification specification = SimpleSortSpecification.of(order.getProperty());
                    if (order.isDescending()) {
                        return specification.descending();
                    }
                    return specification;
                }).toArray(SortSpecification[]::new);

        SelectStatementProvider selectStatementProvider = select(WhiteResourceMapper.selectList)
                .from(WhiteResourceDynamicSqlSupport.whiteResource)
                .where(WhiteResourceDynamicSqlSupport.resourceName, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%"),
                        or(WhiteResourceDynamicSqlSupport.resourcePath, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%")))
                .orderBy(specifications)
                .build().render(RenderingStrategies.MYBATIS3);

        com.github.pagehelper.Page<WhiteResource> result = PageHelper
                .startPage(pageable.getPageNumber() + 1, pageable.getPageSize())
                .doSelectPage(() -> whiteResourceMapper.selectMany(selectStatementProvider));

        return new PageImpl<>(whiteResourceMapping.toWhiteResourceVOList(result.getResult()), pageable, result.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changeEnabled(List<Integer> ids, boolean enable) {
        UpdateStatementProvider updateStatement = SqlBuilder.update(WhiteResourceDynamicSqlSupport.whiteResource)
                .set(RoleDynamicSqlSupport.status).equalTo(enable ? 1 : 0)
                .where(RoleDynamicSqlSupport.id, isIn(ids))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        whiteResourceMapper.update(updateStatement);
    }

    @PostConstruct
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<String> getWhiteResourcePaths() {
        SelectStatementProvider selectStatementProvider = select(WhiteResourceDynamicSqlSupport.resourcePath)
                .from(WhiteResourceDynamicSqlSupport.whiteResource)
                .where(WhiteResourceDynamicSqlSupport.status, isEqualTo(1))
                .build().render(RenderingStrategies.MYBATIS3);
        List<String> list = whiteResourceMapper.selectMany(selectStatementProvider).stream()
                .map((WhiteResource::getResourcePath)).collect(Collectors.toList());
        redisTemplate.opsForValue().set(WHITE_RESOURCE_KEY, list);
        return list;
    }
}

package com.op.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.op.admin.dto.OauthClientDetailsSaveDTO;
import com.op.admin.mapper.OauthClientDetailsMapper;
import com.op.admin.mapper.UserDynamicSqlSupport;
import com.op.admin.mapping.OauthClientDetailsMapping;
import com.op.admin.dto.OauthClientDetailsDTO;
import com.op.admin.dto.OauthClientDetailsPageQueryDTO;
import com.op.admin.entity.OauthClientDetails;
import com.op.admin.mapper.OauthClientDetailsDynamicSqlSupport;
import com.op.admin.vo.OauthClientDetailsVO;
import com.op.admin.service.OauthClientDetailsService;
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

import static org.mybatis.dynamic.sql.SqlBuilder.*;

/**
 * oauth2-client Service Impl
 *
 * @author cdrcool
 */
@Service
public class OauthClientDetailsServiceImpl implements OauthClientDetailsService {
    private final OauthClientDetailsMapper oauthClientDetailsMapper;
    private final OauthClientDetailsMapping oauthClientDetailsMapping;

    public OauthClientDetailsServiceImpl(OauthClientDetailsMapper oauthClientDetailsMapper, OauthClientDetailsMapping oauthClientDetailsMapping) {
        this.oauthClientDetailsMapper = oauthClientDetailsMapper;
        this.oauthClientDetailsMapping = oauthClientDetailsMapping;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(OauthClientDetailsSaveDTO saveDTO) {
        // 校验客户端标识是否重复
        validateClientId(saveDTO.getId(), saveDTO.getClientId());

        if (saveDTO.getId() == null) {
            OauthClientDetails oauthClientDetails = oauthClientDetailsMapping.toOauthClientDetails(saveDTO);
            oauthClientDetailsMapper.insert(oauthClientDetails);
        } else {
            Integer id = saveDTO.getId();
            OauthClientDetails oauthClientDetails = oauthClientDetailsMapper.selectByPrimaryKey(id)
                    .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的OAuth客户端"));
            oauthClientDetailsMapping.update(saveDTO, oauthClientDetails);
            oauthClientDetailsMapper.updateByPrimaryKey(oauthClientDetails);
        }
    }

    /**
     * 校验客户端标识是否重复
     *
     * @param id       主键
     * @param clientId 客户端标识
     */
    private void validateClientId(Integer id, String clientId) {
        SelectStatementProvider selectStatementProvider = countFrom(OauthClientDetailsDynamicSqlSupport.oauthClientDetails)
                .where(OauthClientDetailsDynamicSqlSupport.clientId, isEqualTo(clientId))
                .and(OauthClientDetailsDynamicSqlSupport.id, isNotEqualToWhenPresent(id))
                .build().render(RenderingStrategies.MYBATIS3);
        long count = oauthClientDetailsMapper.count(selectStatementProvider);
        if (count > 0) {
            throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "已存在相同客户端标识的客户端，客户端标识不能重复");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        oauthClientDetailsMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Integer> ids) {
        ids.forEach(this::deleteById);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public OauthClientDetailsVO findById(Integer id) {
        OauthClientDetails resource = oauthClientDetailsMapper.selectByPrimaryKey(id)
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的OAuth客户端"));
        return oauthClientDetailsMapping.toOauthClientDetailsVO(resource);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public OauthClientDetailsDTO findByClientId(String clientId) {
        SelectStatementProvider selectStatementProvider = select(OauthClientDetailsMapper.selectList)
                .from(OauthClientDetailsDynamicSqlSupport.oauthClientDetails)
                .where(OauthClientDetailsDynamicSqlSupport.clientId, isEqualTo(clientId))
                .build().render(RenderingStrategies.MYBATIS3);
        OauthClientDetails oauthClientDetails = oauthClientDetailsMapper.selectOne(selectStatementProvider)
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到clientId为【" + clientId + "】的OAuth客户端"));
        return oauthClientDetailsMapping.toOauthClientDetailsDTO(oauthClientDetails);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Page<OauthClientDetailsVO> queryPage(Pageable pageable, OauthClientDetailsPageQueryDTO queryDTO) {
        SortSpecification[] specifications = pageable.getSort().stream()
                .map(order -> {
                    SortSpecification specification = SimpleSortSpecification.of(order.getProperty());
                    if (order.isDescending()) {
                        return specification.descending();
                    }
                    return specification;
                }).toArray(SortSpecification[]::new);

        SelectStatementProvider selectStatementProvider = select(OauthClientDetailsMapper.selectList)
                .from(OauthClientDetailsDynamicSqlSupport.oauthClientDetails)
                .where(OauthClientDetailsDynamicSqlSupport.clientId, isLike(queryDTO.getKeyword())
                        .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%"))
                .orderBy(specifications)
                .build().render(RenderingStrategies.MYBATIS3);

        com.github.pagehelper.Page<OauthClientDetails> result = PageHelper
                .startPage(pageable.getPageNumber() + 1, pageable.getPageSize())
                .doSelectPage(() -> oauthClientDetailsMapper.selectMany(selectStatementProvider));

        return new PageImpl<>(oauthClientDetailsMapping.toOauthClientDetailsVOList(result.getResult()), pageable, result.getTotal());
    }
}

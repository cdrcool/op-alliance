package com.op.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.op.admin.dto.OauthClientDetailsSaveDTO;
import com.op.admin.mapper.OauthClientDetailsMapper;
import com.op.admin.mapping.OauthClientDetailsMapping;
import com.op.admin.dto.OauthClientDetailsDTO;
import com.op.admin.dto.OauthClientDetailsPageQueryDTO;
import com.op.admin.entity.OauthClientDetails;
import com.op.admin.mapper.OauthClientDetailsDynamicSqlSupport;
import com.op.admin.vo.OauthClientDetailsVO;
import com.op.admin.service.OauthClientDetailsService;
import com.op.framework.web.common.api.response.ResultCode;
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
        if (saveDTO.getId() == null) {
            OauthClientDetails oauthClientDetails = oauthClientDetailsMapping.toOauthClientDetails(saveDTO);
            oauthClientDetailsMapper.insert(oauthClientDetails);
        } else {
            Integer id = saveDTO.getId();
            OauthClientDetails oauthClientDetails = oauthClientDetailsMapper.selectByPrimaryKey(id)
                    .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的 oauth2-client"));
            oauthClientDetailsMapping.update(saveDTO, oauthClientDetails);
            oauthClientDetailsMapper.updateByPrimaryKey(oauthClientDetails);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        oauthClientDetailsMapper.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public OauthClientDetailsVO findById(Integer id) {
        OauthClientDetails resource = oauthClientDetailsMapper.selectByPrimaryKey(id).orElse(new OauthClientDetails());
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
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到clientId为【" + clientId + "】的 oauth2-client"));
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
                .where(OauthClientDetailsDynamicSqlSupport.clientId, isLikeWhenPresent(queryDTO.getSearchText()))
                .orderBy(specifications)
                .limit(pageable.getPageSize()).offset(pageable.getOffset())
                .build().render(RenderingStrategies.MYBATIS3);

        com.github.pagehelper.Page<OauthClientDetailsVO> result = PageHelper
                .startPage(pageable.getPageNumber(), pageable.getPageSize())
                .doSelectPage(() -> oauthClientDetailsMapping.toOauthClientDetailsVOList(oauthClientDetailsMapper.selectMany(selectStatementProvider)));

        return new PageImpl<>(result.getResult(), pageable, result.getTotal());
    }
}

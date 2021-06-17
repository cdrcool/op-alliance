package com.op.admin.service.impl;

import com.op.admin.dto.OrganizationListQueryDTO;
import com.op.admin.dto.OrganizationSaveDTO;
import com.op.admin.entity.Organization;
import com.op.admin.mapper.OrganizationMapper;
import com.op.admin.mapping.OrganizationMapping;
import com.op.admin.service.OrganizationService;
import com.op.admin.vo.OrganizationTreeVO;
import com.op.admin.vo.OrganizationVO;
import com.op.framework.web.common.api.response.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 组织 Service Impl
 *
 * @author cdrcool
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationMapper organizationMapper;
    private final OrganizationMapping organizationMapping;

    public OrganizationServiceImpl(OrganizationMapper organizationMapper, OrganizationMapping organizationMapping) {
        this.organizationMapper = organizationMapper;
        this.organizationMapping = organizationMapping;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(OrganizationSaveDTO saveDTO) {
        if (saveDTO.getId() == null) {
            Organization resource = organizationMapping.toOrganization(saveDTO);
            organizationMapper.insert(resource);
        } else {
            Integer id = saveDTO.getId();
            Organization resource = organizationMapper.selectByPrimaryKey(id).orElseThrow(() -> new BusinessException("找不到组织，组织id：" + id));
            organizationMapping.update(saveDTO, resource);
            organizationMapper.updateByPrimaryKey(resource);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        organizationMapper.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public OrganizationVO findById(Integer id) {
        Organization resource = organizationMapper.selectByPrimaryKey(id).orElse(new Organization());
        return organizationMapping.toOrganizationVO(resource);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public OrganizationTreeVO queryTreeList(OrganizationListQueryDTO queryDTO) {
        return null;
    }
}

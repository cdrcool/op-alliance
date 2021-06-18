package com.op.admin.service;

import com.op.admin.dto.OrganizationListQueryDTO;
import com.op.admin.dto.OrganizationSaveDTO;
import com.op.admin.vo.OrganizationTreeVO;
import com.op.admin.vo.OrganizationVO;

import java.util.List;

/**
 * 组织 Service
 *
 * @author cdrcool
 */
public interface OrganizationService {

    /**
     * 保存组织
     *
     * @param saveDTO 组织保存 dto
     */
    void save(OrganizationSaveDTO saveDTO);

    /**
     * 删除组织
     *
     * @param id 组织id
     */
    void deleteById(Integer id);

    /**
     * 查找组织
     *
     * @param id 组织id
     * @return 组织 vo
     */
    OrganizationVO findById(Integer id);

    /**
     * 查询组织树
     *
     * @param queryDTO 查询对象
     * @return 组织树 vo
     */
    OrganizationTreeVO queryTree(OrganizationListQueryDTO queryDTO);
}

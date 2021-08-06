package com.op.admin.service;

import com.op.admin.dto.WhiteResourcePageQueryDTO;
import com.op.admin.dto.WhiteResourceSaveDTO;
import com.op.admin.vo.WhiteResourceVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 白名单资源 Service
 *
 * @author cdrcool
 */
public interface WhiteResourceService {

    /**
     * 保存白名单资源
     *
     * @param saveDTO 白名单资源保存 dto
     */
    void save(WhiteResourceSaveDTO saveDTO);

    /**
     * 删除白名单资源
     *
     * @param id 白名单资源 id
     */
    void deleteById(Integer id);

    /**
     * 批量删除白名单资源
     *
     * @param ids 白名单资源 id
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 查找白名单资源
     *
     * @param id 白名单资源 id
     * @return 白名单资源 vo
     */
    WhiteResourceVO findById(Integer id);

    /**
     * 分页查询白名单资源
     *
     * @param pageable 分页对象
     * @param queryDTO 查询对象
     * @return 白名单资源 vo 分页列表
     */
    Page<WhiteResourceVO> queryPage(Pageable pageable, WhiteResourcePageQueryDTO queryDTO);
}

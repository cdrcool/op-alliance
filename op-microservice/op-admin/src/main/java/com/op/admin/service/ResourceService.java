package com.op.admin.service;

import com.op.admin.dto.ResourceListQueryDTO;
import com.op.admin.dto.ResourceSaveDTO;
import com.op.admin.vo.ResourceVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 资源 Service
 *
 * @author cdrcool
 */
public interface ResourceService {

    /**
     * 创建资源
     *
     * @param saveDTO 资源保存 dto
     * @return 初始密码
     */
    String create(ResourceSaveDTO saveDTO);

    /**
     * 修改资源
     *
     * @param saveDTO 资源保存 dto
     */
    void update(ResourceSaveDTO saveDTO);

    /**
     * 删除资源
     *
     * @param id 资源id
     */
    void deleteById(Integer id);

    /**
     * 查找资源
     *
     * @param id 资源id
     * @return 资源 vo
     */
    ResourceVO findById(Integer id);

    /**
     * 分页查询资源
     *
     * @param pageable 分页对象
     * @param queryDTO 查询对象
     * @return 资源 vo 分页列表
     */
    Page<ResourceVO> queryPage(Pageable pageable, ResourceListQueryDTO queryDTO);
}

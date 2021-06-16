package com.op.admin.service;

import com.op.admin.dto.RoleListQueryDTO;
import com.op.admin.dto.RoleSaveDTO;
import com.op.admin.vo.RoleVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 角色 Service
 *
 * @author cdrcool
 */
public interface RoleService {

    /**
     * 创建角色
     *
     * @param saveDTO 角色保存 dto
     * @return 初始密码
     */
    String create(RoleSaveDTO saveDTO);

    /**
     * 修改角色
     *
     * @param saveDTO 角色保存 dto
     */
    void update(RoleSaveDTO saveDTO);

    /**
     * 删除角色
     *
     * @param id 角色id
     */
    void deleteById(Integer id);

    /**
     * 查找角色
     *
     * @param id 角色id
     * @return 角色 vo
     */
    RoleVO findById(Integer id);

    /**
     * 分页查询角色
     *
     * @param pageable 分页对象
     * @param queryDTO 查询对象
     * @return 角色 vo 分页列表
     */
    Page<RoleVO> queryPage(Pageable pageable, RoleListQueryDTO queryDTO);
}

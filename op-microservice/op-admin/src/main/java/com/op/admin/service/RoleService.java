package com.op.admin.service;

import com.op.admin.dto.RoleListQueryDTO;
import com.op.admin.dto.RoleSaveDTO;
import com.op.admin.vo.RoleTreeVO;
import com.op.admin.vo.RoleVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 角色 Service
 *
 * @author cdrcool
 */
public interface RoleService {

    /**
     * 保存角色
     *
     * @param saveDTO 角色保存 dto
     */
    void save(RoleSaveDTO saveDTO);

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
     * 分页查询角色树列表
     *
     * @param pageable 分页对象
     * @param queryDTO 查询对象
     * @return 角色树 vo 分页列表
     */
    Page<RoleTreeVO> queryPage(Pageable pageable, RoleListQueryDTO queryDTO);

    /**
     * 启用/禁用角色
     *
     * @param id 角色id
     * @param enable 启用 or 禁用
     */
    void enableDisable(Integer id, boolean enable);
}

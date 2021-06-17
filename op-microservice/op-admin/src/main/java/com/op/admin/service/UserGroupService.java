package com.op.admin.service;

import com.op.admin.dto.UserGroupListQueryDTO;
import com.op.admin.dto.UserGroupSaveDTO;
import com.op.admin.vo.UserGroupVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 用户组 Service
 *
 * @author cdrcool
 */
public interface UserGroupService {

    /**
     * 保存用户组
     *
     * @param saveDTO 用户组保存 dto
     */
    void save(UserGroupSaveDTO saveDTO);

    /**
     * 删除用户组
     *
     * @param id 用户组id
     */
    void deleteById(Integer id);

    /**
     * 查找用户组
     *
     * @param id 用户组id
     * @return 用户组 vo
     */
    UserGroupVO findById(Integer id);

    /**
     * 分页查询用户组
     *
     * @param pageable 分页对象
     * @param queryDTO 查询对象
     * @return 用户组 vo 分页列表
     */
    Page<UserGroupVO> queryPage(Pageable pageable, UserGroupListQueryDTO queryDTO);
}

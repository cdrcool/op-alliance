package com.op.admin.service;

import com.op.admin.dto.*;
import com.op.admin.vo.ResourceCategoryAssignVO;
import com.op.admin.vo.RoleAssignVO;
import com.op.admin.vo.UserVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 用户 Service
 *
 * @author cdrcool
 */
public interface UserService {

    /**
     * 创建用户
     *
     * @param createDTO 用户创建 dto
     * @return 初始密码
     */
    String create(UserCreateDTO createDTO);

    /**
     * 修改用户密码
     *
     * @param changePasswordDto 用户修改密码 dto
     */
    void changePassword(UserChangePasswordDTO changePasswordDto);

    /**
     * 更新用户
     *
     * @param saveDTO 用户保存 dto
     */
    void save(UserSaveDTO saveDTO);

    /**
     * 删除用户
     *
     * @param id 用户 id
     */
    void deleteById(Integer id);

    /**
     * 批量删除用户
     *
     * @param ids 用户 ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 删除组织下所有的用户
     *
     * @param orgIds 组织 ids
     */
    void deleteByOrgIds(List<Integer> orgIds);

    /**
     * 查找用户
     *
     * @param id 用户 id
     * @return 用户 vo
     */
    UserVO findById(Integer id);

    /**
     * 批量查找用户
     *
     * @param ids 用户 ids
     * @return 用户 vo 列表
     */
    List<UserVO> findByIds(List<Integer> ids);

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户 vo
     */
    UserDTO findByUserName(String username);

    /**
     * 分页查询用户
     *
     * @param pageable 分页对象
     * @param queryDTO 查询对象
     * @return 用户 vo 分页列表
     */
    Page<UserVO> queryPage(Pageable pageable, UserPageQueryDTO queryDTO);

    /**
     * 启用/禁用用户
     *
     * @param ids     用户 ids
     * @param enable 启用 or 禁用
     */
    void changeEnabled(List<Integer> ids, boolean enable);

    /**
     * 分配角色
     *
     * @param id      用户 id
     * @param roleIds 角色 ids
     */
    void assignRoles(Integer id, List<Integer> roleIds);

    /**
     * 分配资源动作
     *
     * @param id                用户 id
     * @param resourceActionIds 资源动作 ids
     */
    void assignResourceActions(Integer id, List<Integer> resourceActionIds);

    /**
     * 组织授权
     *
     * @param id                用户 id
     * @param organizationIds 组织 ids
     */
    void assignOrganizations(Integer id, List<Integer> organizationIds);

    /**
     * 获取用户角色分配情况
     *
     * @param id 用户 id
     * @return 角色分配 VO 列表
     */
    List<RoleAssignVO> loadAssignedRoles(Integer id);

    /**
     * 获取用户资源分配情况
     *
     * @param id 用户 id
     * @return 资源分类分配 VO 列表
     */
    List<ResourceCategoryAssignVO> loadAssignedResources(Integer id);

    /**
     * 获取用户授权的组织 ids
     *
     * @param id 用户 id
     * @return 组织 ids
     */
    List<Integer> getAssignedOrganizationIds(Integer id);
}

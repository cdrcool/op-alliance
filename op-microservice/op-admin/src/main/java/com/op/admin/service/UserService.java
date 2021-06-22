package com.op.admin.service;

import com.op.admin.dto.UserChangePasswordDTO;
import com.op.admin.dto.UserCreateDTO;
import com.op.admin.dto.UserPageQueryDTO;
import com.op.admin.dto.UserUpdateDTO;
import com.op.admin.vo.MenuAssignVO;
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
     * @param updateDTO 用户更新 dto
     */
    void update(UserUpdateDTO updateDTO);

    /**
     * 删除用户
     *
     * @param id 用户 id
     */
    void deleteById(Integer id);

    /**
     * 查找用户
     *
     * @param id 用户 id
     * @return 用户 vo
     */
    UserVO findById(Integer id);

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
     * @param id     用户 id
     * @param enable 启用 or 禁用
     */
    void changeEnabled(Integer id, boolean enable);

    /**
     * 分配角色
     *
     * @param id      用户 id
     * @param roleIds 角色 ids
     */
    void assignRoles(Integer id, List<Integer> roleIds);

    /**
     * 分配资源
     *
     * @param id                用户 id
     * @param resourceActionIds 资源动作 ids
     */
    void assignResources(Integer id, List<Integer> resourceActionIds);

    /**
     * 分配菜单
     *
     * @param id      用户 id
     * @param menuIds 菜单 ids
     */
    void assignMenus(Integer id, List<Integer> menuIds);

    /**
     * 获取用户所分配的角色 ids
     *
     * @param id 用户 id
     * @return 角色 ids
     */
    List<Integer> getAssignedRoleIds(Integer id);

    /**
     * 获取用户所分配的资源动作 ids
     *
     * @param id 用户 id
     * @return 资源动作 ids
     */
    List<Integer> getAssignedResourceActionIds(Integer id);

    /**
     * 获取用户所分配的菜单 ids
     *
     * @param id 用户 id
     * @return 菜单 ids
     */
    List<Integer> getAssignedMenuIds(Integer id);

    /**
     * 查找所有角色，以及用户分配情况
     *
     * @param id 用户 id
     * @return 角色分配 VO 列表
     */
    List<RoleAssignVO> loadRoles(Integer id);

    /**
     * 查找所有资源，以及用户分配情况
     *
     * @param id 用户 id
     * @return 资源分类分配 VO 列表
     */
    List<ResourceCategoryAssignVO> loadResources(Integer id);

    /**
     * 查找所有菜单，以及用户分配情况
     *
     * @param id 用户 id
     * @return 菜单分配 VO 列表
     */
    List<MenuAssignVO> loadMenus(Integer id);
}

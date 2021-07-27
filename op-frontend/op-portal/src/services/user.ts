import request from "../utils/request";
import {PageResult} from "../models/PageResult";
import {User} from "../models/User";
import {Role} from "../models/Role";
import {ResourceCategory} from "../models/ResourceCategory";

/**
 * 创建用户（返回新建用户的密码）
 *
 * @param user 用户
 */
export async function saveUser(user: User) {
    return request.post('/api/op-admin/user/save', user);
}

/**
 * 修改用户密码
 *
 * @param user 用户
 */
export async function changePassword(user: User) {
    return request.post('/api/op-admin/user/changePassword', user);
}

/**
 * 删除用户
 *
 * @param id 用户 id
 */
export async function deleteUser(id: number) {
    return request.post(`/api/op-admin/user/delete?id=${id}`);
}

/**
 * 批量删除用户
 *
 * @param ids 用户 ids
 */
export async function deleteUsers(ids: number[]) {
    return request.post(`/api/op-admin/user/batchDelete`, ids);
}

/**
 * 获取用户详情
 *
 * @param id 用户 id
 */
export async function getUser(id: number): Promise<User> {
    return request.get(`/api/op-admin/user/get?id=${id}`);
}

/**
 * 分页查询用户
 *
 * @param page 当前页索引
 * @param size 页大小
 * @param params 查询参数
 */
export async function queryUserPage(page: number, size: number, params: object): Promise<PageResult<User>> {
    return request.post(`/api/op-admin/user/queryPage?page=${page}&size=${size}`, params);
}

/**
 * 启用/禁用用户
 *
 * @param ids 用户 ids
 * @param enable 是否启用
 */
export async function changeUsersEnabled(ids: number[], enable: boolean) {
    return request.post(`/api/op-admin/user/changeEnabled`, {ids, enable});
}

/**
 * 分配用户角色
 *
 * @param id 用户 id
 * @param roleIds 角色 ids
 */
export async function assignUserRoles(id: number, roleIds: number[]) {
    return request.post('/api/op-admin/user/assignRoles', {id, roleIds});
}

/**
 * 获取用户角色分配情况
 *
 * @param id 用户 id
 */
export async function loadUserAssignedRoles(id: number): Promise<Role[]> {
    return request.get(`/api/op-admin/user/loadAssignedRoles?id=${id}`);
}

/**
 * 分配用户资源动作
 *
 * @param id 用户 id
 * @param resourceActionIds 资源动作 ids
 */
export async function assignUserResourceActions(id: number, resourceActionIds: number[]) {
    return request.post('/api/op-admin/user/assignResourceActions', {id, resourceActionIds});
}

/**
 * 获取用户资源分配情况
 *
 * @param id 用户 id
 */
export async function loadUserAssignedResources(id: number): Promise<ResourceCategory[]> {
    return request.get(`/api/op-admin/user/loadAssignedResources?id=${id}`);
}
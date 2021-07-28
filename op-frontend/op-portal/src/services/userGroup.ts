import request from "../utils/request";
import {PageResult} from "../models/PageResult";
import {UserGroup} from "../models/UserGroup";
import {Role} from "../models/Role";
import {ResourceCategory} from "../models/ResourceCategory";

/**
 * 保存用户组
 *
 * @param userGroup 用户组
 */
export async function saveUserGroup(userGroup: UserGroup) {
    return request.post('/api/op-admin/userGroup/save', userGroup);
}

/**
 * 删除用户组
 *
 * @param id 用户组 id
 */
export async function deleteUserGroup(id: number) {
    return request.post(`/api/op-admin/userGroup/delete?id=${id}`);
}

/**
 * 批量删除用户组
 *
 * @param ids 用户组 ids
 */
export async function deleteUserGroups(ids: number[]) {
    return request.post(`/api/op-admin/userGroup/batchDelete`, ids);
}

/**
 * 获取用户组详情
 *
 * @param id 用户组 id
 */
export async function getUserGroup(id: number): Promise<UserGroup> {
    return request.get(`/api/op-admin/userGroup/get?id=${id}`);
}

/**
 * 分页查询用户组
 *
 * @param page 当前页索引
 * @param size 页大小
 * @param params 查询参数
 */
export async function queryUserGroupPage(page: number, size: number, params: object): Promise<PageResult<UserGroup>> {
    return request.post(`/api/op-admin/userGroup/queryPage?page=${page}&size=${size}`, params);
}

/**
 * 分配用户组角色
 *
 * @param id 用户组 id
 * @param roleIds 角色 ids
 */
export async function assignUserGroupRoles(id: number, roleIds: number[]) {
    return request.post('/api/op-admin/userGroup/assignRoles', {id, roleIds});
}

/**
 * 获取用户组角色分配情况
 *
 * @param id 用户组 id
 */
export async function loadUserGroupAssignedRoles(id: number): Promise<Role[]> {
    return request.get(`/api/op-admin/userGroup/loadAssignedRoles?id=${id}`);
}

/**
 * 分配用户组资源动作
 *
 * @param id 用户组 id
 * @param resourceActionIds 资源动作 ids
 */
export async function assignUserGroupResourceActions(id: number, resourceActionIds: number[]) {
    return request.post('/api/op-admin/userGroup/assignResourceActions', {id, resourceActionIds});
}

/**
 * 获取用户组资源分配情况
 *
 * @param id 用户组 id
 */
export async function loadUserGroupAssignedResources(id: number): Promise<ResourceCategory[]> {
    return request.get(`/api/op-admin/userGroup/loadAssignedResources?id=${id}`);
}
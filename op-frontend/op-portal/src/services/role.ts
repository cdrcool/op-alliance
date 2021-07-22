import {Role} from "../models/Role";
import {PageResult} from "../models/PageResult";
import request from "../utils/request";
import {ResourceCategory} from "../models/ResourceCategory";

/**
 * 保存角色
 *
 * @param role 角色
 */
export async function saveRole(role: Role) {
    return request.post('/api/role/save', role);
}

/**
 * 删除角色
 *
 * @param id 角色 id
 */
export async function deleteRole(id: number) {
    return request.post(`/api/role/delete?id=${id}`);
}

/**
 * 批量删除角色
 *
 * @param ids 角色 ids
 */
export async function deleteRoles(ids: number[]) {
    return request.post(`/api/role/batchDelete`, ids);
}

/**
 * 获取角色详情
 *
 * @param id 角色 id
 */
export async function getRole(id: number): Promise<Role> {
    return request.get(`/api/role/get?id=${id}`);
}

/**
 * 分页查询角色
 *
 * @param page 当前页索引
 * @param size 页大小
 * @param params 查询参数
 */
export async function queryRolePage(page: number, size: number, params: object): Promise<PageResult<Role>> {
    return request.post(`/api/role/queryPage?page=${page}&size=${size}`, params);
}

/**
 * 启用/禁用角色
 *
 * @param ids 角色 ids
 * @param enable 是否启用
 */
export async function changeRolesEnabled(ids: number[], enable: boolean) {
    return request.post('/api/role/changeEnabled', {ids, enable});
}

/**
 * 分配资源动作
 *
 * @param id 角色 id
 * @param resourceActionIds 资源动作 ids
 */
export async function assignRoleResourceActions(id: number, resourceActionIds: number[]) {
    return request.post('/api/role/assignResourceActions', {id, resourceActionIds});
}

/**
 * 查找资源分配情况
 *
 * @param id 角色 id
 */
export async function loadRoleAssignedResources(id: number):  Promise<ResourceCategory[]> {
    return request.get(`/api/role/loadAssignedResources?id=${id}`);
}
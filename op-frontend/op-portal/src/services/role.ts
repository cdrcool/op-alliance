import request from "../utils/request";
import {Role} from "../models/Role";
import {Page} from "../models/Page";

/**
 * 保存角色
 *
 * @param role 角色
 */
export async function saveRole(role: Role) {
    return request.post('/api/role/save', {
        ...role
    });
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
 * 获取角色详情
 *
 * @param id 角色 id
 */
export async function getRole(id: number) {
    return request.get('/api/role/get', {
        params: {id}
    });
}

/**
 * 分页查询角色
 *
 * @param searchText 搜索文本
 */
export async function queryRolePage(searchText?: string): Promise<Page<Role>> {
    return request.post('/api/role/page', {
        searchText
    });
}

/**
 * 启用/禁用角色
 *
 * @param id 角色 id
 * @param enable 是否启用
 */
export async function changeRoleEnabled(id: number, enable: boolean) {
    return request.post(`/api/role/changeEnabled?id=${id}&enable=${enable}`);
}
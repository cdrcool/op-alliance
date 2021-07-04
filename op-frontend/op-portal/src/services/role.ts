import request from "../utils/instance";
import {Role} from "../models/Role";
import {Page} from "../models/Page";

/**
 * 保存角色
 *
 * @param role 角色
 */
export async function saveRole(role: Role) {
    return request({
        url: '/api/role/save',
        method: 'POST',
        data: {...role},
    });
}

/**
 * 删除角色
 *
 * @param id 角色 id
 */
export async function deleteRole(id: number) {
    return request({
        url: `/api/role/delete?id=${id}`,
        method: 'POST',
    });
}

/**
 * 获取角色详情
 *
 * @param id 角色 id
 */
export async function getRole(id: number) {
    return request({
        url: `/api/role/get?id=${id}`,
        method: 'GET',
    });
}

/**
 * 分页查询角色
 *
 * @param pageNumber 当前页索引
 * @param pageSize 页大小
 * @param searchText 搜索文本
 */
export async function queryRolePage(pageNumber: number, pageSize: number, searchText?: string): Promise<Page<Role>> {
    return request({
        url: `/api/role/page?page=${pageNumber}&size=${pageSize}`,
        method: 'POST',
        data: {searchText},
    });
}

/**
 * 启用/禁用角色
 *
 * @param id 角色 id
 * @param enable 是否启用
 */
export async function changeRoleEnabled(id: number, enable: boolean) {
    return request({
        url: `/api/role/changeEnabled?id=${id}&enable=${enable}`,
        method: 'GET',
    });
}
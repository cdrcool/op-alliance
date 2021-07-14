import {User} from "../models/User";
import {PageResult} from "../models/PageResult";
import request from "../utils/request";

/**
 * 创建用户（返回新建用户的密码）
 *
 * @param user 用户
 */
export async function createUser(user: User) {
    return request.post('/api/user/save', user);
}

/**
 * 修改用户密码
 *
 * @param user 用户
 */
export async function changePassword(user: User) {
    return request.post('/api/user/changePassword', user);
}

/**
 * 保存用户
 *
 * @param user 用户
 */
export async function saveUser(user: User) {
    return request.post('/api/user/save', user);
}

/**
 * 删除用户
 *
 * @param id 用户 id
 */
export async function deleteUser(id: number) {
    return request.post(`/api/user/delete?id=${id}`);
}

/**
 * 批量删除用户
 *
 * @param ids 用户 ids
 */
export async function deleteUsers(ids: number[]) {
    return request.post(`/api/user/batchDelete`, ids);
}

/**
 * 获取用户详情
 *
 * @param id 用户 id
 */
export async function getUser(id: number) {
    return request.get(`/api/user/get?id=${id}`);
}

/**
 * 分页查询用户
 *
 * @param page 当前页索引
 * @param size 页大小
 * @param params 查询参数
 */
export async function queryUserPage(page: number, size: number, params: object): Promise<PageResult<User>> {
    return request.post(`/api/user/queryPage?page=${page}&size=${size}`, params);
}

/**
 * 启用/禁用用户
 *
 * @param ids 用户 ids
 * @param enable 是否启用
 */
export async function changeUsersEnabled(ids: number[], enable: boolean) {
    return request.post(`/api/user/changeEnabled`, {ids, enable});
}
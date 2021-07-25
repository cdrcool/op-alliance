import {Resource} from "../models/Resource";
import {PageResult} from "../models/PageResult";
import request from "../utils/request";

/**
 * 保存资源
 *
 * @param resource 资源
 */
export async function saveResource(resource: Resource) {
    return request.post('/api/op-admin/resource/save', resource);
}

/**
 * 删除资源
 *
 * @param id 资源 id
 */
export async function deleteResource(id: number) {
    return request.post(`/api/op-admin/resource/delete?id=${id}`);
}

/**
 * 批量删除资源
 *
 * @param ids 资源 ids
 */
export async function deleteResources(ids: number[]) {
    return request.post(`/api/op-admin/resource/batchDelete`, ids);
}

/**
 * 获取资源详情
 *
 * @param id 资源 id
 */
export async function getResource(id: number): Promise<Resource> {
    return request.get(`/api/op-admin/resource/get?id=${id}`);
}

/**
 * 分页查询资源
 *
 * @param page 当前页索引
 * @param size 页大小
 * @param params 查询参数
 */
export async function queryResourcePage(page: number, size: number, params: object): Promise<PageResult<Resource>> {
    return request.post(`/api/op-admin/resource/queryPage?page=${page}&size=${size}`, params);
}
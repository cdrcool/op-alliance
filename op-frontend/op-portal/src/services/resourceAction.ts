import {ResourceAction} from "../models/ResourceAction";
import {PageResult} from "../models/PageResult";
import request from "../utils/request";

/**
 * 保存资源动作
 *
 * @param resourceAction 资源动作
 */
export async function saveResourceAction(resourceAction: ResourceAction) {
    return request.post('/api/resourceAction/save', resourceAction);
}

/**
 * 删除资源动作
 *
 * @param id 资源动作 id
 */
export async function deleteResourceAction(id: number) {
    return request.post(`/api/resourceAction/delete?id=${id}`);
}

/**
 * 批量删除资源动作
 *
 * @param ids 资源动作 ids
 */
export async function deleteResourceActions(ids: number[]) {
    return request.post(`/api/resourceAction/batchDelete`, ids);
}

/**
 * 获取资源动作详情
 *
 * @param id 资源动作 id
 */
export async function getResourceAction(id: number): Promise<ResourceAction> {
    return request.get(`/api/resourceAction/get?id=${id}`);
}

/**
 * 分页查询资源动作
 *
 * @param page 当前页索引
 * @param size 页大小
 * @param params 查询参数
 */
export async function queryResourceActionPage(page: number, size: number, params: object): Promise<PageResult<ResourceAction>> {
    return request.post(`/api/resourceAction/page?page=${page}&size=${size}`, params);
}
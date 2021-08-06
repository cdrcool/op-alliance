import request from "../utils/request";
import {WhiteResource} from "../models/WhiteResource";
import {PageResult} from "../models/PageResult";

/**
 * 保存白名单资源
 *
 * @param whiteResource 白名单资源
 */
export async function saveWhiteResource(whiteResource: WhiteResource) {
    return request.post('/api/op-admin/whiteResource/save', whiteResource);
}

/**
 * 删除白名单资源
 *
 * @param id 白名单资源 id
 */
export async function deleteWhiteResource(id: number) {
    return request.post(`/api/op-admin/whiteResource/delete?id=${id}`);
}

/**
 * 批量删除白名单资源
 *
 * @param ids 白名单资源 ids
 */
export async function deleteWhiteResources(ids: number[]) {
    return request.post(`/api/op-admin/whiteResource/batchDelete`, ids);
}

/**
 * 获取白名单资源详情
 *
 * @param id 白名单资源 id
 */
export async function getWhiteResource(id: number): Promise<WhiteResource> {
    return request.get(`/api/op-admin/whiteResource/get?id=${id}`);
}

/**
 * 分页查询白名单资源列表
 *
 * @param page 当前页索引
 * @param size 页大小
 * @param params 查询参数
 */
export async function queryWhiteResourcePage(page: number, size: number, params: object): Promise<PageResult<WhiteResource>> {
    return request.post('/api/op-admin/whiteResource/queryPage', params);
}

/**
 * 启用/禁用白名单资源
 *
 * @param ids 白名单资源 ids
 * @param enable 是否启用
 */
export async function changeWhiteResourcesEnabled(ids: number[], enable: boolean) {
    return request.post('/api/op-admin/whiteResource/changeEnabled', {ids, enable});
}
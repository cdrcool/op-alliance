import {ResourceCategory} from "../models/ResourceCategory";
import {PageResult} from "../models/PageResult";
import request from "../utils/request";

/**
 * 保存资源分类
 *
 * @param resourceCategory 资源分类
 */
export async function saveResourceCategory(resourceCategory: ResourceCategory) {
    return request.post('/api/resourceCategory/save', resourceCategory);
}

/**
 * 删除资源分类
 *
 * @param id 资源分类 id
 */
export async function deleteResourceCategory(id: number) {
    return request.post(`/api/resourceCategory/delete?id=${id}`);
}

/**
 * 批量删除资源分类
 *
 * @param ids 资源分类 ids
 */
export async function deleteResourceCategories(ids: number[]) {
    return request.post(`/api/resourceCategory/batchDelete`, ids);
}

/**
 * 获取资源分类详情
 *
 * @param id 资源分类 id
 */
export async function getResourceCategory(id: number): Promise<ResourceCategory> {
    return request.get(`/api/resourceCategory/get?id=${id}`);
}

/**
 * 分页查询资源分类
 *
 * @param page 当前页索引
 * @param size 页大小
 * @param params 查询参数
 */
export async function queryResourceCategoryPage(page: number, size: number, params: object): Promise<PageResult<ResourceCategory>> {
    return request.post(`/api/resourceCategory/page?page=${page}&size=${size}`, params);
}
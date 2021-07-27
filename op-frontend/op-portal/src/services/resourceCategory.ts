import request from "../utils/request";
import {PageResult} from "../models/PageResult";
import {SelectOptions} from "../models/SelectOptions";
import {ResourceCategory} from "../models/ResourceCategory";

/**
 * 保存资源分类
 *
 * @param resourceCategory 资源分类
 */
export async function saveResourceCategory(resourceCategory: ResourceCategory) {
    return request.post('/api/op-admin/resourceCategory/save', resourceCategory);
}

/**
 * 删除资源分类
 *
 * @param id 资源分类 id
 */
export async function deleteResourceCategory(id: number) {
    return request.post(`/api/op-admin/resourceCategory/delete?id=${id}`);
}

/**
 * 批量删除资源分类
 *
 * @param ids 资源分类 ids
 */
export async function deleteResourceCategories(ids: number[]) {
    return request.post(`/api/op-admin/resourceCategory/batchDelete`, ids);
}

/**
 * 获取资源分类详情
 *
 * @param id 资源分类 id
 */
export async function getResourceCategory(id: number): Promise<ResourceCategory> {
    return request.get(`/api/op-admin/resourceCategory/get?id=${id}`);
}

/**
 * 分页查询资源分类
 *
 * @param page 当前页索引
 * @param size 页大小
 * @param params 查询参数
 */
export async function queryResourceCategoryPage(page: number, size: number, params: object): Promise<PageResult<ResourceCategory>> {
    return request.post(`/api/op-admin/resourceCategory/queryPage?page=${page}&size=${size}`, params);
}

/**
 * 查询资源分类下拉框列表
 *
 * @param params 查询参数
 */
export async function queryResourceCategorySelectList(params: object): Promise<SelectOptions[]> {
    return request.post(`/api/op-admin/resourceCategory/selectList`, params);
}
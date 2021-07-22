import {Organization} from "../models/Organization";
import request from "../utils/request";
import {ResourceCategory} from "../models/ResourceCategory";

/**
 * 保存组织
 *
 * @param org 组织
 */
export async function saveOrganization(org: Organization) {
    return request.post('/api/organization/save', org);
}

/**
 * 删除组织
 *
 * @param id 组织 id
 */
export async function deleteOrganization(id: number) {
    return request.post(`/api/organization/delete?id=${id}`);
}

/**
 * 批量删除组织
 *
 * @param ids 组织 ids
 */
export async function deleteOrganizations(ids: number[]) {
    return request.post(`/api/organization/batchDelete`, ids);
}

/**
 * 获取组织详情
 *
 * @param id 组织 id
 */
export async function getOrganization(id: number): Promise<Organization> {
    return request.get(`/api/organization/get?id=${id}`);
}

/**
 * 查询组织树
 *
 * @param params 查询参数
 */
export async function queryOrganizationTree(params: object): Promise<Organization> {
    return request.post(`/api/organization/queryTree`, params);
}

/**
 * 分页查询组织
 *
 * @param params 查询参数
 */
export async function queryOrganizationList(params: object): Promise<Organization[]> {
    return request.post(`/api/organization/queryList`, params);
}

/**
 * 分配资源动作
 *
 * @param id 用户 id
 * @param resourceActionIds 资源动作 ids
 */
export async function assignOrganizationResourceActions(id: number, resourceActionIds: number[]) {
    return request.post('/api/organization/assignResourceActions', {id, resourceActionIds});
}

/**
 * 查找资源分配情况
 *
 * @param id 用户 id
 */
export async function loadOrganizationAssignedResources(id: number):  Promise<ResourceCategory[]> {
    return request.get(`/api/organization/loadAssignedResources?id=${id}`);
}
import request from "../utils/request";
import {Organization} from "../models/Organization";
import {Role} from "../models/Role";
import {ResourceCategory} from "../models/ResourceCategory";
import {TreeNode} from "../models/TreeNode";

/**
 * 保存组织
 *
 * @param org 组织
 */
export async function saveOrganization(org: Organization) {
    return request.post('/api/op-admin/organization/save', org);
}

/**
 * 删除组织
 *
 * @param id 组织 id
 */
export async function deleteOrganization(id: number) {
    return request.post(`/api/op-admin/organization/delete?id=${id}`);
}

/**
 * 批量删除组织
 *
 * @param ids 组织 ids
 */
export async function deleteOrganizations(ids: number[]) {
    return request.post(`/api/op-admin/organization/batchDelete`, ids);
}

/**
 * 获取组织详情
 *
 * @param id 组织 id
 */
export async function getOrganization(id: number): Promise<Organization> {
    return request.get(`/api/op-admin/organization/get?id=${id}`);
}

/**
 * 查询组织树
 *
 * @param params 查询参数
 */
export async function queryOrganizationTree(params: object): Promise<Organization> {
    return request.post(`/api/op-admin/organization/queryTree`, params);
}

/**
 * 分配组织角色
 *
 * @param id 组织 id
 * @param roleIds 角色 ids
 */
export async function assignOrganizationRoles(id: number, roleIds: number[]) {
    return request.post('/api/op-admin/organization/assignRoles', {id, roleIds});
}

/**
 * 获取组织角色分配情况
 *
 * @param id 组织 id
 */
export async function loadOrganizationAssignedRoles(id: number): Promise<Role[]> {
    return request.get(`/api/op-admin/organization/loadAssignedRoles?id=${id}`);
}

/**
 * 分配组织资源动作
 *
 * @param id 组织 id
 * @param resourceActionIds 资源动作 ids
 */
export async function assignOrganizationResourceActions(id: number, resourceActionIds: number[]) {
    return request.post('/api/op-admin/organization/assignResourceActions', {id, resourceActionIds});
}

/**
 * 获取组织资源分配情况
 *
 * @param id 组织 id
 */
export async function loadOrganizationAssignedResources(id: number): Promise<ResourceCategory[]> {
    return request.get(`/api/op-admin/organization/loadAssignedResources?id=${id}`);
}

/**
 * 查询菜单树选择列表
 *
 * @param params 查询参数
 */
export async function queryOrganizationTreeSelectList(params?: object): Promise<TreeNode[]> {
    return request.post('/api/op-admin/organization/queryTreeSelectList', params);
}
import request from "../utils/request";
import {OauthClient} from "../models/OauthClient";
import {TreeNode} from "../models/TreeNode";
import {PageResult} from "../models/PageResult";

/**
 * 保存 Oauth 客户端
 *
 * @param oauthClient Oauth 客户端
 */
export async function saveOauthClient(oauthClient: OauthClient) {
    return request.post('/api/op-admin/oauthClientDetails/save', oauthClient);
}

/**
 * 删除 Oauth 客户端
 *
 * @param id Oauth 客户端 id
 */
export async function deleteOauthClient(id: number) {
    return request.post(`/api/op-admin/oauthClientDetails/delete?id=${id}`);
}

/**
 * 批量删除 Oauth 客户端
 *
 * @param ids Oauth 客户端 ids
 */
export async function deleteOauthClients(ids: number[]) {
    return request.post(`/api/op-admin/oauthClientDetails/batchDelete`, ids);
}

/**
 * 获取 Oauth 客户端详情
 *
 * @param id Oauth 客户端 id
 */
export async function getOauthClient(id: number): Promise<OauthClient> {
    return request.get(`/api/op-admin/oauthClientDetails/get?id=${id}`);
}

/**
 * 分页查询 Oauth 客户端列表
 *
 * @param page 当前页索引
 * @param size 页大小
 * @param params 查询参数
 */
export async function queryOauthClientPage(page: number, size: number, params: object): Promise<PageResult<OauthClient>> {
    return request.post('/api/op-admin/oauthClientDetails/queryPage', params);
}
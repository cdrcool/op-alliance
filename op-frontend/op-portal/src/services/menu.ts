import request from "../utils/request";
import {Menus} from "../models/Menus";
import {TreeNode} from "../models/TreeNode";

/**
 * 保存菜单
 *
 * @param menu 菜单
 */
export async function saveMenu(menu: Menus) {
    return request.post('/api/op-admin/menu/save', menu);
}

/**
 * 删除菜单
 *
 * @param id 菜单 id
 */
export async function deleteMenu(id: number) {
    return request.post(`/api/op-admin/menu/delete?id=${id}`);
}

/**
 * 批量删除菜单
 *
 * @param ids 菜单 ids
 */
export async function deleteMenus(ids: number[]) {
    return request.post(`/api/op-admin/menu/batchDelete`, ids);
}

/**
 * 获取菜单详情
 *
 * @param id 菜单 id
 */
export async function getMenu(id: number): Promise<Menus> {
    return request.get(`/api/op-admin/menu/get?id=${id}`);
}

/**
 * 查询菜单树列表
 *
 * @param params 查询参数
 */
export async function queryMenuTreeList(params?: object): Promise<Menus[]> {
    return request.post('/api/op-admin/menu/queryTreeList', params);
}

/**
 * 查询菜单列表
 *
 * @param keyword 关键字
 */
export async function queryMenuList(keyword: string): Promise<Menus[]> {
    return request.get(`/api/op-admin/menu/queryList?keyword=${keyword}`);
}

/**
 * 显示/隐藏菜单
 *
 * @param ids 菜单 ids
 * @param show 是否显示
 */
export async function changeMenusVisibility(ids: number[], show: boolean) {
    return request.post(`/api/op-admin/menu/changeVisibility`, {ids, show});
}

/**
 * 查询菜单异步树
 *
 * @param params 查询参数
 */
export async function queryForMenuAsyncTreeFlat(params?: object): Promise<TreeNode[]> {
    return request.post('/api/op-admin/menu/queryForAsyncTreeFlat', params);
}
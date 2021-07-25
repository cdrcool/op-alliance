import {Menus} from "../models/Menus";
import request from "../utils/request";

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
 * 分页查询菜单
 *
 * @param params 查询参数
 */
export async function queryMenuTreeList(params?: object): Promise<Menus[]> {
    return request.post('/api/op-admin/menu/queryTreeList', params);
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
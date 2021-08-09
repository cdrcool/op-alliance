import request from "../utils/request";

/**
 * 初始化资源路径权限关联
 *
 */
export async function initResourcePathPermissions() {
    return request.post('/api/op-admin/resourceAction/initResourcePathPermissions');
}
import request from "../utils/request";

export type LoginParams = {
    clientId: string;
    username: string;
    password: string;
    phone: string;
    captcha: string;
};

/**
 * 用户登录
 *
 * @param loginParams 登录参数
 */
export async function login(loginParams: LoginParams) {
    return request.post('/api/op-auth/auth/token', loginParams);
}

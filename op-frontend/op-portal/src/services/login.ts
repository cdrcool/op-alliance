import request from "../utils/request";
import {OauthToken} from "../models/OauthToken";

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
export async function login(loginParams: LoginParams): Promise<OauthToken> {
    return request.post('/api/op-auth/auth/token', loginParams);
}

/**
 * 用户登录
 */
export async function getCurrentUser() {
    return request.get('/api/oauth/userInfo');
}

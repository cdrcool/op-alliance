import request from "../utils/request";
import {OauthToken} from "../models/OauthToken";
import {UserInfo} from "../models/UserInfo";

/**
 * 登录参数
 */
export type LoginParams = {
    clientId: string;
    username: string;
    password: string;
    phone: string;
    captcha: string;
};

/**
 * 获取访问令牌
 *
 * @param loginParams 登录参数
 * @return oauth token
 */
export async function getAccessToken(loginParams: LoginParams): Promise<OauthToken> {
    return request.post('/api/op-auth/auth/getToken', loginParams);
}

/**
 * 刷新访问令牌
 *
 * @return oauth token
 */
export async function refreshAccessToken(): Promise<OauthToken> {
    const refreshToken = localStorage.getItem('refreshToken');
    return request.post(`/api/op-auth/auth/refreshToken?refreshToken=${refreshToken}`);
}

/**
 * 获取用当前户信息
 */
export async function getUserInfo(): Promise<UserInfo> {
    return request.get('/api/oauth/userInfo');
}

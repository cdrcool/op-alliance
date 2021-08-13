import axios from 'axios';

import {message, notification} from 'antd';
import {refreshAccessToken} from "../services/token";

const request = axios.create({
    headers: {}
});

request.interceptors.request.use(
    config => {
        // 请求前，携带 AccessToken
        const accessToken = localStorage.getItem('accessToken')
        if (accessToken) {
            config.headers['Authorization'] = `Bearer ${accessToken}`;
        }
        return config
    },
    err => {
        return Promise.reject(err);
    });

// 防止多次调用刷新 token 接口
let isRefreshing = false;
// 待重试请求队列，避免同时有两个或以上请求等待刷新 token
let requests: (() => void)[] = [];

request.interceptors.response.use(
    response => {
        const {code, message: msg, data} = response.data;
        if (code !== 200) {
            message.error(msg, 1);
            return Promise.reject(code);
        }

        return Promise.resolve(data);
    },
    error => {
        const {config, status} = error.response;

        if (status == 401) {
            if (!isRefreshing) {
                isRefreshing = true;
                return refreshAccessToken().then(res => {
                    const {accessToken, tokenType, refreshToken, expiresIn} = res;
                    localStorage.setItem('accessToken', accessToken);
                    localStorage.setItem('tokenType', tokenType);
                    localStorage.setItem('refreshToken', refreshToken);
                    localStorage.setItem('expiresIn', expiresIn + '');

                    requests.forEach(req => req());
                    requests = [];

                    return request(config);
                }).catch(res => {
                    console.error('刷新 token 异常：', res)
                    window.location.href = '/'
                }).finally(() => {
                    isRefreshing = false
                });
            } else {
                return new Promise((resolve) => {
                    requests.push(() => {
                        resolve(request(config))
                    })
                })
            }
        }

        notification.error({
            message: `请求错误：${status}`,
            description: error.response.statusText,
        });

        return Promise.reject(error.response.status);
    });

export default request;

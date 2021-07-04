import axios, {AxiosRequestConfig} from 'axios';

import {message, notification} from 'antd';

const instance = axios.create({
    headers: {}
});

instance.interceptors.request.use(
    config => {
        const token = sessionStorage.getItem('token')
        if (token) {
            config.headers.authorization = token;
        }
        return config
    },
    err => {
        return Promise.reject(err);
    });

instance.interceptors.response.use(
    response => {
        if (response.data.code !== 200) {
            message.error(response.data.message, 1);
        }
        return response.data.data;
    },
    error => {
        return Promise.reject(error.response.status);
    });

export default function request(opt: AxiosRequestConfig) {
    return axios(opt)
        .then((response) => {
            const {code, message:msg, data} = response.data;
            if (code !== 200) {
                message.error(msg, 1);
            }

            return {...data};
        })
        .catch((error) => {
            console.log('error', error)
            const status = error.response.status;

            notification.error({
                message: `请求错误：${status}`,
                description: error.response.statusText,
            });

            return null;
        });
}
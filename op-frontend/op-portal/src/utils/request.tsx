import axios from 'axios';

import {message, notification} from 'antd';

const request = axios.create({
    headers: {}
});

request.interceptors.request.use(
    config => {
        const token = sessionStorage.getItem('token')
        if (token) {
            config.headers.authorization = `Bearer ${token}`;
        }
        return config
    },
    err => {
        return Promise.reject(err);
    });

request.interceptors.response.use(
    response => {
        const {code, message: msg, data} = response.data;
        if (code !== 200) {
            message.error(msg, 1);
        }

        return {...data};
    },
    error => {
        const status = error.response.status;

        notification.error({
            message: `请求错误：${status}`,
            description: error.response.statusText,
        });

        return Promise.reject(error.response.status);
    });

export default request;
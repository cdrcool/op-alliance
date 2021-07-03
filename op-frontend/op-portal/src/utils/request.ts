import axios from 'axios';

const request = axios.create({
    headers: {}
});

request.interceptors.request.use(
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

request.interceptors.response.use(
    response => {
        if (response.data.code) {
            switch (response.data.code) {
            }
        }
        return response.data.data;
    },
    error => {
        return Promise.reject(error.response.status);
    });

export default request;
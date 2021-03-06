import React, {FC, useContext, useState} from 'react';
import {useHistory} from "react-router-dom";
import {Helmet} from 'react-helmet';
import {message, Space, Tabs} from 'antd';
import {
    GithubOutlined,
    LockOutlined,
    MailOutlined,
    MobileOutlined,
    QqOutlined,
    UserOutlined,
    WechatOutlined,
} from '@ant-design/icons';
import {DefaultFooter} from '@ant-design/pro-layout';
import ProForm, {ProFormCaptcha, ProFormCheckbox, ProFormText} from '@ant-design/pro-form';
import {getUserInfo, getAccessToken, LoginParams} from "../../services/token";
import logo from "../../assets/logo.svg";

import "./index.css";
import userContext from "../../context/userContext";

const {TabPane} = Tabs;

const LoginPage: FC = () => {
    const {dispatch} = useContext(userContext);

    const history = useHistory();

    const [type, setType] = useState<string>('account');
    const [submitting, setSubmitting] = useState<boolean>(false);

    const onSubmit = async (values: LoginParams) => {
        setSubmitting(true);
        try {
            const oathToken = await getAccessToken(values);
            const {accessToken, tokenType, refreshToken, expiresIn} = oathToken;
            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('tokenType', tokenType);
            localStorage.setItem('refreshToken', refreshToken);
            localStorage.setItem('expiresIn', expiresIn + '');
            setSubmitting(false);

            const userInfo = await getUserInfo();
            if (!userInfo) {
                history.push('/login');
            }
            dispatch({type: 'loginSuccess', payload: userInfo});

            history.push('/');
        } catch (error) {
            setSubmitting(false);
        }
    };

    return (
        <div className="container">
            <Helmet title="登录"/>
            <div className="content">
                <div className="top">
                    <div className="header">
                        <img alt="logo" className="logo" src={logo}/>
                        <span className="title">One Piece</span>
                    </div>
                    <div className="desc">
                        集成各主流开源框架，构建微服务生产级标准实现
                    </div>
                </div>
                <div className="main">
                    <ProForm<LoginParams>
                        initialValues={{
                            autoLogin: true,
                        }}
                        submitter={{
                            searchConfig: {
                                submitText: '登录',
                            },
                            render: (_, dom) => dom.pop(),
                            submitButtonProps: {
                                loading: submitting,
                                size: 'large',
                                style: {
                                    width: '100%',
                                },
                            },
                        }}
                        onFinish={(values) => {
                            onSubmit(values).then(() => {
                            });
                            return Promise.resolve();
                        }}
                    >
                        <Tabs centered activeKey={type} onChange={setType}>
                            <TabPane
                                key="account"
                                tab="账号登录"
                            />
                            <TabPane
                                key="mobile"
                                tab="验证码登录"
                            />
                        </Tabs>
                        {type === 'account' && (
                            <>
                                <ProFormText
                                    name="username"
                                    fieldProps={{
                                        size: 'large',
                                        prefix: <UserOutlined className="prefixIcon"/>,
                                    }}
                                    placeholder="用户名：账号、邮箱或手机号"
                                    rules={[
                                        {
                                            required: true,
                                            message: "请输入用户名",
                                        },
                                    ]}
                                />
                                <ProFormText.Password
                                    name="password"
                                    fieldProps={{
                                        size: 'large',
                                        prefix: <LockOutlined className="prefixIcon"/>,
                                    }}
                                    placeholder="密码"
                                    rules={[
                                        {
                                            required: true,
                                            message: "请输入密码！",
                                        },
                                    ]}
                                />
                            </>
                        )}
                        {type === 'mobile' && (
                            <>
                                <ProFormText
                                    fieldProps={{
                                        size: 'large',
                                        prefix: <MobileOutlined className="prefixIcon"/>,
                                    }}
                                    name="phone"
                                    placeholder="手机号"
                                    rules={[
                                        {
                                            required: true,
                                            message: "请输入手机号！",
                                        },
                                        {
                                            pattern: /^1\d{10}$/,
                                            message: "不合法的手机号！",
                                        },
                                    ]}
                                />
                                <ProFormCaptcha
                                    fieldProps={{
                                        size: 'large',
                                        prefix: <MailOutlined className="prefixIcon"/>,
                                    }}
                                    captchaProps={{
                                        size: 'large',
                                    }}
                                    placeholder="验证码"
                                    captchaTextRender={(timing, count) => {
                                        if (timing) {
                                            return `${count} 获取验证码`;
                                        }
                                        return "获取验证码";
                                    }}
                                    name="captcha"
                                    rules={[
                                        {
                                            required: true,
                                            message: "请输入验证码！",
                                        },
                                    ]}
                                    onGetCaptcha={async (mobile) => {
                                        // const result = await getFakeCaptcha(mobile);
                                        const result = true;
                                        // if (result === false) {
                                        //     return;
                                        // }
                                        message.success(
                                            '获取验证码成功！验证码是 1234',
                                        );
                                    }}
                                />
                            </>
                        )}
                        <div
                            style={{
                                marginBottom: 24,
                            }}
                        >
                            <ProFormCheckbox noStyle name="autoLogin">
                                自动登录
                            </ProFormCheckbox>
                            <a
                                style={{
                                    float: 'right',
                                }}
                            >
                                忘记密码 ？
                            </a>
                        </div>
                    </ProForm>
                    <Space className="other">
                        其他登录方式：
                        <WechatOutlined className="icon"/>
                        <QqOutlined className="icon"/>
                        <GithubOutlined className="icon"/>
                    </Space>
                </div>
            </div>
            <DefaultFooter
                copyright="2021 cdrcool 版权所有"
                links={[
                    {
                        key: 'github',
                        title: <GithubOutlined/>,
                        href: 'https://github.com/cdrcool/op-alliance',
                        blankTarget: true,
                    },
                    {
                        key: 'One Piece',
                        title: 'One Piece',
                        href: 'https://ant.design',
                        blankTarget: true,
                    },
                ]}
            />
        </div>
    );
};

export default LoginPage;

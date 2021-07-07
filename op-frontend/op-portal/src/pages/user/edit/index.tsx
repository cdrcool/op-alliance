import {Button, Form, Input, DatePicker, PageHeader, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {User} from "../../../models/User";
import {getUser, updateUser} from "../../../services/user";

const UserEditPage = () => {
    const history = useHistory();
    const {id} = useParams<{ id?: string }>();

    const [loading, setLoading] = useState<boolean>(!!id);
    const [form] = Form.useForm();

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const user = await getUser(parseInt(id));
                form.setFieldsValue(user);
                setLoading(false);
            }

            fetchData().then(() => {
            });
        }
    }, []);


    const onFinish = (user: User) => {
        updateUser(user).then(() => {
            history.push('/management/user');
        });
    };

    const onFinishFailed = (errorInfo: any) => {
        console.log('提交表单失败:', errorInfo);
    };

    return (
        <div className="card">
            <PageHeader
                title={<span className="title">{`${id ? '编辑' : '新增'}用户`}</span>}
                onBack={() => history.push('/management/user')}
            >
                <Spin spinning={loading}>
                    <Form
                        form={form}
                        labelCol={{span: 8}}
                        wrapperCol={{span: 8}}
                        onFinish={onFinish}
                        onFinishFailed={onFinishFailed}
                    >
                        <Form.Item name="id" hidden={true}/>
                        <Form.Item label="用户名" name="username" rules={[{required: true, message: '请输入用户名'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="昵称" name="nickname" rules={[{required: true, message: '请输入昵称'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="手机号" name="phone">
                            <Input/>
                        </Form.Item>
                        <Form.Item label="邮箱" name="email">
                            <Input/>
                        </Form.Item>
                        <Form.Item label="出生日期" name="birthday">
                            <DatePicker/>
                        </Form.Item>
                        <Form.Item wrapperCol={{offset: 8, span: 8}}>
                            <Space>
                                <Button onClick={() => history.push('/management/user')}>
                                    取消
                                </Button>
                                <Button type="primary" htmlType="submit">
                                    保存
                                </Button>
                            </Space>
                        </Form.Item>
                    </Form>
                </Spin>
            </PageHeader>
        </div>
    );
};

export default UserEditPage;
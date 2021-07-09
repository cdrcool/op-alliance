import {Button, DatePicker, Form, Input, InputNumber, PageHeader, Radio, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {User} from "../../../models/User";
import {getUser, updateUser} from "../../../services/user";
import moment from "moment";

const UserEditPage = () => {
    const history = useHistory();
    const {id} = useParams<{ id?: string }>();

    const [loading, setLoading] = useState<boolean>(!!id);
    const [form] = Form.useForm();

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const user = await getUser(parseInt(id));
                // @ts-ignore
                user.birthday = moment(user.birthday, 'YYYY-MM-DD');
                form.setFieldsValue(user);
                setLoading(false);
            }

            fetchData().then(() => {
            });
        }
    }, []);


    const onFinish = (user: User) => {
        // @ts-ignore
        user.birthday = user.birthday.format('YYYY-MM-DD');
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
                        <Form.Item name="orgId" hidden={true}/>
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
                        <Form.Item label="性别" name="gender" initialValue={1}>
                            <Radio.Group>
                                <Radio value={1}>男</Radio>
                                <Radio value={2}>女</Radio>
                            </Radio.Group>
                        </Form.Item>
                        <Form.Item label="出生日期" name="birthday">
                            <DatePicker/>
                        </Form.Item>
                        <Form.Item label="是否启用" name="status" initialValue={1}
                                   rules={[{required: true, message: '请勾选是否启用'}]}>
                            <Radio.Group>
                                <Radio value={1}>是</Radio>
                                <Radio value={0}>否</Radio>
                            </Radio.Group>
                        </Form.Item>
                        <Form.Item label="用户编号" name="userNo">
                            <InputNumber/>
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
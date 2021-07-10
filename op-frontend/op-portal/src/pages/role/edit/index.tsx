import {Button, Card, Form, Input, InputNumber, Radio, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import {getRole, saveRole} from "../../../services/role";
import {Role} from "../../../models/Role";
import React, {useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";

const {TextArea} = Input;

const RoleEditPage = () => {
    const history = useHistory();
    const {id} = useParams<{ id?: string }>();

    const [loading, setLoading] = useState<boolean>(!!id);
    const [form] = Form.useForm();

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const role = await getRole(parseInt(id));
                form.setFieldsValue(role);
                setLoading(false);
            }

            fetchData().then(() => {
            });
        }
    }, []);


    const onFinish = (role: Role) => {
        saveRole(role).then(() => {
            history.push('/admin/role');
        });
    };

    const onFinishFailed = (errorInfo: any) => {
        console.log('保存角色失败:', errorInfo);
    };

    return (
        <PageContainer
            className="page-container"
            title={`${id ? '编辑' : '新增'}角色`}
            header={{
                breadcrumb: {},
            }}
            onBack={() => history.push('/admin/role')}

        >
            <Card>
                <Spin spinning={loading}>
                    <Form
                        form={form}
                        labelCol={{span: 8}}
                        wrapperCol={{span: 8}}
                        onFinish={onFinish}
                        onFinishFailed={onFinishFailed}
                    >
                        <Form.Item name="id" hidden={true}/>
                        <Form.Item label="角色名" name="roleName" rules={[{required: true, message: '请输入角色名'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="角色编码" name="roleCode" rules={[{required: true, message: '请输入角色编码'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="角色描述" name="roleDesc">
                            <TextArea/>
                        </Form.Item>
                        <Form.Item label="是否启用" name="status" initialValue={1}
                                   rules={[{required: true, message: '请勾选是否启用'}]}>
                            <Radio.Group>
                                <Radio value={1}>是</Radio>
                                <Radio value={0}>否</Radio>
                            </Radio.Group>
                        </Form.Item>
                        <Form.Item label="角色编号" name="roleNo">
                            <InputNumber/>
                        </Form.Item>
                        <Form.Item wrapperCol={{offset: 8, span: 8}}>
                            <Space>
                                <Button onClick={() => history.push('/admin/role')}>
                                    取消
                                </Button>
                                <Button type="primary" htmlType="submit">
                                    保存
                                </Button>
                            </Space>
                        </Form.Item>
                    </Form>
                </Spin>
            </Card>
        </PageContainer>
    );
};

export default RoleEditPage;
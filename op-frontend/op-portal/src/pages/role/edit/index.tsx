import {Button, Card, Form, Input, InputNumber, Radio, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import {getRole} from "../../../services/role";
import React, {FC, useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {saveMenu} from "../../../services/menu";

const {TextArea} = Input;

const RoleEditPage: FC = () => {
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

    const onSave = () => {
        form.validateFields().then(values => {
            saveMenu(values).then(() => {
                history.push('/admin/role');
            });
        })
    };

    return (
        <PageContainer
            className="page-container"
            title={`${id ? '编辑' : '新增'}角色`}
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={onSave}>保存</Button>
                </Space>
            }
            onBack={() => history.push('/admin/role')}
        >
            <Spin spinning={loading}>
                <Card>
                    <Form
                        form={form}
                        labelCol={{span: 8}}
                        wrapperCol={{span: 8}}
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
                    </Form>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default RoleEditPage;
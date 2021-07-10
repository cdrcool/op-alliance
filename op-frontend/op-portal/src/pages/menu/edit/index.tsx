import {Button, Card, Form, Input, InputNumber, Radio, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {getMenu, saveMenu} from "../../../services/menu";
import {Menus} from "../../../models/Menus";

const MenuEditPage = () => {
    const history = useHistory();
    const {id} = useParams<{ id?: string }>();
    // @ts-ignore
    const {pid, pName} = history.location.state || {};

    const [loading, setLoading] = useState<boolean>(!!id);
    const [form] = Form.useForm();

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const menu = await getMenu(parseInt(id));
                form.setFieldsValue(menu);
                setLoading(false);
            }

            fetchData().then(() => {
            });
        } else {
            form.setFieldsValue({pid});
        }
    }, []);


    const onFinish = (menu: Menus) => {
        saveMenu(menu).then(() => {
            history.push('/admin/menu');
        });
    };

    const onFinishFailed = (errorInfo: any) => {
        console.log('保存菜单失败:', errorInfo);
    };

    return (
        <PageContainer
            className="page-container"
            title={`${id ? '编辑' : '新增'}菜单`}
            header={{
                breadcrumb: {},
            }}
            onBack={() => history.push('/admin/menu')}

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
                        <Form.Item name="pid" hidden={true}/>
                        <Form.Item label="上级菜单" >
                            {pName}
                        </Form.Item>
                        <Form.Item label="菜单名" name="menuName" rules={[{required: true, message: '请输入菜单名'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="菜单图标" name="menuIcon">
                            <Input/>
                        </Form.Item>
                        <Form.Item label="菜单路径" name="menuPath">
                            <Input/>
                        </Form.Item>
                        <Form.Item label="是否隐藏" name="isHidden" initialValue={false}
                                   rules={[{required: true, message: '请勾选是否隐藏'}]}>
                            <Radio.Group>
                                <Radio value={true}>是</Radio>
                                <Radio value={false}>否</Radio>
                            </Radio.Group>
                        </Form.Item>
                        <Form.Item label="权限标识" name="permission">
                            <Input/>
                        </Form.Item>
                        <Form.Item label="菜单编号" name="menuNo">
                            <InputNumber/>
                        </Form.Item>
                        <Form.Item wrapperCol={{offset: 8, span: 8}}>
                            <Space>
                                <Button onClick={() => history.push('/admin/menu')}>
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

export default MenuEditPage;
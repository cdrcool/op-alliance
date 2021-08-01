import {Button, Card, Form, Input, InputNumber, Radio, Space, Spin, TreeSelect} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {FC, useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {getMenu, queryForMenuAsyncTreeFlat, saveMenu} from "../../../services/menu";
import {TreeNode} from "../../../models/TreeNode";

const MenuEditPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id?: string }>();
    // @ts-ignore
    const {pid} = history.location.state || {};

    const [loading, setLoading] = useState<boolean>(!!id);
    const [menuTreeData, setMenuTreeData] = useState<TreeNode[]>([]);
    const [form] = Form.useForm();

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const menu = await getMenu(parseInt(id));
                form.setFieldsValue(menu);

                setLoading(false);
            }

            fetchData().then((value) => {
            });
        } else {
            form.setFieldsValue({pid});
        }

        const fetchMenuTreeData = async () => {
            const menuTreeData = await queryForMenuAsyncTreeFlat({
                id: id,
            });
            setMenuTreeData(menuTreeData || []);
        };

        fetchMenuTreeData().then(() => {
        });
    }, []);

    const onSave = () => {
        form.validateFields().then(values => {
            saveMenu(values).then(() => {
                history.push('/admin/menu');
            });
        })
    };

    return (
        <PageContainer
            className="page-container"
            title={`${id ? '编辑' : '新增'}菜单`}
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={onSave}>保存</Button>
                </Space>
            }
            onBack={() => history.push('/admin/menu')}
        >
            <Spin spinning={loading}>
                <Card>
                    <Form
                        form={form}
                        labelCol={{span: 8}}
                        wrapperCol={{span: 8}}
                    >
                        <Form.Item name="id" hidden={true}/>
                        <Form.Item label="上级菜单" name="pid" >
                            <TreeSelect
                                allowClear={true}
                                showSearch={true}
                                treeNodeFilterProp="title"
                                treeData={menuTreeData}
                            />
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
                    </Form>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default MenuEditPage;
import {Button, Card, DatePicker, Form, Input, InputNumber, Radio, Space, Spin, TreeSelect} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {FC, useEffect, useState} from "react";
import {getUser, saveUser} from "../../../services/user";
import moment from "moment";
import {PageContainer} from "@ant-design/pro-layout";
import {TreeNode} from "../../../models/TreeNode";
import {queryForOrganizationAsyncTreeFlat} from "../../../services/organization";
import {LegacyDataNode} from "rc-tree-select/lib/interface";

const {TextArea} = Input;

const UserEditPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id?: string }>();
    // @ts-ignore
    const {orgId} = history.location.state || {};

    const [loading, setLoading] = useState<boolean>(!!id);
    const [organizationTreeData, setOrganizationTreeData] = useState<TreeNode[]>([]);
    const [form] = Form.useForm();

    const fetchOrganizationTreeData = async (appendedId: number) => {
        const organizationTreeData = await queryForOrganizationAsyncTreeFlat({
            appendedId,
        });
        setOrganizationTreeData(organizationTreeData || []);
    };

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const user = await getUser(parseInt(id));
                // @ts-ignore
                user.birthday = user.birthday && moment(user.birthday, 'YYYY-MM-DD');
                form.setFieldsValue(user);

                fetchOrganizationTreeData(user.orgId as number).then(() => {
                });

                setLoading(false);
            }

            fetchData().then(() => {
            });
        } else {
            fetchOrganizationTreeData(orgId).then(() => {
                form.setFieldsValue({orgId});
            });
        }
    }, []);

    const onSave = () => {
        form.validateFields().then(values => {
            values.birthday = values.birthday && values.birthday.format('YYYY-MM-DD');
            saveUser(values).then(() => {
                history.push('/admin/user');
            });
        })
    };

    const onLoadData = async (treeNode: LegacyDataNode) => {
        const children = await queryForOrganizationAsyncTreeFlat({
            pid: treeNode.id,
        });
        setOrganizationTreeData([...organizationTreeData, ...children]);
    };

    return (
        <PageContainer
            className="page-container"
            title={`${id ? '编辑' : '新增'}用户`}
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={onSave}>保存</Button>
                </Space>
            }
            onBack={() => history.push('/admin/user')}
        >
            <Spin spinning={loading}>
                <Card>
                    <Form
                        form={form}
                        labelCol={{span: 8}}
                        wrapperCol={{span: 8}}
                    >
                        <Form.Item name="id" hidden={true}/>
                        <Form.Item label="所属组织" name="orgId" rules={[{required: true, message: '请选择所属组织'}]}>
                            <TreeSelect
                                treeNodeFilterProp="title"
                                allowClear={true}
                                showSearch={true}
                                treeDefaultExpandedKeys={organizationTreeData.filter(node => node.isExpand).map(node => node.id)}
                                treeDataSimpleMode
                                treeData={organizationTreeData}
                                //@ts-ignore
                                loadData={onLoadData}
                            />
                        </Form.Item>
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
                        <Form.Item label="个性签名" name="signature">
                            <TextArea/>
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
                    </Form>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default UserEditPage;
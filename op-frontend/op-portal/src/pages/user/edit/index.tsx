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
            appendedIds: [appendedId]
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
            title={`${id ? '??????' : '??????'}??????`}
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={onSave}>??????</Button>
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
                        <Form.Item label="????????????" name="orgId" rules={[{required: true, message: '?????????????????????'}]}>
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
                        <Form.Item label="?????????" name="username" rules={[{required: true, message: '??????????????????'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="??????" name="nickname" rules={[{required: true, message: '???????????????'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="?????????" name="phone">
                            <Input/>
                        </Form.Item>
                        <Form.Item label="??????" name="email">
                            <Input/>
                        </Form.Item>
                        <Form.Item label="??????" name="gender" initialValue={1}>
                            <Radio.Group>
                                <Radio value={1}>???</Radio>
                                <Radio value={2}>???</Radio>
                            </Radio.Group>
                        </Form.Item>
                        <Form.Item label="????????????" name="birthday">
                            <DatePicker/>
                        </Form.Item>
                        <Form.Item label="????????????" name="signature">
                            <TextArea/>
                        </Form.Item>
                        <Form.Item label="????????????" name="status" initialValue={1}
                                   rules={[{required: true, message: '?????????????????????'}]}>
                            <Radio.Group>
                                <Radio value={1}>???</Radio>
                                <Radio value={0}>???</Radio>
                            </Radio.Group>
                        </Form.Item>
                        <Form.Item label="????????????" name="userNo">
                            <InputNumber/>
                        </Form.Item>
                    </Form>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default UserEditPage;
import {Button, Card, Form, Input, Select, Space, Spin, TreeSelect} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {FC, useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {getOrganization, queryForOrganizationAsyncTreeFlat, saveOrganization} from "../../../services/organization";
import {TreeNode} from "../../../models/TreeNode";
import {LegacyDataNode} from "rc-tree-select/lib/interface";

const {Option} = Select;

const OrganizationEditPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id?: string }>();
    // @ts-ignore
    const {pid} = history.location.state || {};

    const [loading, setLoading] = useState<boolean>(!!id);
    const [organizationTreeData, setOrganizationTreeData] = useState<TreeNode[]>([]);
    const [form] = Form.useForm();

    const fetchOrganizationTreeData = async (filteredId: number|null, appendedId: number) => {
        const organizationTreeData = await queryForOrganizationAsyncTreeFlat({
            filteredId,
            appendedId,
        });
        setOrganizationTreeData(organizationTreeData || []);
    };

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const org = await getOrganization(parseInt(id));
                form.setFieldsValue(org);

                fetchOrganizationTreeData(Number(id), org.pid as number).then(() => {
                });

                setLoading(false);
            }

            fetchData().then(() => {
            });
        } else {

            fetchOrganizationTreeData(null, pid).then(() => {
                form.setFieldsValue({pid});
            });
        }
    }, []);

    const onSave = () => {
        form.validateFields().then(values => {
            saveOrganization(values).then(() => {
                history.push('/admin/organization');
            });
        })
    };

    const onLoadData = async (treeNode: LegacyDataNode) => {
        const children = await queryForOrganizationAsyncTreeFlat({
            pid: treeNode.id,
            filteredId: Number(id),
        });
        setOrganizationTreeData([...organizationTreeData, ...children]);
    };

    return (
        <PageContainer
            className="page-container"
            title={`${id ? '编辑' : '新增'}组织`}
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={onSave}>保存</Button>
                </Space>
            }
            onBack={() => history.push('/admin/organization')}
        >
            <Spin spinning={loading}>
                <Card>
                    <Form
                        form={form}
                        labelCol={{span: 8}}
                        wrapperCol={{span: 8}}
                    >
                        <Form.Item name="id" hidden={true}/>
                        <Form.Item label="上级组织" name="pid" rules={[{required: true, message: '请选择上级组织'}]}>
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
                        <Form.Item label="组织名" name="orgName" rules={[{required: true, message: '请输入组织名'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="组织编码" name="orgCode" rules={[{required: true, message: '请输入组织编码'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="组织类型" name="orgType" rules={[{required: true, message: '请选择组织类型'}]}>
                            <Select>
                                <Option value={1}>集团</Option>
                                <Option value={2}>公司</Option>
                                <Option value={3}>分公司</Option>
                                <Option value={4}>项目部</Option>
                                <Option value={5}>部门</Option>
                            </Select>
                        </Form.Item>
                    </Form>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default OrganizationEditPage;
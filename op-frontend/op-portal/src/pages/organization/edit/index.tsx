import {Button, Card, Form, Input, Space, Spin, Select} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {getOrganization, saveOrganization} from "../../../services/organization";
import {Organization} from "../../../models/Organization";
import {saveMenu} from "../../../services/menu";

const {Option} = Select;

const OrganizationEditPage = () => {
    const history = useHistory();
    const {id} = useParams<{ id?: string }>();
    // @ts-ignore
    const {pid, pName} = history.location.state || {};

    const [loading, setLoading] = useState<boolean>(!!id);
    const [form] = Form.useForm();

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const org = await getOrganization(parseInt(id));
                form.setFieldsValue(org);
                setLoading(false);
            }

            fetchData().then(() => {
            });
        } else {
            form.setFieldsValue({pid});
        }
    }, []);

    const onSave = () => {
        form.validateFields().then(values => {
            saveMenu(values).then(() => {
                history.push('/admin/organization');
            });
        })
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
            <Card>
                <Spin spinning={loading}>
                    <Form
                        form={form}
                        labelCol={{span: 8}}
                        wrapperCol={{span: 8}}
                    >
                        <Form.Item name="id" hidden={true}/>
                        <Form.Item name="pid" hidden={true}/>
                        <Form.Item label="上级组织">
                            {pName}
                        </Form.Item>
                        <Form.Item label="组织名" name="orgName" rules={[{required: true, message: '请输入组织名'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="组织编码" name="orgCode" rules={[{required: true, message: '请输入组织编码'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="组织类型" name="orgType" rules={[{required: true, message: '请选择组织类型'}]}>
                            <Select >
                                <Option value={2}>公司</Option>
                                <Option value={3}>分公司</Option>
                                <Option value={4}>项目部</Option>
                                <Option value={5}>部门</Option>
                            </Select>
                        </Form.Item>
                    </Form>
                </Spin>
            </Card>
        </PageContainer>
    );
};

export default OrganizationEditPage;
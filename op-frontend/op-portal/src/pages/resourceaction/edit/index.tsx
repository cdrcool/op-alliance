import {Button, Card, Form, Input, InputNumber, Select, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {getResourceAction, saveResourceAction} from "../../../services/resourceAction";
import {ResourceAction} from "../../../models/ResourceAction";

const {TextArea} = Input;

const ResourceActionEditPage = () => {
    const history = useHistory();
    const {id} = useParams<{ id?: string }>();
    // @ts-ignore
    const {resourceId, resourceName} = history.location.state || {};

    const [loading, setLoading] = useState<boolean>(!!id);
    const [form] = Form.useForm();

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const resourceAction = await getResourceAction(parseInt(id));
                form.setFieldsValue(resourceAction);
                setLoading(false);
            }

            fetchData().then(() => {
            });
        } else {
            form.setFieldsValue({resourceId});
        }
    }, []);


    const onFinish = (resourceAction: ResourceAction) => {
        saveResourceAction(resourceAction).then(() => {
            history.push('/admin/resource');
        });
    };

    const onFinishFailed = (errorInfo: any) => {
        console.log('保存资源动作失败:', errorInfo);
    };

    return (
        <PageContainer
            className="page-container"
            title={`${id ? '编辑' : '新增'}资源动作`}
            header={{
                breadcrumb: {},
            }}
            onBack={() => history.push('/admin/resource')}

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
                        <Form.Item name="resourceId" hidden={true}/>
                        <Form.Item label="资源名" name="resourceName">
                            {resourceName}
                        </Form.Item>
                        <Form.Item label="动作名" name="actionName" rules={[{required: true, message: '请输入动作名'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="动作路径" name="actionPath" rules={[{required: true, message: '请输入动作路径'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="动作描述" name="actionDesc">
                            <TextArea/>
                        </Form.Item>
                        <Form.Item label="动作编号" name="actionNo">
                            <InputNumber/>
                        </Form.Item>
                        <Form.Item wrapperCol={{offset: 8, span: 8}}>
                            <Space>
                                <Button onClick={() => history.push('/admin/resource')}>
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

export default ResourceActionEditPage;
import {Button, Card, Form, Input, InputNumber, Select, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {getResource, saveResource} from "../../../services/resource";
import {Resource} from "../../../models/Resource";
import {queryResourceCategorySelectList} from "../../../services/resourceCategory";
import {SelectOptions} from "../../../models/SelectOptions";

const {TextArea} = Input;
const {Option} = Select;

const ResourceEditPage = () => {
    const history = useHistory();
    const {id} = useParams<{ id?: string }>();
    // @ts-ignore
    const {categoryId} = history.location.state || {};

    const [loading, setLoading] = useState<boolean>(!!id);
    const [categoryOptions, setCategoryOptions] = useState<SelectOptions[]>([]);
    const [form] = Form.useForm();

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const resource = await getResource(parseInt(id));
                form.setFieldsValue(resource);
                setLoading(false);
            }

            fetchData().then(() => {
            });
        } else {
            form.setFieldsValue({categoryId});
        }

        const fetchCategoryOptions = async () => {
            const categoryOptions = await queryResourceCategorySelectList({});
            setCategoryOptions(categoryOptions || []);
        };
        fetchCategoryOptions().then(() => {
        });

    }, []);


    const onFinish = (resource: Resource) => {
        saveResource(resource).then(() => {
            history.push('/admin/resource');
        });
    };

    const onFinishFailed = (errorInfo: any) => {
        console.log('保存资源失败:', errorInfo);
    };

    return (
        <PageContainer
            className="page-container"
            title={`${id ? '编辑' : '新增'}资源`}
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
                        <Form.Item label="资源分类" name="categoryId">
                            <Select>
                                {
                                    categoryOptions.map(item => <Option key={item.value} value={item.value}>{item.label}</Option>)
                                }
                            </Select>
                        </Form.Item>
                        <Form.Item label="资源名" name="resourceName" rules={[{required: true, message: '请输入资源名'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="资源路径" name="resourcePath" rules={[{required: true, message: '请输入资源路径'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="资源描述" name="resourceDesc">
                            <TextArea/>
                        </Form.Item>
                        <Form.Item label="资源编号" name="resourceNo">
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

export default ResourceEditPage;
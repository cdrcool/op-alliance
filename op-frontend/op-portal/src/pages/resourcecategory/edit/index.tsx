import {Button, Card, Form, Input, InputNumber, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {getResourceCategory, saveResourceCategory} from "../../../services/resourceCategory";

const {TextArea} = Input;

const ResourceCategoryEditPage = () => {
    const history = useHistory();
    const {id} = useParams<{ id?: string }>();

    const [loading, setLoading] = useState<boolean>(!!id);
    const [form] = Form.useForm();

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const resourceCategory = await getResourceCategory(parseInt(id));
                form.setFieldsValue(resourceCategory);
                setLoading(false);
            }

            fetchData().then(() => {
            });
        }
    }, []);

    const onSave = () => {
        form.validateFields().then(values => {
            saveResourceCategory(values).then(() => {
                history.push('/admin/resourceCategory');
            });
        })
    };

    return (
        <PageContainer
            className="page-container"
            title={`${id ? '编辑' : '新增'}资源分类`}
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={onSave}>保存</Button>
                </Space>
            }
            onBack={() => history.push('/admin/resourceCategory')}
        >
            <Card>
                <Spin spinning={loading}>
                    <Form
                        form={form}
                        labelCol={{span: 8}}
                        wrapperCol={{span: 8}}
                    >
                        <Form.Item name="id" hidden={true}/>
                        <Form.Item label="分类名称" name="categoryName" rules={[{required: true, message: '请输入分类名称'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="分类图标" name="categoryIcon" rules={[{required: true, message: '请输入分类图标'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="服务名称" name="serverName">
                            <TextArea/>
                        </Form.Item>
                        <Form.Item label="分类编号" name="categoryNo">
                            <InputNumber/>
                        </Form.Item>
                    </Form>
                </Spin>
            </Card>
        </PageContainer>
    );
};

export default ResourceCategoryEditPage;
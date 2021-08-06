import {Button, Card, Form, Input, InputNumber, Radio, Select, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {FC, useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {getWhiteResource, saveWhiteResource} from "../../../services/whiteResource";

const {TextArea} = Input;

const WhiteResourceEditPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id?: string }>();

    const [loading, setLoading] = useState<boolean>(!!id);
    const [form] = Form.useForm();

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const whiteResource = await getWhiteResource(parseInt(id));
                form.setFieldsValue(whiteResource);
                setLoading(false);
            }

            fetchData().then(() => {
            });
        }
    }, []);

    const onSave = () => {
        form.validateFields().then(values => {
            saveWhiteResource(values).then(() => {
                history.push('/admin/whiteResource');
            });
        })
    };

    return (
        <PageContainer
            className="page-container"
            title={`${id ? '编辑' : '新增'}白名单资源`}
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={onSave}>保存</Button>
                </Space>
            }
            onBack={() => history.push('/admin/whiteResource')}
        >
            <Spin spinning={loading}>
                <Card>
                    <Form
                        form={form}
                        labelCol={{span: 8}}
                        wrapperCol={{span: 8}}
                    >
                        <Form.Item name="id" hidden={true}/>
                        <Form.Item label="资源名称" name="resourceName" rules={[{required: true, message: '请输入资源名称'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="资源路径" name="resourcePath" rules={[{required: true, message: '请输入资源路径'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="资源描述" name="resourceDesc">
                            <TextArea/>
                        </Form.Item>
                        <Form.Item label="是否启用" name="status" initialValue={1}
                                   rules={[{required: true, message: '请勾选是否启用'}]}>
                            <Radio.Group>
                                <Radio value={1}>是</Radio>
                                <Radio value={0}>否</Radio>
                            </Radio.Group>
                        </Form.Item>
                        <Form.Item label="资源编号" name="resourceNo">
                            <InputNumber/>
                        </Form.Item>
                    </Form>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default WhiteResourceEditPage;
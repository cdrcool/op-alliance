import {Button, Card, Form, Input, InputNumber, Select, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {FC, useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {getOauthClient, saveOauthClient} from "../../../services/oauthclient";

const {TextArea} = Input;
const {Option} = Select;

const OauthClientEditPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id?: string }>();

    const [loading, setLoading] = useState<boolean>(!!id);
    const [form] = Form.useForm();

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const oauthClient = await getOauthClient(parseInt(id));
                form.setFieldsValue(oauthClient);
                setLoading(false);
            }

            fetchData().then(() => {
            });
        }
    }, []);

    const onSave = () => {
        form.validateFields().then(values => {
            saveOauthClient(values).then(() => {
                history.push('/admin/oauthClient');
            });
        })
    };

    return (
        <PageContainer
            className="page-container"
            title={`${id ? '编辑' : '新增'}Oauth客户端`}
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={onSave}>保存</Button>
                </Space>
            }
            onBack={() => history.push('/admin/oauthClient')}
        >
            <Spin spinning={loading}>
                <Card>
                    <Form
                        form={form}
                        labelCol={{span: 8}}
                        wrapperCol={{span: 8}}
                    >
                        <Form.Item name="id" hidden={true}/>
                        <Form.Item label="客户端标识" name="clientId" rules={[{required: true, message: '请输入客户端标识'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="客户端密钥" name="clientSecret" rules={[{required: true, message: '请输入客户端密钥'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="授权许可类型" name="authorizedGrantTypes">
                            <Select mode="multiple">
                                <Option value="authorization_code">授权码模式</Option>
                                <Option value="password">密码模式</Option>
                                <Option value="implicit">隐式模式</Option>
                                <Option value="client_credentials">客户端模式</Option>
                                <Option value="refresh_token">刷新token</Option>
                            </Select>
                        </Form.Item>
                        <Form.Item label="授权范围" name="scope">
                            <Input/>
                        </Form.Item>
                        <Form.Item label="重定向地址" name="webServerRedirectUri">
                            <Input/>
                        </Form.Item>
                        <Form.Item label="权限" name="authorities">
                            <Input/>
                        </Form.Item>
                        <Form.Item label="资源ids" name="resourceIds">
                            <Input/>
                        </Form.Item>
                        <Form.Item label="访问令牌有效期" name="accessTokenValidity">
                            <InputNumber/>
                        </Form.Item>
                        <Form.Item label="刷新令牌有效期" name="refreshTokenValidity">
                            <InputNumber/>
                        </Form.Item>
                        <Form.Item label="是否自动授权" name="autoapprove">
                            <Input/>
                        </Form.Item>
                        <Form.Item label="其它信息" name="additionalInformation">
                            <Input/>
                        </Form.Item>
                    </Form>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default OauthClientEditPage;
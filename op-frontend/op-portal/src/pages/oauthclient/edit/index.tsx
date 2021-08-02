import {Button, Card, Form, Input, InputNumber, Select, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {FC, useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {getOauthClient, saveOauthClient} from "../../../services/oauthclient";

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
                form.setFieldsValue({
                    'authorizedGrantTypes': oauthClient.authorizedGrantTypes?.split(','),
                });
                setLoading(false);
            }

            fetchData().then(() => {
            });
        }
    }, []);

    const onSave = () => {
        form.validateFields().then(values => {
            values.authorizedGrantTypes = values.authorizedGrantTypes.join(',');
            saveOauthClient(values).then(() => {
                history.push('/admin/oauthClient');
            });
        })
    };

    return (
        <PageContainer
            className="page-container"
            title={`${id ? '编辑' : '新增'}OAuth客户端`}
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
                        <Form.Item label="授权许可类型" name="authorizedGrantTypes" rules={[{required: true, message: '请选择授权许可类型'}]}
                                   tooltip="可以同时设置多个授权许可类型，用逗号(,)分隔；刷新token只有在授权码模式或密码模式时才有效">
                            <Select mode="multiple">
                                <Option value="authorization_code">授权码模式</Option>
                                <Option value="password">密码模式</Option>
                                <Option value="implicit">隐式模式</Option>
                                <Option value="client_credentials">客户端模式</Option>
                                <Option value="refresh_token">刷新token</Option>
                            </Select>
                        </Form.Item>
                        <Form.Item label="授权范围" name="scope" tooltip="指定客户端申请的权限范围，可选值包括read|write|trust，若有多个权限范围用逗号(,)分隔">
                            <Input/>
                        </Form.Item>
                        <Form.Item label="重定向地址" name="webServerRedirectUri" tooltip="客户端的重定向URI，当授权许可类型为授权码模式或隐式模式时，在OAuth的流程中会使用并检查与注册时填写的重定向地址是否一致">
                            <Input/>
                        </Form.Item>
                        <Form.Item label="权限" name="authorities" tooltip="指定客户端所拥有的的权限值，若有多个权限值，用逗号(,)分隔；如果是授权码模式或密码模式，可以不用设置，因为服务端将根据用户在服务端所拥有的权限来判断是否有权限访问对应的API">
                            <Input/>
                        </Form.Item>
                        <Form.Item label="资源ids" name="resourceIds" tooltip="客户端所能访问的资源id集合，多个资源时用逗号(,)分隔">
                            <Input/>
                        </Form.Item>
                        <Form.Item label="访问令牌有效期" name="accessTokenValidity" tooltip="单位为秒，若不设定值则默认为12小时">
                            <InputNumber/>
                        </Form.Item>
                        <Form.Item label="刷新令牌有效期" name="refreshTokenValidity" tooltip="单位为秒，若不设定值则默认为30天；如果授权模式中不包括刷新token，则不用设置该字段">
                            <InputNumber/>
                        </Form.Item>
                        <Form.Item label="是否自动授权" name="autoapprove" tooltip="设置用户是否自动授权，默认值为false，可选值包括true|false|read|write；该字段只适用于授权码模式">
                            <Input/>
                        </Form.Item>
                        <Form.Item label="其它信息" name="additionalInformation" tooltip="若设置值，必须是JSON格式的数据">
                            <Input/>
                        </Form.Item>
                    </Form>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default OauthClientEditPage;
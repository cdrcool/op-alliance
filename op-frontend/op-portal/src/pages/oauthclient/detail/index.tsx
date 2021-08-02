import {Button, Card, Descriptions, Popconfirm, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {FC, useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {deleteOauthClient, getOauthClient} from "../../../services/oauthclient";
import {OauthClient} from "../../../models/OauthClient";

const OauthClientDetailPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id: string }>();

    const [loading, setLoading] = useState<boolean>(true);
    const [oauthClient, setOauthClient] = useState<OauthClient>({});

    useEffect(() => {
        const fetchData = async () => {
            const oauthClient = await getOauthClient(parseInt(id));
            setOauthClient(oauthClient || {});
            setLoading(false);
        }

        fetchData().then(() => {
        });
    }, []);

    const onDeleteOauthClient = (id: string) => {
        deleteOauthClient(parseInt(id)).then(() => history.push('/admin/oauthClient'));
    }

    return (
        <PageContainer
            className="page-container"
            title="OAuth客户端详情"
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={() => history.push(`/admin/oauthClient/edit/${id}`)}>编辑</Button>
                    <Popconfirm
                        title="确定要删除吗？"
                        okText="确定"
                        cancelText="取消"
                        onConfirm={() => onDeleteOauthClient(id)}
                    >
                        <Button>删除</Button>
                    </Popconfirm>
                    <Button>打印</Button>
                </Space>
            }
            onBack={() => history.push('/admin/oauthClient')}
        >
            <Spin spinning={loading}>
                <Card>
                    <Descriptions>
                        <Descriptions.Item label="客户端标识">{oauthClient.clientId}</Descriptions.Item>
                        <Descriptions.Item label="客户端密钥">{oauthClient.clientSecret}</Descriptions.Item>
                        <Descriptions.Item label="授权许可类型">{
                            oauthClient.authorizedGrantTypes &&
                            oauthClient.authorizedGrantTypes.split(',')
                                .map(item => item === 'authorization_code' ? '授权码模式' :
                                    (item === 'password' ? '密码模式' :
                                            (item === 'implicit' ? '隐式模式' :
                                                    (item === 'client_credentials' ? '客户端模式' :
                                                            (item === 'refresh_token' ? '刷新token' : '')
                                                    )
                                            )
                                    ))
                                .join(',')

                        }</Descriptions.Item>
                        <Descriptions.Item label="授权范围">{oauthClient.scope}</Descriptions.Item>
                        <Descriptions.Item label="重定向地址">{oauthClient.webServerRedirectUri}</Descriptions.Item>
                        <Descriptions.Item label="权限">{oauthClient.authorities}</Descriptions.Item>
                        <Descriptions.Item label="资源ids">{oauthClient.resourceIds}</Descriptions.Item>
                        <Descriptions.Item label="访问令牌有效期">{oauthClient.accessTokenValidity}</Descriptions.Item>
                        <Descriptions.Item label="刷新令牌有效期">{oauthClient.refreshTokenValidity}</Descriptions.Item>
                        <Descriptions.Item label="是否自动授权">{oauthClient.autoapprove}</Descriptions.Item>
                        <Descriptions.Item label="其它信息">{oauthClient.additionalInformation}</Descriptions.Item>
                    </Descriptions>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default OauthClientDetailPage;
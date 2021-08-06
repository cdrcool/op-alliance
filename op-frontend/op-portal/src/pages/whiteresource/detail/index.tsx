import {Button, Card, Descriptions, Popconfirm, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {FC, useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {WhiteResource} from "../../../models/WhiteResource";
import {deleteWhiteResource, getWhiteResource} from "../../../services/whiteResource";

const WhiteResourceDetailPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id: string }>();

    const [loading, setLoading] = useState<boolean>(true);
    const [whiteResource, setWhiteResource] = useState<WhiteResource>({});

    useEffect(() => {
        const fetchData = async () => {
            const whiteResource = await getWhiteResource(parseInt(id));
            setWhiteResource(whiteResource || {});
            setLoading(false);
        }

        fetchData().then(() => {
        });
    }, []);

    const onDeleteWhiteResource = (id: string) => {
        deleteWhiteResource(parseInt(id)).then(() => history.push('/admin/whiteResource'));
    }

    return (
        <PageContainer
            className="page-container"
            title="白名单资源"
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={() => history.push(`/admin/whiteResource/edit/${id}`)}>编辑</Button>
                    <Popconfirm
                        title="确定要删除吗？"
                        okText="确定"
                        cancelText="取消"
                        onConfirm={() => onDeleteWhiteResource(id)}
                    >
                        <Button>删除</Button>
                    </Popconfirm>
                    <Button>打印</Button>
                </Space>
            }
            onBack={() => history.push('/admin/whiteResource')}
        >
            <Spin spinning={loading}>
                <Card>
                    <Descriptions>
                        <Descriptions.Item label="资源名称">{whiteResource.resourceName}</Descriptions.Item>
                        <Descriptions.Item label="资源路径">{whiteResource.resourcePath}</Descriptions.Item>
                        <Descriptions.Item label="资源描述">{whiteResource.resourceDesc}</Descriptions.Item>
                        <Descriptions.Item
                            label="是否启用">{whiteResource.status && (whiteResource.status === 1 ? '启用' : '禁用')}</Descriptions.Item>
                        <Descriptions.Item label="资源编号">{whiteResource.resourceNo}</Descriptions.Item>
                    </Descriptions>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default WhiteResourceDetailPage;
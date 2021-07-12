import {Button, Card, Descriptions, Popconfirm, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {Resource} from "../../../models/Resource";
import {deleteResource, getResource} from "../../../services/resource";

const ResourceDetailPage = () => {
    const history = useHistory();
    const {id} = useParams<{ id: string }>();

    const [loading, setLoading] = useState<boolean>(true);
    const [resource, setResource] = useState<Resource>({});

    useEffect(() => {
        const fetchData = async () => {
            const resource = await getResource(parseInt(id));
            setResource(resource || {});
            setLoading(false);
        }

        fetchData().then(() => {
        });
    }, []);

    const onDeleteResource = (id: string) => {
        deleteResource(parseInt(id)).then(() => history.push('/admin/resource'));
    }

    return (
        <PageContainer
            className="page-container"
            title="资源详情"
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={() => history.push(`/admin/resource/edit/${id}`)}>编辑</Button>
                    <Popconfirm
                        title="确定要删除吗？"
                        okText="确定"
                        cancelText="取消"
                        onConfirm={() => onDeleteResource(id)}
                    >
                        <Button>删除</Button>
                    </Popconfirm>
                    <Button>打印</Button>
                </Space>
            }
            onBack={() => history.push('/admin/resource')}

        >
            <Card>
                <Spin spinning={loading}>
                    <Descriptions>
                        <Descriptions.Item label="资源名">{resource.resourceName}</Descriptions.Item>
                        <Descriptions.Item label="资源路径">{resource.resourcePath}</Descriptions.Item>
                        <Descriptions.Item label="资源描述">{resource.resourceDesc}</Descriptions.Item>
                        <Descriptions.Item label="资源编号">{resource.resourceNo}</Descriptions.Item>
                    </Descriptions>
                </Spin>
            </Card>
        </PageContainer>
    );
};

export default ResourceDetailPage;
import {Button, Card, Descriptions, Popconfirm, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {deleteResourceAction, getResourceAction} from "../../../services/resourceAction";
import {ResourceAction} from "../../../models/ResourceAction";

const ResourceActionDetailPage = () => {
    const history = useHistory();
    const {id} = useParams<{ id: string }>();

    const [loading, setLoading] = useState<boolean>(true);
    const [resourceAction, setResourceAction] = useState<ResourceAction>({});

    useEffect(() => {
        const fetchData = async () => {
            const resourceAction = await getResourceAction(parseInt(id));
            setResourceAction(resourceAction || {});
            setLoading(false);
        }

        fetchData().then(() => {
        });
    }, []);

    const onDeleteResourceAction = (id: string) => {
        deleteResourceAction(parseInt(id)).then(() => history.push('/admin/resource'));
    }

    return (
        <PageContainer
            className="page-container"
            title="资源动作详情"
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={() => history.push(`/admin/resourceAction/edit/${id}`)}>编辑</Button>
                    <Popconfirm
                        title="确定要删除吗？"
                        okText="确定"
                        cancelText="取消"
                        onConfirm={() => onDeleteResourceAction(id)}
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
                        <Descriptions.Item label="动作名">{resourceAction.actionName}</Descriptions.Item>
                        <Descriptions.Item label="动作路径">{resourceAction.actionPath}</Descriptions.Item>
                        <Descriptions.Item label="动作描述">{resourceAction.actionDesc}</Descriptions.Item>
                        <Descriptions.Item label="动作编号">{resourceAction.actionNo}</Descriptions.Item>
                    </Descriptions>
                </Spin>
            </Card>
        </PageContainer>
    );
};

export default ResourceActionDetailPage;
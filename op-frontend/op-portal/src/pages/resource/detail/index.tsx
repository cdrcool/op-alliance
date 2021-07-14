import {Button, Card, Collapse, Descriptions, Popconfirm, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {Resource} from "../../../models/Resource";
import {deleteResource, getResource} from "../../../services/resource";
import {EditableProTable} from "@ant-design/pro-table";
import {ResourceAction} from "../../../models/ResourceAction";

const {Panel} = Collapse;

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
                    <Collapse bordered={false} ghost={true} activeKey={['baseInfo', 'actions']}>
                        <Panel header="基本信息" key="baseInfo" showArrow={false}>
                            <Descriptions>
                                <Descriptions.Item label="资源名">{resource.resourceName}</Descriptions.Item>
                                <Descriptions.Item label="资源路径">{resource.resourcePath}</Descriptions.Item>
                                <Descriptions.Item label="资源描述">{resource.resourceDesc}</Descriptions.Item>
                                <Descriptions.Item label="资源编号">{resource.resourceNo}</Descriptions.Item>
                            </Descriptions>
                        </Panel>
                        <Panel header="资源动作" key="actions" showArrow={false}>
                            <EditableProTable<ResourceAction>
                                rowKey="id"
                                columns={[
                                    {title: '动作名称', dataIndex: 'actionName'},
                                    {title: '动作路径', dataIndex: 'actionPath'},
                                    {title: '动作描述', dataIndex: 'actionDesc'},
                                    {title: '权限标识', dataIndex: 'permission'},
                                    {title: '动作编号', dataIndex: 'actionNo'},
                                ]}
                                recordCreatorProps={false}
                                value={resource.actions || []}
                            />
                        </Panel>
                    </Collapse>
                </Spin>
            </Card>
        </PageContainer>
    );
};

export default ResourceDetailPage;
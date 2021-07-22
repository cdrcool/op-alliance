import {Button, Card, Descriptions, Popconfirm, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {FC, useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {ResourceCategory} from "../../../models/ResourceCategory";
import {deleteResourceCategory, getResourceCategory} from "../../../services/resourceCategory";

const ResourceCategoryDetailPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id: string }>();

    const [loading, setLoading] = useState<boolean>(true);
    const [resourceCategory, setResourceCategory] = useState<ResourceCategory>({});

    useEffect(() => {
        const fetchData = async () => {
            const resourceCategory = await getResourceCategory(parseInt(id));
            setResourceCategory(resourceCategory || {});
            setLoading(false);
        }

        fetchData().then(() => {
        });
    }, []);

    const onDeleteResourceCategory = (id: string) => {
        deleteResourceCategory(parseInt(id)).then(() => history.push('/admin/resourceCategory'));
    }

    return (
        <PageContainer
            className="page-container"
            title="资源分类详情"
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary"
                            onClick={() => history.push(`/admin/resourceCategory/edit/${id}`)}>编辑</Button>
                    <Popconfirm
                        title="确定要删除吗？"
                        okText="确定"
                        cancelText="取消"
                        onConfirm={() => onDeleteResourceCategory(id)}
                    >
                        <Button>删除</Button>
                    </Popconfirm>
                    <Button>打印</Button>
                </Space>
            }
            onBack={() => history.push('/admin/resourceCategory')}
        >
            <Spin spinning={loading}>
                <Card>
                    <Descriptions>
                        <Descriptions.Item label="分类名称">{resourceCategory.categoryName}</Descriptions.Item>
                        <Descriptions.Item label="分类图标">{resourceCategory.categoryIcon}</Descriptions.Item>
                        <Descriptions.Item label="服务名称">{resourceCategory.serverName}</Descriptions.Item>
                        <Descriptions.Item label="分类编号">{resourceCategory.categoryNo}</Descriptions.Item>
                    </Descriptions>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default ResourceCategoryDetailPage;
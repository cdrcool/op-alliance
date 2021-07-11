import {Button, Card, Descriptions, Popconfirm, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {Organization} from "../../../models/Organization";
import {deleteOrganization, getOrganization} from "../../../services/organization";

const OrganizationDetailPage = () => {
    const history = useHistory();
    const {id} = useParams<{ id: string }>();

    const [loading, setLoading] = useState<boolean>(true);
    const [org, setOrg] = useState<Organization>({});

    useEffect(() => {
        const fetchData = async () => {
            const org = await getOrganization(parseInt(id));
            setOrg(org || {});
            setLoading(false);
        }

        fetchData().then(() => {
        });
    }, []);

    const onDeleteOrganization = (id: string) => {
        deleteOrganization(parseInt(id)).then(() => history.push('/admin/organization'));
    }

    return (
        <PageContainer
            className="page-container"
            title="组织详情"
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={() => history.push(`/admin/organization/edit/${id}`)}>编辑</Button>
                    <Popconfirm
                        title="确定要删除吗？"
                        okText="确定"
                        cancelText="取消"
                        onConfirm={() => onDeleteOrganization(id)}
                    >
                        <Button>删除</Button>
                    </Popconfirm>
                    <Button>打印</Button>
                </Space>
            }
            onBack={() => history.push('/admin/organization')}

        >
            <Card>
                <Spin spinning={loading}>
                    <Descriptions>
                        <Descriptions.Item label="组织名">{org.orgName}</Descriptions.Item>
                        <Descriptions.Item label="组织编码">{org.orgCode}</Descriptions.Item>
                        <Descriptions.Item label="组织类型">
                            {org.orgType && (org.orgType === 1 ? '集团' : (org.orgType === 2 ? '公司' : (org.orgType === 3 ? '分公司' : (org.orgType === 4 ? '项目部' : '部门'))))}
                        </Descriptions.Item>
                    </Descriptions>
                </Spin>
            </Card>
        </PageContainer>
    );
};

export default OrganizationDetailPage;
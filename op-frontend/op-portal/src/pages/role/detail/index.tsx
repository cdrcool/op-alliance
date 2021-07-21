import {Button, Card, Descriptions, Popconfirm, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {deleteRole, getRole} from "../../../services/role";
import {Role} from "../../../models/Role";
import {PageContainer} from "@ant-design/pro-layout";

const RoleDetailPage = () => {
    const history = useHistory();
    const {id} = useParams<{ id: string }>();

    const [loading, setLoading] = useState<boolean>(true);
    const [role, setRole] = useState<Role>({});

    useEffect(() => {
        const fetchData = async () => {
            const role = await getRole(parseInt(id));
            setRole(role || {});
            setLoading(false);
        }

        fetchData().then(() => {
        });
    }, []);

    const onDeleteRole = (id: string) => {
        deleteRole(parseInt(id)).then(() => history.push('/admin/role'));
    }

    return (
        <PageContainer
            className="page-container"
            title="角色详情"
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={() => history.push(`/admin/role/edit/${id}`)}>编辑</Button>
                    <Popconfirm
                        title="确定要删除吗？"
                        okText="确定"
                        cancelText="取消"
                        onConfirm={() => onDeleteRole(id)}
                    >
                        <Button>删除</Button>
                    </Popconfirm>
                    <Button>打印</Button>
                </Space>
            }
            onBack={() => history.push('/admin/role')}
        >
            <Card>
                <Spin spinning={loading}>
                    <Descriptions>
                        <Descriptions.Item label="角色名">{role.roleName}</Descriptions.Item>
                        <Descriptions.Item label="角色编码">{role.roleCode}</Descriptions.Item>
                        <Descriptions.Item label="角色描述">{role.roleDesc}</Descriptions.Item>
                        <Descriptions.Item
                            label="是否启用">{role.status && (role.status === 1 ? '启用' : '禁用')}</Descriptions.Item>
                        <Descriptions.Item label="角色编号">{role.roleNo}</Descriptions.Item>
                    </Descriptions>
                </Spin>
            </Card>
        </PageContainer>
    );
};

export default RoleDetailPage;
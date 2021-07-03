import {Button, Descriptions, PageHeader, Popconfirm, Space} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {deleteRole, getRole} from "../../../services/role";
import {Role} from "../../../models/Role";

const RoleDetailPage = () => {
    const [role, setRole] = useState<Role>({});

    const history = useHistory();
    const {id} = useParams<{ id: string }>();

    useEffect(() => {
        const fetchData = async () => {
            const role = await getRole(parseInt(id));
            setRole(role);
        }

        fetchData();
    }, []);

    const handleDelete = (id: string) => {
        deleteRole(parseInt(id));
        history.push('/management/role');
    }

    return (
        <div className="card">
            <PageHeader title="角色详情"
                        extra={
                            <Space>
                                <Button onClick={() => history.push('/management/role-edit')}>编辑</Button>
                                <Popconfirm
                                    title="确定要删除吗？"
                                    okText="确定"
                                    cancelText="取消"
                                >
                                    <Button onClick={() => handleDelete(id)}>删除</Button>
                                </Popconfirm>
                                <Button>打印</Button>
                            </Space>
                        }
                        onBack={() => history.push('/management/role')}
            >
                <Descriptions>
                    <Descriptions.Item label="角色名">{role.roleName}</Descriptions.Item>
                    <Descriptions.Item label="角色编码">{role.roleCode}</Descriptions.Item>
                    <Descriptions.Item label="角色描述">{role.roleDesc}</Descriptions.Item>
                    <Descriptions.Item
                        label="是否启用">{role.status && (role.status === 1 ? '启用' : '禁用')}</Descriptions.Item>
                    <Descriptions.Item label="角色编号">{role.roleNo}</Descriptions.Item>
                </Descriptions>
            </PageHeader>
        </div>
    );
};

export default RoleDetailPage;
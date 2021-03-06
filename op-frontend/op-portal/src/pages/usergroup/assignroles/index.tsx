import React, {FC, useState} from 'react';
import {PageContainer} from "@ant-design/pro-layout";
import {useHistory, useParams} from "react-router-dom";
import {Button, Space, Tag} from "antd";
import ProList from '@ant-design/pro-list';
import {Role} from "../../../models/Role";
import {assignUserGroupRoles, loadUserGroupAssignedRoles} from "../../../services/userGroup";

const UserGroupAssignRolesPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id: string }>();

    const [selectedRowKeys, setSelectedRowKeys] = useState<number[]>([]);
    const [selectedRows, setSelectedRows] = useState<Role[]>([]);

    const onSaveSelected = () => {
        assignUserGroupRoles(Number(id), selectedRowKeys).then(() => history.push('/admin/userGroup'));
    };

    return (
        <PageContainer
            className="page-container"
            title="用户组角色分配"
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={onSaveSelected}>保存</Button>
                </Space>
            }
            onBack={() => history.push('/admin/userGroup')}
        >
            <ProList<Role>
                style={{backgroundColor: '#fff'}}
                rowKey="id"
                options={{
                    fullScreen: true,
                    setting: false,
                }}
                metas={{
                    avatar: {},
                    title: {
                        title: '角色名称',
                        dataIndex: 'roleName',
                    },
                    subTitle: {
                        dataIndex: '角色编码',
                        render: (_, row) => {
                            return (
                                <Tag color="#5BD8A6" key={row.roleCode}>
                                    {row.roleCode}
                                </Tag>
                            );
                        },
                    },
                    type: {},
                    content: {
                        dataIndex: 'roleDesc',
                    },
                }}
                tableAlertRender={
                    () => (
                        <Space size={24}>
                            <span>
                                已选 {selectedRowKeys.length} 项
                            </span>
                            <span>
                                {
                                    selectedRows.map(row => row.roleName).join()
                                }
                            </span>
                        </Space>
                    )
                }
                request={
                    async () => {
                        const roles = await loadUserGroupAssignedRoles(Number(id));

                        const selectedRows = roles.filter(role => role.checked);
                        setSelectedRows(selectedRows);
                        setSelectedRowKeys(selectedRows.map(role => role.id as number));

                        return {
                            data: roles,
                            success: true,
                            total: roles.length,
                        };
                    }
                }
                rowSelection={
                    {
                        selectedRowKeys: selectedRowKeys,
                        onChange: (selectedRowKeys, selectedRows) => {
                            setSelectedRowKeys(selectedRowKeys as number[]);
                            setSelectedRows(selectedRows);
                        },
                        getCheckboxProps: (record) => ({
                            disabled: !record.enableUncheck,
                        }),
                    }
                }
                pagination={{
                    pageSize: 10,
                }}
            />
        </PageContainer>
    );
};

export default UserGroupAssignRolesPage;
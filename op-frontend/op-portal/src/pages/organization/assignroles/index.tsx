import React, {FC, useState} from 'react';
import {useHistory, useParams} from "react-router-dom";
import {Button, Space, Tag} from "antd";
import {PageContainer} from "@ant-design/pro-layout";
import ProList from '@ant-design/pro-list';
import {Role} from "../../../models/Role";
import {assignOrganizationRoles, loadOrganizationAssignedRoles} from "../../../services/organization";

const OrganizationAssignRolesPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id: string }>();

    const [selectedRowKeys, setSelectedRowKeys] = useState<number[]>([]);
    const [selectedRows, setSelectedRows] = useState<Role[]>([]);
    const [defaultSelectedRowKeys, setDefaultSelectedRowKeys] = useState<number[]>([]);
    const [defaultSelectedRows, setDefaultSetSelectedRows] = useState<Role[]>([]);

    const onSaveSelected = () => {
        assignOrganizationRoles(Number(id), selectedRowKeys).then(() => history.push('/admin/organization'));
    };

    return (
        <PageContainer
            className="page-container"
            title="组织角色分配"
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={onSaveSelected}>保存</Button>
                </Space>
            }
            onBack={() => history.push('/admin/organization')}
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
                tableAlertOptionRender={() => {
                    return (
                        <a onClick={() => {
                            setSelectedRowKeys(defaultSelectedRowKeys);
                            setSelectedRows(defaultSelectedRows);
                        }}>
                            取消选择
                        </a>
                    );
                }}
                request={
                    async () => {
                        const roles = await loadOrganizationAssignedRoles(Number(id));

                        const selectedRows = roles.filter(role => role.checked);
                        setSelectedRows(selectedRows);
                        setSelectedRowKeys(selectedRows.map(role => role.id as number));
                        setDefaultSetSelectedRows(selectedRows);
                        setDefaultSelectedRowKeys(selectedRows.filter(role => !role.enableUncheck).map(role => role.id as number));

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

export default OrganizationAssignRolesPage;